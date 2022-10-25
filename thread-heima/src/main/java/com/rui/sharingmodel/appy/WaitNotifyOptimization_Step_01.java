package com.rui.sharingmodel.appy;

import lombok.extern.slf4j.Slf4j;

import static com.rui.utils.Sleeper.sleep;

/**
 * description: wait notify 优化 01
 * <hr/>
 * date: 2022/10/25
 *
 * @author rui
 */
@Slf4j(topic = "rui.WaitNotifyOptimization_Step_01")
public class WaitNotifyOptimization_Step_01 {
    private static final Object ROOM = new Object();

    private static boolean hasCigarette = false;    // 是否有烟

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (ROOM) {
                log.debug("有烟没？[{}]", hasCigarette);
                if (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    sleep(2);
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                }
            }
        }, "小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (ROOM) {
                    log.debug("可以开始干活了");
                }
            }, "其它人").start();
        }

        sleep(1);
        new Thread(() -> {
            // 这里能不能加 synchronized (ROOM)？ 不能
            synchronized (ROOM) {
                hasCigarette = true;
                log.debug("烟到了噢！");
            }
        }, "送烟的").start();
    }
}
