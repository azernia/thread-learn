package com.rui.sharingmodel;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * description: wait notify
 * <hr/>
 * date: 2022/10/25
 * wait(long timeout)
 * 在等待时间结束后停止等待，若提前唤醒则直接唤醒
 * timeout = 0 无限等待
 * sleep 与 wait 的区别
 * 1. sleep Thread 的静态方法，wait 是 Object 的方法
 * 2. wait 需要与 synchronized 配合使用
 * 3. sleep 不会释放对象锁，wait 会释放对象锁
 * @author rui
 */
@Slf4j(topic = "rui.SM_04_WaitNotify")
public class SM_04_WaitNotify {
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (SM_04_WaitNotify.class) {
                log.debug("执行。。。。。");
                try {
                    SM_04_WaitNotify.class.wait();  // 等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其他代码。。。。。");
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (SM_04_WaitNotify.class) {
                log.debug("执行。。。。。");
                try {
                    SM_04_WaitNotify.class.wait();  // 等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其他代码。。。。。");
            }
        }, "t2").start();

        Sleeper.sleep(2);
        log.debug("唤醒线程");
        synchronized (SM_04_WaitNotify.class) {
            // SM_04_WaitNotify.class.notify();
            SM_04_WaitNotify.class.notifyAll();
        }

    }
}
