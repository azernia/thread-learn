package com.rui.threadcommonmethod.apply;

import java.util.concurrent.TimeUnit;

/**
 * description: sleep 方法的简单应用
 * <hr/>
 * date: 2022/10/21 17:50
 * 防止 CPU 占用 100%
 *
 * @author rui
 */
public class SleepApply {
    public static void main(String[] args) {
        Runnable task = () -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        new Thread(task, "task").start();
    }
}
