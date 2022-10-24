package com.rui.threadcommonmethod;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * description: 打断线程
 * <hr/>
 * date: 2022/10/24 10:42
 * 打断阻塞状态的线程（sleep、wait、join）
 * 打断正在运行的线程
 * 错误思路
 * 1. 使用 stop，会真正的杀死线程，导致线程无法释放被锁住的共享资源
 * 2. 使用 exit，会使整个程序终止运行
 *
 * @author rui
 */
@Slf4j(topic = "rui.TCM_06_Interrupt")
public class TCM_06_Interrupt {
    public static void main(String[] args) {
        test2();
    }


    /**
     * 打断正在运行的线程
     */
    public static void test2() {
        Thread t1 = new Thread(() -> {
            while (true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    log.debug("被打断了");
                    break;
                }
            }
        }, "t1");
        t1.start();
        log.debug("interrupt");
        Sleeper.sleep(1);
        t1.interrupt(); // 不会影响线程的正常运行
    }

    /**
     * 打断阻塞状态的线程
     */
    public static void test1() {
        Thread t1 = new Thread(() -> {
            log.debug("sleeping....");
            Sleeper.sleep(5);   // sleep wait join 会重置打断标记，重置为 false
        }, "t1");
        t1.start();
        log.debug("interrupt");
        Sleeper.sleep(1);
        t1.interrupt(); // 抛出打断异常，并添加打断标记
        log.debug("interrupt tag: {}", t1.isInterrupted());
    }
}
