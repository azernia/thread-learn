package com.rui.singleton;

/**
 * 与01相同
 * @author rui
 * @since 2022/5/17 10:32
 */
public class Singleton02 {
    private static final Singleton02 INSTANCE;

    static {
        INSTANCE = new Singleton02();
    }

    private Singleton02(){}

    public static Singleton02 getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        Singleton02 instance1 = Singleton02.getInstance();
        Singleton02 instance2 = Singleton02.getInstance();
        System.out.println(instance1 == instance2);
    }
}
