package com.rui.apply;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * description: 线程基本应用
 * <hr/>
 * date: 2022/10/24 15:19
 * 模拟两个人写作烧水
 *
 * @author rui
 */
@Slf4j(topic = "rui.Apply_01_BaseApply")
public class Apply_01_BaseApply {
    public static void main(String[] args) {
        Thread boilWaterAndWashKettle = new Thread(() -> {
            log.debug("wash kettle");
            Sleeper.sleep(1);
            log.debug("step one finished");
            log.debug("boil water");
            Sleeper.sleep(5);
        }, "boil water and wash kettle");

        Thread otherThings = new Thread(() -> {
            log.debug("wash teapot");
            Sleeper.sleep(1);
            log.debug("wash cup");
            Sleeper.sleep(1);
            log.debug("take tea");
            Sleeper.sleep(1);
            try {
                boilWaterAndWashKettle.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("make tea");
        }, "other things");

        otherThings.start();
        boilWaterAndWashKettle.start();
    }
}
