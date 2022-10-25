package com.rui.apply;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * description: 模拟买票
 * <hr/>
 * date: 2022/10/25 10:54
 * 找到临界区并加锁
 *
 * @author rui
 */
@Slf4j(topic = "rui.Apply_02_ExerciseSell")
public class Apply_02_ExerciseSell {

    private static final Random RANDOM = new Random();

    public static int randomAmount(int amount) {
        return RANDOM.nextInt(amount) + 1;
    }

    public static void main(String[] args) throws InterruptedException {
        // 模拟多人买票
        TicketWindow ticketWindow = new TicketWindow(2000000);

        // 所有线程的集合
        List<Thread> threadList = new ArrayList<>();
        // 卖出的票数统计
        List<Integer> sellAmountList = new Vector<>();  // Vector 线程安全
        for (int i = 0; i < 2000; i++) {
            Thread thread = new Thread(() -> {
                // 买票
                int amount = ticketWindow.sell(randomAmount(5));
                // 统计买票数
                sellAmountList.add(amount);
            });
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        // IntSummaryStatistics collect = sellAmountList.stream().collect(Collectors.summarizingInt(value -> value));
        int sum = sellAmountList.stream().mapToInt(value -> value).sum();
        // 统计卖出的票数和剩余的票数
        log.debug("余票数：{}", ticketWindow.getCount());
        log.debug("售出票数：{}", sum);
        log.debug("两者之和：{}", ticketWindow.getCount() + sum);
    }

}

/**
 * 票窗口
 *
 * @author Rui
 * date 2022/10/25
 */
class TicketWindow {
    private int count;

    public TicketWindow() {
    }

    public TicketWindow(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    /**
     * 卖票
     * @param amount 卖票数量
     * @return 卖票数量
     */
    public synchronized int sell(int amount) {
        if (this.count >= amount) {
            this.count -= amount;
            return amount;
        } else {
            return 0;
        }
    }

}
