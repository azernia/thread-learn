package com.rui.newLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 用于替代 synchronized
 * 由于 m1 锁定this 只有 m1 执行完毕的时候 m2 才执行
 * tryLock
 * @author rui
 * @since 2022/5/18 15:38
 */
public class ReentrantLock_03 {
    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 使用 tryLock 进行尝试锁定，不管锁定与否，方法都将继续执行
     * 可以根据 tryLock 的返回值判定是否锁定
     * 也可以指定 tryLock 的时间，注意 tryLock 异常时需要 unlock
     */
    void m2() {
        boolean locked = false;
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m2 ...." + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLock_03 lock03 = new ReentrantLock_03();
        new Thread(lock03::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(lock03::m2).start();
    }
}
