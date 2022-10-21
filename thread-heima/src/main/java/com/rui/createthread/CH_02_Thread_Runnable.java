package com.rui.createthread;

import lombok.extern.slf4j.Slf4j;

/**
 * description: 通过 Thread 和 Runnable 创建线程
 * <hr/>
 * date: 2022/10/21 14:57
 * 推荐使用
 * @author rui
 */
@Slf4j(topic = "rui.ch_01_thread_runnable")
public class CH_02_Thread_Runnable {
    public static void main(String[] args) {
        // 通富过匿名内部类
        // Runnable runnable = new Runnable() {
        //     @Override
        //     public void run() {
        //         log.info("create by runnable thread is started");
        //     }
        // };
        Runnable runnable = () -> log.info("create by runnable thread is started");
        Thread thread = new Thread(runnable, "create by runnable");
        thread.start();
    }
}
