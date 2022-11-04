package com.rui.concurrenttool.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description: 线程池 文档（assets/并发编程）
 * <hr/>
 * date: 2022/11/4
 * <hr/>
 * corePoolSize 线程核心数（最多保留的线程数）
 * maximumPoolSize 最大线程数目 （max - core）= 救急线程
 * keepAliveTime 生存时间 - 针对救急线程
 * unit 时间单位 - 针对救急线程
 * workQueue 阻塞队列
 * threadFactory 线程工厂 - 可以在线程创建时起个好名字
 * handler 拒绝策略
 *
 * @author rui
 */
@Slf4j(topic = "rui.TP_02_ThreadPoolExecutor")
public class TP_02_ThreadPoolExecutor {
    public static void main(String[] args) {
        // 带缓存的线程池
        // ExecutorService executorService = Executors.newCachedThreadPool();
        test2();
    }

    public static void test2() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(() -> {
            log.debug("1");
            throw new RuntimeException("error occurred");
        });

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });
    }

    private static void test1() {
        // 固定大小的线程池(不会主动结束)
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private final AtomicInteger t = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "t" + t.getAndIncrement());
            }
        });
        fixedThreadPool.execute(() -> log.debug("1"));
        fixedThreadPool.execute(() -> log.debug("2"));
        fixedThreadPool.execute(() -> log.debug("3"));
    }
}
