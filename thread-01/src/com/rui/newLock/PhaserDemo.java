package com.rui.newLock;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 按照不同阶段执行
 * 结婚场景
 * create date 2022/5/18 16:58
 *
 * @author rui
 */
public class PhaserDemo {
    static Random random = new Random();
    static MarriagePhaser phaser = new MarriagePhaser();

    static class Person implements Runnable {
        private final String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrive() {
            milliSleep(1);
            System.out.printf("%s 到达现场\n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            milliSleep(1);
            System.out.printf("%s 吃完\n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void leave() {
            milliSleep(1);
            System.out.printf("%s 离开了\n", name);
            phaser.arriveAndAwaitAdvance();
        }

        private void hug() {
            if (name.equals("bridegroom") || name.equals("bride")) {
                milliSleep(1);
                System.out.printf("%s 洞房\n", name);
                phaser.arriveAndAwaitAdvance();
            } else {
                // 移除非需要线程
                phaser.arriveAndDeregister();
            }
        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }
    }

    /**
     * 婚礼阶段
     */
    static class MarriagePhaser extends Phaser {
        /**
         * 在推进
         * 满足阶段条件时自动调用
         * @param phase             阶段
         * @param registeredParties 注册方 线程数
         * @return boolean true 所有该组线程结束
         */
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("everyone are arrived!" + registeredParties);
                    System.out.println("=========================================");
                    return false;
                case 1:
                    System.out.println("everyone are eaten!" + registeredParties);
                    System.out.println("=========================================");
                    return false;
                case 2:
                    System.out.println("everyone are leaved!" + registeredParties);
                    System.out.println("=========================================");
                    return false;
                case 3:
                    System.out.println("marriage completed!" + registeredParties);
                    System.out.println("=========================================");
                    return true;
                default:
                    return true;
            }
        }
    }

    static void milliSleep(int milli) {
        try {
            TimeUnit.SECONDS.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 注册该阶段有几个人(线程)
        phaser.bulkRegister(7);
        for (int i = 0; i < 5; i++) {
            new Thread(new Person("person" + i)).start();
        }
        new Thread(new Person("bridegroom")).start();
        new Thread(new Person("bride")).start();
    }
}
