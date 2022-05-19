package com.rui.threadPool;

import java.util.concurrent.*;

/**
 * public ThreadPoolExecutor(
 *  int corePoolSize, 核心线程数 创建多少个线程执行任务
 *  int maximumPoolSize, 最大线程数 线程池中最大拥有的线程数
 *  long keepAliveTime, 非核心线程存活时间
 *  TimeUnit unit, keepAliveTime 的单位
 *  BlockingQueue<Runnable> workQueue, 阻塞(工作)队列
 *  ThreadFactory threadFactory, 线程工厂
 *  RejectedExecutionHandler handler 拒绝策略 核心线程非核心线程都非空闲时间执行
 * )
 * create date 2022/5/19 10:13
 *
 * @author rui
 */
public class ThreadPool_01 {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
          1,
          2,
          500,
          TimeUnit.SECONDS,
          new LinkedBlockingDeque<>(),
            (runnable) -> {
                Thread t = new Thread(runnable);
                t.setName("test");
                return t;
            },
            new ThreadPoolExecutor.AbortPolicy()
        );
        // 执行 Runnable 任务
        executor.execute(() -> {
            System.out.println("ha ha ha");
        });
        // 执行 Callable 任务 获取 future
        // 有返回结果
        Future<Integer> aaa = executor.submit(
                () -> {
                    System.out.println("aaa");
                    return 1;
                }
        );
        try {
            Integer integer = aaa.get();
            System.out.println("integer = " + integer);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
