package com.rui.threadstate;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;
/**
 * description: 线程状态
 * <hr/>
 * date: 2022/10/24 15:06
 * NEW RUNNABLE TERMINATED TIMED_WAITING WAITING BLOCKED
 *
 * @author rui
 */
@Slf4j(topic = "rui.ThreadState")
public class ThreadState {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("t1 running");
        }, "t1");

        Thread t2 = new Thread(() -> {
            while (true);
        }, "t2");
        t2.start();


        Thread t3 = new Thread(() ->{
            log.debug("t3 running");
        }, "t3");
        t3.start();


        Thread t4 = new Thread(() -> {
            synchronized (ThreadState.class) {
                Sleeper.sleep(1000);
            }
        }, "t4");
        t4.start();


        Thread t5 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t5");
        t5.start();


        Thread t6 = new Thread(() -> {
            synchronized (ThreadState.class) {
                Sleeper.sleep(1000);
            }
        }, "t6");
        t6.start();

        Sleeper.sleep(0.5);

        log.debug("t1's status is {}", t1.getState());
        log.debug("t2's status is {}", t2.getState());
        log.debug("t3's status is {}", t3.getState());
        log.debug("t4's status is {}", t4.getState());
        log.debug("t5's status is {}", t5.getState());
        log.debug("t6's status is {}", t6.getState());
    }
}
