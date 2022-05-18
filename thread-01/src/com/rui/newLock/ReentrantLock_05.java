package com.rui.newLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * create date 2022/5/18 16:07
 * 公平锁 (不公平时直接抢锁)
 *  新的线程检查队列中是否有等待的线程有则进入队列等待否则获得锁
 * @author rui
 */
public class ReentrantLock_05 extends Thread {
    private static final ReentrantLock lock = new ReentrantLock(true); // true 标识为公平锁

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "get lock");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "release lock");
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock_05 lock05 = new ReentrantLock_05();
        Thread t1 = new Thread(lock05);
        Thread t2 = new Thread(lock05);
        t1.start();
        t2.start();
    }
}
