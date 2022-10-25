package com.rui.apply;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * description: 模拟转账
 * <hr/>
 * date: 2022/10/25 11:28
 *
 * @author rui
 */
@Slf4j(topic = "rui.Apply_03_ExerciseTransfer")
public class Apply_03_ExerciseTransfer {

    private static final Random RANDOM = new Random();

    public static int randomAmount(int amount) {
        return RANDOM.nextInt(amount) + 1;
    }

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(1000);
        Account b = new Account(1000);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                a.transfer(b, randomAmount(100));
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b.transfer(a, randomAmount(100));
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("after 2000 transfer account: {}", a.getMoney() + b.getMoney());
    }

}

/**
 * 账户
 */
class Account {
    private int money;  // 两个账户的的余额都为共享变量，需要给共有对象加锁

    public Account(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void transfer(Account target, int amount) {
        synchronized (Account.class) {
            if (this.money >= amount) {
                this.setMoney(this.getMoney() - amount);
                target.setMoney(target.getMoney() + amount);
            }
        }
    }
}
