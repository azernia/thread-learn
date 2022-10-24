package com.rui.sharingmodel;

import lombok.extern.slf4j.Slf4j;

/**
 * description: 共享模型 管程 悲观锁
 * <hr/>
 * date: 2022/10/24 15:32
 * synchronized
 *  保证了临界区代码的原子性
 * 临界区
 *  共享资源在多线程中读写的代码块
 *
 * @author rui
 */
@Slf4j(topic = "rui.SM_01_Monitor")
public class SM_01_Monitor {
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (SM_01_Monitor.class) {
                for (int i = 0; i < 5000; i++) {
                    count++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (SM_01_Monitor.class) {
                for (int i = 0; i < 5000; i++) {
                    count--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("count = {}", count);
    }
}
