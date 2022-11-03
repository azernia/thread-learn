package com.rui.sharingmodel.optimisticlocking.actomic;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * description: 原引用类型
 * <hr/>
 * date: 2022/11/2
 *
 * @author rui
 */
@Slf4j(topic = "rui.Atom_Reference")
public class Atom_Reference {
    public static void main(String[] args) {
        DecimalAccountCAS decimalAccountCAS = new DecimalAccountCAS(new BigDecimal("10000"));
        DecimalAccount.demo(decimalAccountCAS);
    }
}

class DecimalAccountCAS implements DecimalAccount {

    private AtomicReference<BigDecimal> balance;

    public DecimalAccountCAS(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while (true) {
            BigDecimal prev = balance.get();
            BigDecimal next = prev.subtract(amount);
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }
}

interface DecimalAccount {
    BigDecimal getBalance();

    /**
     * 取款
     */
    void withdraw(BigDecimal amount);

    static void demo(DecimalAccount decimalAccount) {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threadList.add(new Thread(() -> decimalAccount.withdraw(BigDecimal.valueOf(10L)), "t" + (i + 1)));
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
        System.out.println(decimalAccount.getBalance() + " cost: " + (end - start) / 1000_000 + "ms");
    }
}
