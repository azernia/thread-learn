package com.rui.sharingmodel.immutable.problem;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * description:
 * <hr/>
 * date: 2022/11/3
 *
 * @author rui
 */
@Slf4j(topic = "rui.Test1")
public class Test1 {
    public static void main(String[] args) {
        DateTimeFormatter stf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // 不可变类
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                TemporalAccessor parse = stf.parse("1951-04-21");
                log.debug("{}", parse);
            }).start();
        }
    }

    private static void test() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // 可变类，类中的变量在并发中可以改变
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (sdf) {
                    try {
                        log.debug("{}", sdf.parse("1951-04-21"));
                    } catch (Exception e) {
                        log.error("{}", e);
                    }
                }
            }).start();
        }
    }
}
