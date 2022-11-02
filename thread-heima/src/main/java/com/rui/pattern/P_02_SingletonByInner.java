package com.rui.pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * description: 静态内部类实现单例模式
 * <hr/>
 * date: 2022/11/2
 *
 * @author rui
 */
@Slf4j(topic = "rui.P_02_SingletonByInner")
public class P_02_SingletonByInner {

    public static void main(String[] args) {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance1);
        System.out.println(instance2);
    }

}

/**
 * 运用 JVM 的特性
 * 只有在调用静态内部类的时候才创建对象
 * 同时保证了线程安全
 */
class Singleton {

    private Singleton() {}

    private static class SingletonInner {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonInner.INSTANCE;
    }
}
