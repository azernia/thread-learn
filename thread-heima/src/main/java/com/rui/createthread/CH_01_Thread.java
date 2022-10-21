package com.rui.createthread;

import lombok.extern.slf4j.Slf4j;

/**
 * description: 通过Thread创建线程
 * <hr/>
 * date: 2022/10/21 14:57
 * 1. 通过匿名内部类的方式创建(可使用Lambda表达式)
 *
 * @author rui
 */
@Slf4j(topic = "rui.ch_01_thread")
public class CH_01_Thread {
    public static void main(String[] args) {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                log.info("thread is run");
            }
        };
        Thread thread2 = new Thread(() -> log.info("thread is run by lambda"));
        thread1.setName("thread by Anonymous inner class");
        thread1.start();
        thread2.setName("thread by lambda");
        thread2.start();
        log.info("main thread is run");
    }
}
