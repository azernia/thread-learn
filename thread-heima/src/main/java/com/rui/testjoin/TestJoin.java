package com.rui.testjoin;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * description: 测试 join()
 * <hr/>
 * date: 2022/6/28 17:08
 *
 * @author rui
 */
@Slf4j(topic = "rui.TestJoin")
public class TestJoin {

    private Integer r = 0;

    @Test
    public void testJoin() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.info("t1 start");
            r = 10;
            log.info("t1 end");
        }, "t1");
        t1.start();
        t1.join();
        log.info("r = {}", r);
    }
}
