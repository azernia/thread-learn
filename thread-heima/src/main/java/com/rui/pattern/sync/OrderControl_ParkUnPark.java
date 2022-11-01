package com.rui.pattern.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * description: 同步模式之顺序控制（park unpark）
 * <hr/>
 * date: 2022/11/1
 *
 * @author rui
 */
@Slf4j(topic = "rui.OrderControl_ParkUnPark")
public class OrderControl_ParkUnPark {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("t1");
        }, "t1");
        t1.start();
        new Thread(() -> {
            log.debug("t2");
            LockSupport.unpark(t1);
        }, "t2").start();
    }

}
