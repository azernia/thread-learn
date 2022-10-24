package com.rui.threadcommonmethod;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * description: join 方法
 * <hr/>
 * date: 2022/10/24 09:37
 * 等待使用该放的线程结束
 *
 * @author rui
 */
@Slf4j(topic = "rui.TCM_05_Join")
public class TCM_05_Join {
    private static int r1 = 0;

    private static int r2 = 0;

    public static void main(String[] args) throws InterruptedException {
        // test1();
        // test2();
        test3();
    }

    public static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Sleeper.sleep(2);
            r1 = 10;
        }, "t1");
        long startTime = System.currentTimeMillis();
        t1.start();
        log.debug("join start");
        t1.join(1000);  // 最多等待 1000 ms 若提前结束 join 也会结束
        log.debug("join end");
        long endTime = System.currentTimeMillis();
        log.debug("r1: {}, r2: {}, time interval: {}", r1, r2, endTime - startTime);
    }

    public static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Sleeper.sleep(1);
            r1 = 10;
        }, "t1");

        Thread t2 = new Thread(() -> {
            Sleeper.sleep(2);
            r2 = 20;
        }, "t2");
        t1.start();
        t2.start();
        long startTime = System.currentTimeMillis();
        log.debug("join begin ");
        t1.join();
        log.debug("t1 join complete");
        t2.join();
        log.debug("t2 join complete");
        long endTime = System.currentTimeMillis();
        log.debug("r1: {}, r2: {}, time interval: {}", r1, r2, endTime - startTime);

    }

    public static void test1() throws InterruptedException {
        log.debug("start");

        Thread t1 = new Thread(() -> {
            log.debug("t1 start");
            Sleeper.sleep(1);
            log.debug("t1 sleep finish");
            r1 = 10;
        }, "t1");
        t1.start();
        t1.join();
        log.debug("result is {}", r1);
        log.debug("finish");
    }
}
