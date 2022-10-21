package com.rui.threadcommonmethod;

import lombok.extern.slf4j.Slf4j;

/**
 * description: start() run() 方法
 * <hr/>
 * date: 2022/10/21 17:09
 *
 * @author rui
 */
@Slf4j(topic = "rui.TCM_01_Start_Run")
public class TCM_01_Start_Run {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.info("t1 is running");
            }
        };
        t1.start(); // t1   start 不能多次调用
        t1.run();   // main

    }
}
