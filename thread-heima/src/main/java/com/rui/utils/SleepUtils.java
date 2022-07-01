package com.rui.utils;

import java.util.concurrent.TimeUnit;

/**
 * description: 睡眠工具类
 * <hr/>
 * date: 2022/7/1 10:31
 *
 * @author rui
 */
public class SleepUtils {
    private SleepUtils() {}

    public static void sleep(int i) {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(double i) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (i * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
