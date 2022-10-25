package com.rui.threadsafety;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 线程安全
 * <hr/>
 * date: 2022/10/25 10:18
 *
 * @author rui
 */
@Slf4j(topic = "rui.TS_02_ThreadSafe")
public class TS_02_ThreadSafe {

    public static final int THREAD_NUMBER = 2;

    public static final int LOOP_NUMBER = 200;

    public static void main(String[] args) {
        ThreadSafe threadSafe = new ThreadSafe();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> threadSafe.method1(LOOP_NUMBER), "Thread-" + (i + 1)).start();
        }
    }

}

class ThreadSafe {
    public final void method1(int loopNumber) {
        List<String> list = new ArrayList<>();  // 每个线程会创建不同的对象
        for (int i = 0; i < loopNumber; i++) {
            method2(list);
            method3(list);
        }
    }

    private void method2(List<String> list) {
        list.add("1");
    }

    private void method3(List<String> list) {
        list.remove(0);
    }
}
