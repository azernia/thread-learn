package com.rui.threaddesign;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * description: 两阶段终止模式
 * 在一个线程 T1 中 优雅的终止线程 T2
 * 优雅指给 T2 处理后续问题的机会，不会立刻杀死
 * <hr/>
 * date: 2022/6/28 17:23
 *
 * @author rui
 */
@Slf4j(topic = "rui.TowPhaseTermination")
public class TowPhaseTermination {
    /**
     * 监控线程
     */
    private Thread monitor;

    /**
     * 启动监控线程
     */
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                if (currentThread.isInterrupted()) {
                    // 判断是否被打断
                    log.info("deal with something");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1); // 会清除打断标记
                    log.info("monitor info");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    currentThread.interrupt();  // 重新设置打断标记
                }
            }
        });
        monitor.start();
    }

    /**
     * 停止监控线程
     */
    public void stop() {
        monitor.interrupt();
    }

    @Test
    public void testTowPhaseTermination() throws InterruptedException {
        start();
        Thread.sleep(3500);
        stop();
    }
}
