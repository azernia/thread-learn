package com.rui.sharedmodel;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * description: 共享模型测试
 * <hr/>
 * date: 2022/6/30 16:40
 *
 * @author rui
 */
@Slf4j(topic = "rui.SharedTest")
public class SharedTest {

    private static int count = 0;

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 阻塞式
     */
    @Test
    public void testSharedResource() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (SharedTest.class) {
                for (int i = 0; i < 5000; i++) {
                    count++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (SharedTest.class) {
                for (int i = 0; i < 5000; i++) {
                    count--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.info("count = {}", count);
    }

    /**
     * 非阻塞式 原子变量
     */
    @Test
    public void testSharedResourceAtomic() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                atomicInteger.getAndIncrement();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                atomicInteger.getAndDecrement();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.info("count = {}", atomicInteger.get());
    }

    @Test
    public void testSharedResourceByClass() throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decrement();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.info("counter = {}", room.getCounter());
    }
}

class Room {
    private Integer counter = 0;

    public Integer getCounter() {
        return counter;
    }

    public void increment() {
        synchronized (this) {
            counter++;
        }
    }

    public void decrement() {
        synchronized (this) {
            counter--;
        }
    }
}
