package com.rui.pattern.apply;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * description: 两阶段终止模式
 * <hr/>
 * date: 2022/10/24 11:03
 * 两阶段终止模式
 * 在 t1 线程中优雅的终止 t2
 * 优雅指给 t2 料理后事的机会
 *
 * @author rui
 */
@Slf4j(topic = "rui.TwoPhaseTermination")
public class TwoPhaseTermination {
    private Thread monitor;

    /**
     * 启动监控线程
     */
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                if (currentThread.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000); // 情况1 进入 catch，会重置打断标记
                    log.debug("执行监控");  // 情况2
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    currentThread.interrupt();  // 重新设置打断标记 因为 sleep 会清除打断标记
                }
            }
        }, "monitor");
        monitor.start();
    }

    /**
     * 停止监控线程
     */
    public void stop() {
        monitor.interrupt();
    }

    public static void main(String[] args) {
        TwoPhaseTermination mainMethod = new TwoPhaseTermination();
        mainMethod.start();
        Sleeper.sleep(3.5);
        mainMethod.stop();
    }
}
