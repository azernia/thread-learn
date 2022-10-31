package com.rui.sharingmodel.lock;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * description: 死锁
 * <hr/>
 * date: 2022/10/31
 *
 * @author rui
 */
@Slf4j(topic = "rui.Lock_02_Deadlock")
public class Lock_02_Deadlock {

    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");
        new Philosopher("苏格拉底", c1, c2).start();
        new Philosopher("柏拉图", c2, c3).start();
        new Philosopher("亚里士多德", c3, c4).start();
        new Philosopher("赫拉克利特", c4, c5).start();
        new Philosopher("阿基米德", c5, c1).start();
    }

    private static void test1() {
        Object a = new Object();
        Object b = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (a) {
                log.debug("lock a");
                Sleeper.sleep(1);
                synchronized (b) {
                    log.debug("lock b");
                    log.debug("option....");
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (b) {
                log.debug("lock b");
                Sleeper.sleep(0.5);
                synchronized (a) {
                    log.debug("lock a");
                    log.debug("option...");
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }

}

// 筷子问题
@Slf4j(topic = "rui.Philosopher")
class Philosopher extends Thread {

    private final Chopstick left;

    private final Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            // 尝试获取左手筷子
            synchronized (left) {
                // 尝试获取右手筷子
                synchronized (right) {
                    eat();
                }
            }
        }
    }

    private void eat() {
        log.debug("eating...");
        Sleeper.sleep(1);
    }
}

class Chopstick {
    private final String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Chopstick{" +
                "name='" + name + '\'' +
                '}';
    }
}
