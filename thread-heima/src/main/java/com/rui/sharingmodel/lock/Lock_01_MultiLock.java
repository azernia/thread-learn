package com.rui.sharingmodel.lock;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * description: 多把锁
 * <hr/>
 * date: 2022/10/31
 *
 * @author rui
 */
@Slf4j(topic = "rui.Lock_01_MultiLock")
public class Lock_01_MultiLock {

    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();
        new Thread(bigRoom::study, "小南").start();
        new Thread(bigRoom::sleep, "小北").start();
    }

}

@Slf4j(topic = "rui.BigRoom")
class BigRoom {

    // 将锁的粒度细化
    private final Object studyRoom = new Object();
    private final Object bedroom = new Object();

    public void sleep() {
        // synchronized (this) {
        //     log.debug("sleep 2 hours");
        //     Sleeper.sleep(2);
        // }
        synchronized (bedroom) {
            log.debug("sleep 2 hours");
            Sleeper.sleep(2);
        }
    }

    public void study() {
        // synchronized (this) {
        //     log.debug("study 1 hour");
        //     Sleeper.sleep(1);
        // }
        synchronized (studyRoom) {
            log.debug("study 1 hour");
            Sleeper.sleep(1);
        }
    }

}
