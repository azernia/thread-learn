package com.rui.practice;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * description: 练习
 * <hr/>
 * date: 2022/6/30 16:19
 *
 * @author rui
 */
@Slf4j(topic = "rui.Practice")
public class Practice {

    public void testOrdinating() {
        Thread t1 = new Thread(() -> {
            try {
                log.debug("洗水壶");
                TimeUnit.SECONDS.sleep(1);
                log.debug("烧开水");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "老王");

        Thread t2 = new Thread(() -> {
            try {
                log.debug("洗茶壶");
                TimeUnit.SECONDS.sleep(1);
                log.debug("洗茶杯");
                TimeUnit.SECONDS.sleep(2);
                log.debug("拿茶叶");
                TimeUnit.SECONDS.sleep(1);
                t1.join();
                log.debug("泡茶");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "小王");

        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        new Practice().testOrdinating();
    }

}
