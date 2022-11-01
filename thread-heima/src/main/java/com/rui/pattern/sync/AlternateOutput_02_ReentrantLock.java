package com.rui.pattern.sync;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * description: 交替输出（ReentrantLock）
 * <hr/>
 * date: 2022/11/1
 *
 * @author rui
 */
@Slf4j(topic = "rui.AlternateOutput_02_ReentrantLock")
public class AlternateOutput_02_ReentrantLock {

    public static void main(String[] args) {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition aCondition = awaitSignal.newCondition();
        Condition bCondition = awaitSignal.newCondition();
        Condition cCondition = awaitSignal.newCondition();
        new Thread(() -> awaitSignal.print("a", aCondition, bCondition)).start();
        new Thread(() -> awaitSignal.print("b", bCondition, cCondition)).start();
        new Thread(() -> awaitSignal.print("c", cCondition, aCondition)).start();
        Sleeper.sleep(1);
        awaitSignal.lock();
        try {
            log.debug("start print。。。。");
            aCondition.signal();
        } finally {
            awaitSignal.unlock();
        }
    }

}

class AwaitSignal extends ReentrantLock {
    private final Integer loopNumber;

    public AwaitSignal(Integer loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String content, Condition current, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                current.await();
                System.out.print(content);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}
