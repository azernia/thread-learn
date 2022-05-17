package com.rui.base;

import java.util.concurrent.TimeUnit;

/**
 * volatile
 * 保证线程可见性
 *  使用了 CPU 的缓存一致性协议
 * 禁止指令重排序
 * DCL 单例
 * Double Check Lock
 * Mgr06.java
 *
 * @author rui
 * @since 2022/5/17 09:58
 */
public class TVolatile {
    volatile boolean running = true;    // 表示值可变，时刻监测

    void m() {
        System.out.println("m start");
        while (running) {
            // System.out.println("running");
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        TVolatile t = new TVolatile();
        new Thread(t::m, "t1").start(); // Lambda 表达式 new Thread(new Runnable() { run() {t.m()} })
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.running = false;
    }
}
