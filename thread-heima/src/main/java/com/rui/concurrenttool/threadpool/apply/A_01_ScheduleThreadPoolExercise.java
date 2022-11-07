package com.rui.concurrenttool.threadpool.apply;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * description: 定时线程应用
 * <hr/>
 * date: 2022/11/7
 * <hr/>
 * 让每周四 18:00:00 定时执行任务
 *
 * @author rui
 */
@Slf4j(topic = "rui.A_01_ScheduleThreadPoolExercise")
public class A_01_ScheduleThreadPoolExercise {
    public static void main(String[] args) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 获取周四的时间
        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);
        // 当前时间大于本周周四，找到下周周四
        if (now.compareTo(time) > 0) {
            time = time.plusWeeks(1L);
        }
        long initialDelay = Duration.between(now, time).toMillis();
        // initialDelay 当前时间与周四的时间差 period 一周的时间间隔
        long period = 1000 * 60 * 60 * 24 * 7;
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(() -> {
            log.debug("running.....");
        }, initialDelay, period, TimeUnit.MILLISECONDS);
    }
}
