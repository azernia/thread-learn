package com.rui.singleton;

/**
 * 饿汉式
 * 类加载到内存后，就实例化一个单例，JVM保证内存安全
 * 简单实用 推荐
 * 唯一缺点 不管用到与否类加载时完成实例化
 * @author rui
 * @since 2022/5/17 10:32
 */
public class Singleton01 {
    private static final Singleton01 INSTANCE = new Singleton01();

    private Singleton01(){}

    public static Singleton01 getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        Singleton01 instance1 = Singleton01.getInstance();
        Singleton01 instance2 = Singleton01.getInstance();
        System.out.println(instance1 == instance2);
    }
}
