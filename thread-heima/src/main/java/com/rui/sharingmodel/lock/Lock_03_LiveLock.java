package com.rui.sharingmodel.lock;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * description: 活锁
 * <hr/>
 * date: 2022/10/31
 *
 * @author rui
 */
@Slf4j(topic = "rui.Lock_03_LiveLock")
public class Lock_03_LiveLock {

    private static volatile int count = 10;

    private static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            while (count > 0) {
                Sleeper.sleep(0.2);
                count--;
                log.debug("count: {}", count);
            }
        }, "t1").start();
        new Thread(() -> {
            while (count < 20) {
                Sleeper.sleep(0.2);
                count++;
                log.debug("count: {}", count);
            }
        }, "t2").start();
    }
}
