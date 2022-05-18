package com.rui.newLock;

import java.util.concurrent.TimeUnit;

/**
 * 可重入锁
 * @author rui
 * @since 2022/5/18 14:57
 */
public class ReentrantLock_01 {
    synchronized void m1() {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
            // synchronized 调用 synchronized 锁重入
            if(i == 2) m2();
        }
    }

    synchronized void m2() {
        System.out.println("m2 ....");
    }

    public static void main(String[] args) {
        ReentrantLock_01 lock01 = new ReentrantLock_01();
        new Thread(lock01::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 未拿到锁
        // new Thread(lock01::m2).start();
    }
}
