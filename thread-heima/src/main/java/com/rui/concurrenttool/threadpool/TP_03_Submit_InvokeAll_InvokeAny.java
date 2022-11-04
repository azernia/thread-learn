package com.rui.concurrenttool.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * description: 线程池提交
 * <hr/>
 * date: 2022/11/4
 * <hr/>
 *
 * @author rui
 */
@Slf4j(topic = "rui.TP_03_Submit_InvokeAll_InvokeAny")
public class TP_03_Submit_InvokeAll_InvokeAny {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        method1(pool);

    }

    private static void method3(ExecutorService pool) throws InterruptedException, ExecutionException {
        String result = pool.invokeAny(Arrays.asList(
                () -> {
                    log.debug("begin 1");
                    Thread.sleep(1000);
                    log.debug("end 1");
                    return "1";
                },
                () -> {
                    log.debug("begin 2");
                    Thread.sleep(500);
                    log.debug("end 2");
                    return "2";
                },
                () -> {
                    log.debug("begin 3");
                    Thread.sleep(2000);
                    log.debug("end 3");
                    return "3";
                }
        ));
        log.debug("{}", result);
    }

    private static void method2(ExecutorService pool) throws InterruptedException {
        List<Future<String>> futures = pool.invokeAll(Arrays.asList(
                () -> {
                    log.debug("begin");
                    Thread.sleep(1000);
                    return "1";
                },
                () -> {
                    log.debug("begin");
                    Thread.sleep(500);
                    return "2";
                },
                () -> {
                    log.debug("begin");
                    Thread.sleep(2000);
                    return "3";
                }
        ));

        futures.forEach( f ->  {
            try {
                log.debug("{}", f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private static void method1(ExecutorService pool) throws InterruptedException, ExecutionException {
        Future<String> future = pool.submit(() -> {
            log.debug("running");
            Thread.sleep(1000);
            return "ok";
        });

        log.debug("{}", future.get());
    }
}
