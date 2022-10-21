package com.rui.createthread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * description: FutureTask 创建任务
 * <hr/>
 * date: 2022/10/21 16:38
 *
 * @author rui
 */
@Slf4j(topic = "rui.ch_03_future_task")
public class CH_03_FutureTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task1 = new FutureTask<>(() -> {
            log.info("task1 running");
            TimeUnit.MICROSECONDS.sleep(1);
            return 1;
        });
        Thread thread1 = new Thread(task1, "task1");
        thread1.start();
        log.info("result value = {}", task1.get());
    }
}
