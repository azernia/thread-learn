package com.rui.threadcommonmethod;

import lombok.extern.slf4j.Slf4j;

/**
 * description: yield 方法
 * <hr/>
 * date: 2022/10/21 17:36
 * 从 RUNNING -> RUNNABLE
 * 依赖于系统的任务调度器
 *
 * @author rui
 */
@Slf4j(topic = "rui.TCM_04_Yield_Priority")
public class TCM_04_Yield_Priority {
    public static void main(String[] args) {
        Runnable task1 = () -> {
            int count = 0;
            while (true) {
                log.info("t1----->{}", count++);
            }
        };
        Runnable task2 = () -> {
            int count = 0;
            while (true) {
                Thread.yield();
                log.info("t2----->{}", count++);
            }
        };
        Thread t1 = new Thread(task1, "t1");
        Thread t2 = new Thread(task2, "t2");
        t1.start();
        t2.start();
        // t1.setPriority(Thread.MIN_PRIORITY);
        // t2.setPriority(Thread.MAX_PRIORITY);
    }
}
