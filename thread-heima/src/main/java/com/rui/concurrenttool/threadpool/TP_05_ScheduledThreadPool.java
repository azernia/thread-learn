package com.rui.concurrenttool.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

import static com.rui.utils.Sleeper.sleep;

/**
 * description: 任务调度线程池
 * <hr/>
 * date: 2022/11/4
 * <hr/>
 *
 * @author rui
 */
@Slf4j(topic = "rui.TP_05_ScheduledThreadPool")
public class TP_05_ScheduledThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 需要自己主动处理异常
        // ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        // pool.schedule(() -> {
        //     try {
        //         log.debug("task1");
        //         int i = 1 / 0;
        //     } catch (Exception e) {
        //         log.error("error:", e);
        //     }
        // }, 1, TimeUnit.SECONDS);

        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<?> future = pool.submit(() -> {
            try {
                log.debug("task1");
                int i = 1 / 0;
            } catch (Exception e) {
                log.error("error:", e);
            }
        });
        log.debug("result: {}", future.get());
    }

    private static void method3() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("start...");
        // 重复执行
        pool.scheduleAtFixedRate(() -> {
            log.debug("running...");
        }, 1, 1, TimeUnit.SECONDS);
    }

    private static void method2(ScheduledExecutorService pool) {
        // 延时执行
        pool.schedule(() -> {
            log.debug("task1");
            int i = 1 / 0;
        }, 1, TimeUnit.SECONDS);

        pool.schedule(() -> {
            log.debug("task2");
        }, 1, TimeUnit.SECONDS);
    }

    private static void method1() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 1");
                sleep(2);
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 2");
            }
        };

        log.debug("start...");
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }

}
