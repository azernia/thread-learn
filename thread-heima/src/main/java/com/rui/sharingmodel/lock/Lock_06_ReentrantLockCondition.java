package com.rui.sharingmodel.lock;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * description: 条件变量
 * <hr/>
 * date: 2022/11/1
 *
 * @author rui
 */
@Slf4j(topic = "rui.Lock_06_ReentrantLockCondition")
public class Lock_06_ReentrantLockCondition {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final Condition WAITE_CIGARETTE_QUEUE = LOCK.newCondition(); // 创建新的条件变量（休息室）

    private static final Condition WAITE_BREAKFAST_QUEUE = LOCK.newCondition();

    private static volatile boolean hasCigarette = false;

    private static volatile boolean hasBreakfast = false;

    public static void main(String[] args) {
        new Thread(() -> {
            LOCK.lock();
            try {
                while (!hasCigarette) {
                    try {
                        WAITE_CIGARETTE_QUEUE.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("抽到烟了");
            } finally {
                LOCK.unlock();
            }
        }, "抽烟").start();
        new Thread(() -> {
            LOCK.lock();
            try {
                while (!hasBreakfast) {
                    try {
                        WAITE_BREAKFAST_QUEUE.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("吃到早餐了");
            } finally {
                LOCK.unlock();
            }
        }, "吃早餐").start();

        Sleeper.sleep(1);
        sendCigarette();
        Sleeper.sleep(1);
        sendBreakfast();
    }

    private static void sendCigarette() {
        LOCK.lock();    // 获得锁
        try {
            log.debug("烟来了。。。。");
            hasCigarette = true;
            WAITE_CIGARETTE_QUEUE.signal();
        } finally {
            LOCK.unlock();
        }
    }

    private static void sendBreakfast() {
        LOCK.lock();    // 获得锁
        try {
            log.debug("早餐来了。。。。");
            hasBreakfast = true;
            WAITE_BREAKFAST_QUEUE.signal();
        } finally {
            LOCK.unlock();
        }
    }
}
