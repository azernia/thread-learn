package com.rui.pattern.sync;

import lombok.extern.slf4j.Slf4j;

/**
 * description: 同步模式之保护性暂停
 * <hr/>
 * date: 2022/10/25
 *
 * @author rui
 */
@Slf4j(topic = "rui.GuardedSuspension")
public class GuardedSuspension {

    public static void main(String[] args) {
        // 线程1 等待 线程2 的下载结果
    }

}

class GuardedObj {
    /**
     * 结果
     */
    private Object response;

    /**
     * 获取结果
     * @return object
     */
    public Object get() {
        synchronized (this) {
            while (response == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return this.response;
        }
    }

    /**
     * 产生结果
     */
    public void complete(Object response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }
}
