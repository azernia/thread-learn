package com.rui.newLock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * create date 2022/5/18 16:47
 * CyclicBarrier
 * 类似栅栏 满了释放
 * 需要等待其他线程执行结束才执行
 * @author rui
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        // CyclicBarrier barrier = new CyclicBarrier(20); // 不执行任何操作
        // param1 满多少人 20 -> 满19个 param2 执行的操作
        CyclicBarrier barrier = new CyclicBarrier(20, () -> System.out.println("满人发车"));
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
