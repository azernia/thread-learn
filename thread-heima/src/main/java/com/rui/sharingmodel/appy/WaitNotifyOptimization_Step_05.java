package com.rui.sharingmodel.appy;

import lombok.extern.slf4j.Slf4j;

import static com.rui.utils.Sleeper.sleep;

/**
 * description: wait notify 优化 05
 * <hr/>
 * date: 2022/10/25
 * notify 唤醒了错误的线程（虚假唤醒）
 * 真确使用姿势
 * synchronized(lock) {
 *  while(条件不成立) {
 *      lock.wait();
 *  }
 *  // 干活
 * }
 * //另一个线程
 * synchronized(lock) {
 *  lock.notifyAll();
 * }
 *
 *
 * @author rui
 */
@Slf4j(topic = "rui.WaitNotifyOptimization_Step_05")
public class WaitNotifyOptimization_Step_05 {
    private static final Object ROOM = new Object();

    private static boolean hasCigarette = false;    // 是否有烟

    private static boolean hasTakeout = false;

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (ROOM) {
                log.debug("有烟没？[{}]", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    try {
                        ROOM.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活");
                }
            }
        }, "小南").start();

        new Thread(() -> {
            synchronized (ROOM) {
                log.debug("外卖送到没？[{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没外卖，先歇会！");
                    try {
                        ROOM.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("外卖送到没？[{}]", hasTakeout);
                if (hasTakeout) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活");
                }
            }
        }, "小女").start();

        sleep(1);
        new Thread(() -> {
            synchronized (ROOM) {
                hasTakeout = true;
                log.debug("外卖到了噢！");
                ROOM.notifyAll();
            }
        }, "送外卖的").start();
    }
}
