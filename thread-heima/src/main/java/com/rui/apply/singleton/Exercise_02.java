package com.rui.apply.singleton;

import lombok.extern.slf4j.Slf4j;

/**
 * description: 单例模式实现 2
 * <hr/>
 * date: 2022/11/2
 *
 * @author rui
 */
@Slf4j(topic = "rui.Exercise_02")
public class Exercise_02 {
    public static void main(String[] args) {
        System.out.println(SingletonByEnum.INSTANCE);
    }

}

/**
 * 1. 枚举如何限制实例个数
 *  为枚举类中的静态成员变量
 * 2. 是否有并发问题
 *  没有
 * 3. 能否被反射破坏
 *  不能
 * 4. 能否以被反序列化破坏
 *  不能
 * 5. 属于饿汉式还是懒汉式
 *  饿汉式
 * 6. 希望加入创建的逻辑如何处理
 *  通过构造方法
 */
enum SingletonByEnum {
    INSTANCE
}
