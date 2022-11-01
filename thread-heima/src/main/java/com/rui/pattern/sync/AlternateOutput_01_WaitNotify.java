package com.rui.pattern.sync;

import lombok.extern.slf4j.Slf4j;

/**
 * description: 交替输出（Wait Notify）
 * <hr/>
 * date: 2022/11/1
 *
 * @author rui
 */
@Slf4j(topic = "rui.AlternateOutput_WaitNotify")
public class AlternateOutput_01_WaitNotify {
    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify(1, 5);
        new Thread(() -> waitNotify.print("a", 1, 2), "t1").start();
        new Thread(() -> waitNotify.print("b", 2, 3), "t2").start();
        new Thread(() -> waitNotify.print("c", 3, 1), "t3").start();
    }
}

class WaitNotify {
    /**
     * 等待标记
     */
    private Integer flag;

    /**
     * 循环数
     */
    private final Integer loopNumber;

    public WaitNotify(Integer flag, Integer loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    public synchronized void print(String content, int waitFlag, Integer nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            while (flag != waitFlag) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print(content);
            flag = nextFlag;
            this.notifyAll();
        }
    }
}
