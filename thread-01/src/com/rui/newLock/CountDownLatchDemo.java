package com.rui.newLock;

import java.util.concurrent.CountDownLatch;

/**
 *
 * create date 2022/5/18 16:22
 *
 * @author rui
 */
public class CountDownLatchDemo {

    private static void usingCountDownLatch() {
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
               int result = 0;
                for (int j = 0; j < 1000; j++) {
                    result += j;
                }
                latch.countDown();
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        try {
            // 每次减一变为0时打开 等待多个线程结束时执行业务
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end latch");
    }

    private static void usingJoin() {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
               int result = 0;
                for (int j = 0; j < 10000; j++) {
                    result += j;
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        usingCountDownLatch();
        usingJoin();
    }

}
