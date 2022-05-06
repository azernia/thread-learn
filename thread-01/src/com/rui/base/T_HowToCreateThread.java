package com.rui.base;

public class T_HowToCreateThread {
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("hello my thread");
        }
    }
    static class MyRun implements Runnable {

        @Override
        public void run() {
            System.out.println("hello my run");
        }
    }
    public static void main(String[] args) {
        new MyThread().start();
        new Thread(new MyRun()).start();
        new Thread(() -> {
            System.out.println("hello lambda");
        }).start();
    }
}
