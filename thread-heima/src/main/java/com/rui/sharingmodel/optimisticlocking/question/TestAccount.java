package com.rui.sharingmodel.optimisticlocking.question;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description: 非阻塞线程安全举例
 * <hr/>
 * date: 2022/11/2
 *
 * @author rui
 */
@Slf4j(topic = "rui.TestAccount")
public class TestAccount {
    public static void main(String[] args) {
        Account accountUnsafe = new AccountUnsafe(10000);
        Account.demo(accountUnsafe);
        Account accountSafeByLock = new AccountSafeByLock(10000);
        Account.demo(accountSafeByLock);
        Account accountSafeByCAS = new AccountSafeByCAS(10000);
        Account.demo(accountSafeByCAS);
    }
}

class AccountSafeByCAS implements Account {

    /**
     * 余额
     */
    private final AtomicInteger balance;

    public AccountSafeByCAS(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        // while (true) {
        //     // 余额的最新值
        //     int prev = balance.get();
        //     // 修改的余额
        //     int next = prev - amount;
        //     // 真正修改 CAS(compareAndSet / compareAndSwap)
        //     if (balance.compareAndSet(prev, next)) {
        //         break;
        //     }
        // }
        this.balance.getAndAdd(-1 * amount);
    }
}

class AccountSafeByLock implements Account {

    /**
     * 余额
     */
    private Integer balance;

    public AccountSafeByLock(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        synchronized (this) {
            return this.balance;
        }
    }

    @Override
    public void withdraw(Integer amount) {
        synchronized (this) {
            this.balance -= amount;
        }
    }
}

class AccountUnsafe implements Account {

    /**
     * 余额
     */
    private Integer balance;

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return this.balance;
    }

    @Override
    public void withdraw(Integer amount) {
        this.balance -= amount;
    }
}

interface Account {
    /**
     * 获取余额
     *
     * @return {@link Integer}
     */
    Integer getBalance();

    /**
     * 取款
     *
     * @param amount 量
     */
    void withdraw(Integer amount);

    static void demo(Account account) {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threadList.add(new Thread(() -> account.withdraw(10), "t" + (i + 1)));
        }
        long start = System.nanoTime();
        threadList.forEach(Thread::start);
        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance() + " cost: " + (end - start) / 1000_000 + "ms");
    }
}
