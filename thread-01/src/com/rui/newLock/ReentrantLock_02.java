package com.rui.newLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized 自动解锁 锁遇到异常 jvm 自动释放锁，lock 必须手动释放
 * @author rui
 * @since 2022/5/18 15:25
 */
public class ReentrantLock_02 {
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

    void m2() {
        try {
            lock.lock();
            System.out.println("m2 ....");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLock_02 lock02 = new ReentrantLock_02();
        new Thread(lock02::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(lock02::m2).start();
    }
}
