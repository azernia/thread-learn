package com.rui.base;

import java.util.concurrent.TimeUnit;

/**
 * @author rui
 * @since 2022/5/17 09:58
 */
public class TNoVolatile {
    boolean running = true;

    void m() {
        System.out.println("m start");
        while (running) {
            // System.out.println("running");
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        TNoVolatile t = new TNoVolatile();
        new Thread(t::m, "t1").start(); // Lambda 表达式 new Thread(new Runnable() { run() {t.m()} })
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.running = false;
    }
}
