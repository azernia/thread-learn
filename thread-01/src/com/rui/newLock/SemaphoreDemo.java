package com.rui.newLock;

import java.util.concurrent.Semaphore;

/**
 * Semaphore也叫信号量，在JDK1.5被引入，可以用来控制同时访问特定资源的线程数量，通过协调各个线程，以保证合理的使用资源。
 * 应用场景 卖票
 * create date 2022/5/19 09:09
 *
 * @author rui
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 最多允许多少个线程同时执行
        Semaphore semaphore = new Semaphore(1);
        // true 表示公平
        // Semaphore semaphore1 = new Semaphore(2, true);

        new Thread(() -> {
            try {
                semaphore.acquire();    // 取得 -》 permits - 1
                System.out.println("T1 running ....");
                Thread.sleep(200);
                System.out.println("T1 running ....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire();    // 取得 -》 permits - 1
                System.out.println("T2 running ....");
                Thread.sleep(200);
                System.out.println("T2 running ....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }).start();
    }
}
