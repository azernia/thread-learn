package com.rui.singleton;

/**
 * 懒汉式
 * 可按需实例化，线程不安全
 * 解决方案
 *  synchronize
 *  双重检查锁
 * 指令重排序(超高并发时可能会出现) volatile 保证按照正常顺序执行
 *  new instance()
 *  1. 分配内存
 *  2. 成员变量赋值
 *  3. 赋值
 * @author rui
 * @since 2022/5/17 13:36
 */
public class Singleton03 {
    private volatile static Singleton03 INSTANCE;

    private Singleton03() {}

    public static Singleton03 getInstance() {
        // 可能有业务逻辑
        // 进行两次校验
        if (INSTANCE == null) {
            synchronized (Singleton03.class) {
                if (INSTANCE == null) {
                    // 设置干扰
                    // try {
                    //     Thread.sleep(1);
                    // } catch (InterruptedException e) {
                    //     e.printStackTrace();
                    // }
                    INSTANCE = new Singleton03();
                }
            }
        }
        return INSTANCE;
    }

    public void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> System.out.println(Singleton03.getInstance().hashCode())).start();
        }
    }
}
