package com.rui.sync;

public class T_Synchronized {
    private int count = 10;

    private Object object = new Object();

    public void a() {
        // 锁定某个对象
        synchronized (object) { // 执行下面代码需要获取 object 的锁
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

    public void b() {
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
    // c d 作用相同
    public synchronized void c() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {
    }
}
