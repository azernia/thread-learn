package com.rui.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * description: 线程安全的单例模式
 * <hr/>
 * date: 2022/11/1
 *
 * @author rui
 */
@Slf4j(topic = "rui.P_01_ThreadSafetySingleton")
public class P_01_ThreadSafetySingleton {

    public static void main(String[] args) {
        A a1 = A.getInstance();
        A a2 = A.getInstance();
        System.out.println(a1.equals(a2));
    }

}

class A {

    private static A INSTANCE = null;

    private A() {}

    public static synchronized A getInstance() {
        if (Objects.nonNull(INSTANCE)) {
            return INSTANCE;
        }
        INSTANCE = new A();
        return INSTANCE;
    }
}
