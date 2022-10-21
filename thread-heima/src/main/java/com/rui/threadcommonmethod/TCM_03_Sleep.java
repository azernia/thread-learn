package com.rui.threadcommonmethod;

import lombok.extern.slf4j.Slf4j;

/**
 * description: 线程睡眠
 * <hr/>
 * date: 2022/10/21 17:24
 * 将线程从 RUNNING -> TIMED_WAITING
 * 其他线程可使用 interrupt 方法打断，抛出 InterruptedException
 * 未必立即执行
 *
 * @author rui
 */
@Slf4j(topic = "rui.TCM_03_Sleep")
public class TCM_03_Sleep {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    log.info("enter sleep");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.info("wake up");
                    throw new RuntimeException(e);
                }
            }
        };
        t1.start();
        log.info("t1's state is {}", t1.getState());

        Thread.sleep(500);
        log.info("t1's state is {}", t1.getState());

        log.info("interrupt t1");
        t1.interrupt();
    }
}
