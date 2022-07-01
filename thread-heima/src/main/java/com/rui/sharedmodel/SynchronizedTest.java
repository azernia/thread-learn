package com.rui.sharedmodel;

/**
 * description: Synchronized 测试
 * <hr/>
 * date: 2022/7/1 10:15
 *
 * @author rui
 */
public class SynchronizedTest {
    public synchronized void test1() {
        System.out.println("aaa");
    }

    // 等价于
    public void test2() {
        synchronized (this) {
            System.out.println("bbb");
        }
    }

    public synchronized static void test3() {
        System.out.println("ccc");
    }

    // 等价于
    public static void test4() {
        synchronized (SynchronizedTest.class) {
            System.out.println("ddd");
        }
    }


}
