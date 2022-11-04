package com.rui.concurrenttool.threadpool.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.SynchronousQueue;

import static com.rui.utils.Sleeper.sleep;

/**
 * description: 同步队列
 * <hr/>
 * date: 2022/11/4
 * <hr/>
 * 没有容量，存入一个之后不取走是不能继续存入的
 *
 * @author rui
 */
@Slf4j(topic = "rui.Q_01SynchronousQueue")
public class Q_01SynchronousQueue {
    public static void main(String[] args) {
        SynchronousQueue<Integer> integers = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                log.debug("putting {} ", 1);
                integers.put(1);
                log.debug("{} putted...", 1);

                log.debug("putting...{} ", 2);
                integers.put(2);
                log.debug("{} putted...", 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        sleep(1);

        new Thread(() -> {
            try {
                log.debug("taking {}", 1);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();

        sleep(1);

        new Thread(() -> {
            try {
                log.debug("taking {}", 2);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t3").start();
    }
}
