package com.rui.utils;

import java.util.concurrent.TimeUnit;

/**
 * description: 线程睡眠工具类
 * <hr/>
 * date: 2022/10/24 09:54
 *
 * @author rui
 */
public class Sleeper {
    private Sleeper() {}

    /**
     * 睡眠
     *
     * @param seconds 秒
     */
    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 睡眠
     *
     * @param milliseconds 毫秒
     */
    public static void sleep(double milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep((long) (milliseconds * 1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
