package com.rui.sharingmodel;

import lombok.extern.slf4j.Slf4j;

/**
 * description:
 * <hr/>
 * date: 2022/10/24 18:02
 *
 * @author rui
 */
@Slf4j(topic = "rui.SM_01_SynchronizedByClass")
public class SM_01_SynchronizedByClass {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread increment = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.increment();
            }
        }, "increment");
        Thread decrement = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decrement();
            }
        }, "decrement");
        increment.start();
        decrement.start();
        increment.join();
        decrement.join();
        log.debug("result count = {}", room.getCount());
    }
}

class Room {
    private int count = 0;

    public void increment() {
        synchronized (this) {
            this.count++;
        }
    }

    public void decrement() {
        synchronized (this) {
            this.count--;
        }
    }

    public int getCount() {
        return this.count;

    }}
