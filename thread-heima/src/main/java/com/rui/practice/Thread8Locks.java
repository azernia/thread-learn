package com.rui.practice;

import com.rui.utils.SleepUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * description: 练习 线程八锁
 * <hr/>
 * date: 2022/7/1 10:20
 *
 * @author rui
 */
@Slf4j(topic = "rui.Thread8Locks")
public class Thread8Locks {
    public static void main(String[] args) {
        Number number = new Number();
        new Thread(() -> {
            log.info("{} start", Thread.currentThread().getName());
            number.a();
        }, "t1").start();

        new Thread(() -> {
            log.info("{} start", Thread.currentThread().getName());
            number.b();
        }, "t2").start();

        new Thread(() -> {
            log.info("{} start", Thread.currentThread().getName());
            number.c();
        }, "t3").start();
    }
}

@Slf4j(topic = "rui.Number")
class Number {
    public synchronized void a() {
        SleepUtils.sleep(1);
        log.info("1");
    }

    public synchronized void b() {
        log.info("2");
    }

    public void c() {
        log.info("3");
    }
}
