package com.rui.sharingmodel;

import lombok.extern.slf4j.Slf4j;

/**
 * description: synchronized 加载方法上
 * <hr/>
 * date: 2022/10/25 09:15
 *
 * @author rui
 */
@Slf4j(topic = "rui.SM_03_SynchronizedOnMethod")
public class SM_03_SynchronizedOnMethod {
    public static void main(String[] args) throws InterruptedException {
        OtherRoom otherRoom = new OtherRoom();
        Thread increment = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                otherRoom.increment();
            }
        }, "increment");
        Thread decrement = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                otherRoom.decrement();
            }
        }, "decrement");
        increment.start();
        decrement.start();
        increment.join();
        decrement.join();
        log.debug("result count = {}", otherRoom.getCount());
    }
}

class OtherRoom {
    private int count = 0;

    public synchronized void increment() {
        this.count++;
    }

    public synchronized void decrement() {
        this.count--;
    }

    public int getCount() {
        return this.count;

    }
}
