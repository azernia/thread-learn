package com.rui.concurrenttool.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * description: 自定义线程池
 * <hr/>
 * date: 2022/11/3
 *
 * @author rui
 */
@Slf4j(topic = "rui.TP_01_CustomThreadPool")
public class TP_01_CustomThreadPool {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(1,
                1000, TimeUnit.MILLISECONDS, 1, (queue, task)->{
            // 1. 死等
//            queue.put(task);
            // 2) 带超时等待
           queue.offer(task, 500, TimeUnit.MILLISECONDS);
            // 3) 让调用者放弃任务执行
//            log.debug("放弃{}", task);
            // 4) 让调用者抛出异常
//            throw new RuntimeException("任务执行失败 " + task);
            // 5) 让调用者自己执行任务
            // task.run();
        });
        for (int i = 0; i < 4; i++) {
            int j = i;
            threadPool.execute(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("{}", j);
            });
        }
    }
}

/**
 * 拒绝策略
 */
@FunctionalInterface
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}

@Slf4j(topic = "rui.ThreadPool")
class ThreadPool {
    /**
     * 任务队列
     */
    private final BlockingQueue<Runnable> taskQueue;

    /**
     * 线程集合
     */
    private final HashSet<Worker> workers = new HashSet<>();

    /**
     * 线程数
     */
    private final int coreSize;

    /**
     * 获取任务的超时时间
     */
    private final long timeout;

    /**
     * 时间单位
     */
    private final TimeUnit timeUnit;

    /**
     * 拒绝策略
     */
    private final RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCapacity, RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(queueCapacity);
        this.rejectPolicy = rejectPolicy;
    }

    class Worker extends Thread {

        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            // 执行任务
            // task 不为空执行任务
            // task 执行完毕从任务队列中获取任务并执行
            // while (task != null || (task = taskQueue.take()) != null) {  未设置超时时间，一直等待
            while (task != null || (task = taskQueue.poll(timeout, timeUnit)) != null) {
                try {
                    log.debug("正在执行 task: {}", task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                log.debug("移除 worker: {}", this);
                workers.remove(this);
            }

        }
    }

    /**
     * 执行任务
     */
    public void execute(Runnable task) {
        // 保证线程安全
        synchronized (workers) {
            // 任务数未超过 coreSize 交给 worker 对象
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.debug("新增 worker: {}, task: {}", worker, task);
                workers.add(worker);
                worker.start();
            } else {
                /*
                    任务队列满后的处理方式（使用策略模式）
                    1. 死等
                    2. 带超时的等待
                    3. 放弃任务执行
                    4. 抛出异常
                    5. 让调用者自己执行任务
                 */
                // 超过 coreSize 加入任务队列
                // taskQueue.put(task);    // 死等
                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }

}

/**
 * 阻塞队列
 */
@Slf4j(topic = "rui.BlockingQueue")
class BlockingQueue<T> {
    /**
     * 任务列表
     * 双向队列
     */
    private final Deque<T> queue = new ArrayDeque<>();

    /**
     * 锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 生产者条件变量
     */
    private final Condition fullWaitSet = lock.newCondition();

    /**
     * 消费者条件变量
     */
    private final Condition emptyWaitSet = lock.newCondition();

    /**
     * 容量
     */
    private final int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    // 带超时阻塞获取
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            // 将 timeout 统一转换为 纳秒
            long nanos = unit.toNanos(timeout);
            while (queue.isEmpty()) {
                try {
                    // 返回值是剩余时间
                    if (nanos <= 0) {
                        return null;
                    }
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    // 阻塞获取
    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    // 阻塞添加
    public void put(T task) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                try {
                    log.debug("等待加入任务队列 {} ...", task);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列 {}", task);
            queue.addLast(task);
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    // 带超时时间阻塞添加
    public boolean offer(T task, long timeout, TimeUnit timeUnit) {
        lock.lock();
        try {
            long nanos = timeUnit.toNanos(timeout);
            while (queue.size() == capacity) {
                try {
                    if(nanos <= 0) {
                        return false;
                    }
                    log.debug("等待加入任务队列 {} ...", task);
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列 {}", task);
            queue.addLast(task);
            emptyWaitSet.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            // 判断队列是否满
            if(queue.size() == capacity) {
                rejectPolicy.reject(this, task);
            } else {  // 有空闲
                log.debug("加入任务队列 {}", task);
                queue.addLast(task);
                emptyWaitSet.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
