package com.rui.pattern.apply;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * description: 两阶段终止模式(volatile)
 * <hr/>
 * date: 2022-11-01
 * 两阶段终止模式
 * 在 t1 线程中优雅的终止 t2
 * 优雅指给 t2 料理后事的机会
 *
 * @author rui
 */
@Slf4j(topic = "rui.TwoPhaseTermination")
public class TwoPhaseTerminationByVolatile {
    private Thread monitor;

    private volatile boolean stop;

    private boolean started = false;

    /**
     * 启动监控线程
     */
    public void start() {
        synchronized (this) {
            // 只创建一个监控线程
            if (started) {
                return;
            }
            started = true;
            monitor = new Thread(() -> {
                while (true) {
                    if (stop) {
                        log.debug("料理后事");
                        break;
                    }
                    try {
                        Thread.sleep(1000); // 情况1 进入 catch，会重置打断标记
                        log.debug("执行监控");  // 情况2
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "monitor");
            monitor.start();
        }
    }

    /**
     * 停止监控线程
     */
    public void stop() {
        stop = true;
        monitor.interrupt();    // 打断睡眠
    }

    public static void main(String[] args) {
        TwoPhaseTerminationByVolatile mainMethod = new TwoPhaseTerminationByVolatile();
        mainMethod.start();
        Sleeper.sleep(3.5);
        mainMethod.stop();
    }
}
