package com.rui.pattern.sync;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * description: 交替输出（park unpark）
 * <hr/>
 * date: 2022/11/1
 *
 * @author rui
 */
@Slf4j(topic = "rui.AlternateOutput_03_ParkUnpark")
public class AlternateOutput_03_ParkUnpark {

    private static Thread t1;
    private static Thread t2;
    private static Thread t3;


    public static void main(String[] args) {
        ParkUnpark parkUnpark = new ParkUnpark(5);
        t1 = new Thread(() -> parkUnpark.print("a", t2));
        t2 = new Thread(() -> parkUnpark.print("b", t3));
        t3 = new Thread(() -> parkUnpark.print("c", t1));
        t1.start();
        t2.start();
        t3.start();
        Sleeper.sleep(1);
        LockSupport.unpark(t1);
    }
}

class ParkUnpark {
    private final Integer loopNumber;

    public ParkUnpark(Integer loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String content, Thread next) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(content);
            LockSupport.unpark(next);
        }
    }
}
