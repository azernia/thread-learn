package com.rui.threadcommonmethod;

import lombok.extern.slf4j.Slf4j;

/**
 * description: 线程的状态
 * <hr/>
 * date: 2022/10/21 17:18
 *
 * @author rui
 */
@Slf4j(topic = "rui.TCM_02_ThreadStatus")
public class TCM_02_ThreadStatus {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.info("running");
            }
        };
        System.out.println(t1.getState());
        t1.start();
        System.out.println(t1.getState());
    }
}
