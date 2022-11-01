package com.rui.sharingmodel;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * description: 可见性
 * <hr/>
 * date: 2022/11/1
 * 适用一个写线程多个读线程
 *
 * @author rui
 */
@Slf4j(topic = "rui.SM_06_Visibility")
public class SM_06_Visibility {

    // volatile 易变关键字
    private volatile static boolean run = true; // 使线程不会将该变量写入自己的工作缓存，每次获取必须在主存中获取

    // private static boolean run = true;

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (run);
        });
        thread.start();

        Sleeper.sleep(1);
        log.debug("stop thread");
        run = false;
    }

}
