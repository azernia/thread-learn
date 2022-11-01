package com.rui.sharingmodel.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * description: reentrant lock
 * <hr/>
 * date: 2022/10/31
 * 相对于 synchronized
 * 可中断
 * 可设置超时时间
 * 可设置为公平锁
 * 支持多个条件变量
 * 可重入
 *  指同一个线程首次获得了这把锁就有权利再次获得这把锁
 *  如果是不可重入锁，第二次获得时会将自己挡住
 * @author rui
 */
@Slf4j(topic = "rui.Lock_04_ReentrantLock")
public class Lock_04_ReentrantLock {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        method1();
        new Thread(() -> {
            try {
                log.debug("尝试获得锁");
                lock.lockInterruptibly();   // 打断
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("被打断了");
                return;
            }
            try {
                log.debug("获取锁");
            } finally {
                lock.unlock();
            }
        }, "interrupt lock").start();
    }

    public static void method1() {
        try {
            lock.lock();
            log.debug("execute method1");
            method2();
        } finally {
            lock.unlock();
        }
    }

    public static void method2() {
        try {
            lock.lock();
            log.debug("execute method2");
            method3();
        } finally {
            lock.unlock();
        }
    }

    public static void method3() {
        try {
            lock.lock();
            log.debug("execute method3");
        } finally {
            lock.unlock();
        }
    }

}
