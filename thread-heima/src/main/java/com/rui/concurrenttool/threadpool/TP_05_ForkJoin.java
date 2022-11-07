package com.rui.concurrenttool.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * description: fork join 线程池
 * <hr/>
 * date: 2022/11/7
 * <hr/>
 * fork join 适用于 CPU 密集型的任务
 *
 * @author rui
 */
@Slf4j(topic = "rui.TP_05_ForkJoin")
public class TP_05_ForkJoin {
    public static void main(String[] args) {
        // 创建线程池 无参默认创建 CPU 核心个数的线程池
        ForkJoinPool pool = new ForkJoinPool(4);
        Integer invoke = pool.invoke(new MyTask(5));
        System.out.println(invoke);

        // new MyTask(5) new MyTask(4) new MyTask(3) new MyTask(2) new MyTask(1)
    }
}

/**
 * RecursiveTask 有返回值使用
 * RecursiveAction 无返回值使用
 * MyTask 计算 1~n 的整数和
 */
@Slf4j(topic = "rui.MyTask")
class MyTask extends RecursiveTask<Integer> {

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "{" + n + '}';
    }

    @Override
    protected Integer compute() {
        // 如果 n 已经为 1，可以求得结果了
        if (n == 1) {
            log.debug("join() {}", n);
            return n;
        }

        // 将任务进行拆分(fork)
        AddTask1 t1 = new AddTask1(n - 1);
        t1.fork();
        log.debug("fork() {} + {}", n, t1);

        // 合并(join)结果
        int result = n + t1.join();
        log.debug("join() {} + {} = {}", n, t1, result);
        return result;
    }
}

@Slf4j(topic = "rui.AddTask")
class AddTask1 extends RecursiveTask<Integer> {

    int n;

    public AddTask1(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "{" + n + '}';
    }

    @Override
    protected Integer compute() {
        if (n == 1) {
            log.debug("join() {}", n);
            return n;
        }
        AddTask1 t1 = new AddTask1(n - 1);

        t1.fork();
        log.debug("fork() {} + {}", n, t1);
        int result = n + t1.join();
        log.debug("join() {} + {} = {}", n, t1, result);
        return result;
    }
}

@Slf4j(topic = "rui.AddTask")
class AddTask2 extends RecursiveTask<Integer> {

    int begin;
    int end;

    public AddTask2(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "{" + begin + "," + end + '}';
    }

    @Override
    protected Integer compute() {
        if (begin == end) {
            log.debug("join() {}", begin);
            return begin;
        }
        if (end - begin == 1) {
            log.debug("join() {} + {} = {}", begin, end, end + begin);
            return end + begin;
        }
        int mid = (end + begin) / 2;

        AddTask2 t1 = new AddTask2(begin, mid - 1);
        t1.fork();
        AddTask2 t2 = new AddTask2(mid + 1, end);
        t2.fork();
        log.debug("fork() {} + {} + {} = ?", mid, t1, t2);

        int result = mid + t1.join() + t2.join();
        log.debug("join() {} + {} + {} = {}", mid, t1, t2, result);
        return result;
    }
}

@Slf4j(topic = "rui.AddTask")
class AddTask3 extends RecursiveTask<Integer> {

    int begin;
    int end;

    public AddTask3(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "{" + begin + "," + end + '}';
    }

    @Override
    protected Integer compute() {
        if (begin == end) {
            log.debug("join() {}", begin);
            return begin;
        }
        if (end - begin == 1) {
            log.debug("join() {} + {} = {}", begin, end, end + begin);
            return end + begin;
        }
        int mid = (end + begin) / 2;

        AddTask3 t1 = new AddTask3(begin, mid);
        t1.fork();
        AddTask3 t2 = new AddTask3(mid + 1, end);
        t2.fork();
        log.debug("fork() {} + {} = ?", t1, t2);

        int result = t1.join() + t2.join();
        log.debug("join() {} + {} = {}", t1, t2, result);
        return result;
    }
}
