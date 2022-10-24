package com.rui.mainanddaemonthread;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * description: 主线程与守护线程
 * <hr/>
 * date: 2022/10/24 13:52
 * 默认情况下 Java 进程需要等待所有线程都运行结束才结束
 * 守护线程
 * 只要费守护线程运行结束了，及时守护线程的代码没有执行完成也会强制结束
 *
 * @author rui
 */
@Slf4j(topic = "rui.MDT_Thread")
public class MDT_Thread {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            log.debug("finish");
        }, "T1");
        t1.setDaemon(true); // 默认为 false
        t1.start();
        Sleeper.sleep(1);
        log.debug("main finish");
    }
}
