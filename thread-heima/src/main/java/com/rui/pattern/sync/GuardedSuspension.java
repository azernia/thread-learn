package com.rui.pattern.sync;

import com.rui.utils.DownLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

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
        GuardedObj guardedObj = new GuardedObj();
        // 线程1 等待 线程2 的下载结果
        new Thread(() -> {
            // 等待结果
            log.debug("等待结果");
            List<String> list = (List<String>) guardedObj.get();
            log.debug("结果大小：{}", list.size());
        }, "t1").start();

        new Thread(() -> {
            log.debug("执行下载");
            try {
                List<String> download = DownLoader.download();
                guardedObj.complete(download);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2").start();
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
