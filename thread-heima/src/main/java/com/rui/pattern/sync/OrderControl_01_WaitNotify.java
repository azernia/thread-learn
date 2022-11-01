package com.rui.pattern.sync;

import lombok.extern.slf4j.Slf4j;

/**
 * description: 同步模式之顺序控制(wait notify)
 * <hr/>
 * date: 2022/11/1
 * 控制线程的运行顺序
 *
 * @author rui
 */
@Slf4j(topic = "rui.OrderControl")
public class OrderControl_01_WaitNotify {
    private static final Object OBJECT = new Object();

    /**
     * 标识 t2 是否通过
     */
    private static boolean t2Ran = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (OBJECT) {
                while (!t2Ran) {
                    try {
                        OBJECT.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("t1");
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (OBJECT) {
                log.debug("t2");
                t2Ran = true;
                OBJECT.notifyAll();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
