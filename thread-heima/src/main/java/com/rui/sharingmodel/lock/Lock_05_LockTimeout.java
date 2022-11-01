package com.rui.sharingmodel.lock;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * description: 锁超时
 * <hr/>
 * date: 2022/11/1
 *
 * @author rui
 */
@Slf4j(topic = "rui.Lock_05_LockTimeout")
public class Lock_05_LockTimeout {

    private final static ReentrantLock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("尝试获得锁");
            try {
                if (LOCK.tryLock(1, TimeUnit.SECONDS)) {    // 设置尝试时间
                    try {
                        log.debug("成功");
                    } finally {
                        LOCK.unlock();
                    }
                } else {
                    log.debug("失败");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // if (LOCK.tryLock()) {
            //     try {
            //         log.debug("成功");
            //     } finally {
            //         LOCK.unlock();
            //     }
            // } else {
            //     log.debug("失败");
            // }
        }, "t1");

        LOCK.lock();
        log.debug("{} 获得锁", Thread.currentThread().getName());
        Sleeper.sleep(0.5);
        log.debug("{} 释放锁", Thread.currentThread().getName());
        LOCK.unlock();

        t1.start();
    }

}
