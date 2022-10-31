package com.rui.sharingmodel;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * description: park unpark
 * <hr/>
 * date: 2022/10/31
 * unpark 可以在 park 前调用
 *
 * @author rui
 */
@Slf4j(topic = "rui.SM_05_Park_Unpark")
public class SM_05_Park_Unpark {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            Sleeper.sleep(1);
            log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
        }, "t1");
        t1.start();
        Sleeper.sleep(2);
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}
