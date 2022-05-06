package com.rui.sync;

public class T_Sync_Demo {
    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + "m1 start...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "m1 end");
    }

    public void m2() {
        System.out.println(Thread.currentThread().getName() + "m2 start...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "m2 end");
    }

    public static void main(String[] args) {
        T_Sync_Demo demo = new T_Sync_Demo();
        new Thread(demo::m1, "t1").start();
        new Thread(demo::m2, "t2").start();
    }
}
