package com.rui.pattern.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * description: 同步模式之顺序控制（ReentrantLock）
 * <hr/>
 * date: 2022/11/1
 *
 * @author rui
 */
@Slf4j(topic = "rui.OrderControl_02_ReentrantLock")
public class OrderControl_02_ReentrantLock {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final Condition ORDER_SET = LOCK.newCondition();

    private static boolean t2Ran = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LOCK.lock();
            try {
                while (!t2Ran) {
                    try {
                        ORDER_SET.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("t1");
            } finally {
                LOCK.unlock();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            LOCK.lock();
            try {
                log.debug("t2");
                t2Ran = true;
                ORDER_SET.signal();
            } finally {
                LOCK.unlock();
            }
        }, "t2");

        t1.start();
        t2.start();
    }

}
