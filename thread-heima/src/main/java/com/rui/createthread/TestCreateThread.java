package com.rui.createthread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * description: 测试创建线程
 * <hr/>
 * date: 2022/6/28 14:04
 *
 * @author rui
 */
@Slf4j(topic = "rui.TestCreateThread")
public class TestCreateThread {

    @Test
    public void createByThread() {
        Thread thread = new Thread(() -> {
            log.info("hello thread");
        });
        thread.setName("t1");
        thread.start();
        log.info("run in main");
    }

    @Test
    public void createByRunnable() {
        Runnable runnable = () -> {
          log.info("hello runnable thread");
        };
        new Thread(runnable).start();
        log.info("run in main");
    }

    @Test
    public void createByFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
           log.info("hello future task");
           Thread.sleep(1000);
           return 100;
        });
        new Thread(task, "t1").start();
        log.info("task result {}", task.get());
    }

}
