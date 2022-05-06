package com.rui.sync;

public class T_Static_Synchronized {
    private static int count = 10;

    public synchronized static void m() {   // 等同于 synchronized(T_Static_Synchronized.class)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm() {
        synchronized (T_Static_Synchronized.class) {
            count--;
        }
    }
}
