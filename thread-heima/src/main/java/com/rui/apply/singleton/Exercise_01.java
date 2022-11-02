package com.rui.apply.singleton;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * description: 单例模式实现 1
 * <hr/>
 * date: 2022/11/2
 *
 * @author rui
 */
@Slf4j(topic = "rui.Exercise_01")
public class Exercise_01 {
    public static void main(String[] args) {
        System.out.println(Singleton.getInstance());
    }
}

/**
 * 1. 为什么加 final
 *  防止类被继承破坏单例
 * 2. 如果实现了序列化接口，如何防止反序列化破坏单例
 */
final class Singleton implements Serializable {
    /*
        3. 为什么设置私有构造
            防止 new
            不能防止反射
     */
    private Singleton() {}

    /*
        4. 这样设置能否保证单例对象创建时的线程安全
            可以保证线程安全
            静态成员变量在类创建时已完成初始化
     */
    private static final Singleton INSTANCE = new Singleton();

    /*
        5. 为什么不直接将 INSTANCE 设置为 public
            1. 提供更好的封装性
            2. 有更多的控制
            3. 可增加泛型的支持
     */
    public static Singleton getInstance() {
        return INSTANCE;
    }

    // 防止反序列化破坏单例
    public Object readResolve() {
        return INSTANCE;
    }


}
