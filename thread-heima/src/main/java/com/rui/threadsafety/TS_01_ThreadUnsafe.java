package com.rui.threadsafety;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 线程不安全
 * <hr/>
 * date: 2022/10/25 10:11
 *
 * @author rui
 */
@Slf4j(topic = "rui.TS_01_ThreadUnsafe")
public class TS_01_ThreadUnsafe {

    public static final int THREAD_NUMBER = 2;

    public static final int LOOP_NUMBER = 200;

    public static void main(String[] args) {
        ThreadUnsafe threadUnsafe = new ThreadUnsafe();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> threadUnsafe.method1(LOOP_NUMBER), "Thread" + (i + 1)).start();
        }
    }
}

class ThreadUnsafe {
    List<String> list = new ArrayList<>();

    public void method1(int loopNumber) {
        for (int i = 0; i < loopNumber; i++) {
            method2();
            method3();
        }
    }

    public void method2() {
        list.add("1");
    }

    public void method3() {
        list.remove(0);
    }
}
