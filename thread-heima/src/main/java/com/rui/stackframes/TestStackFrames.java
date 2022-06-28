package com.rui.stackframes;

import org.junit.Test;

/**
 * description: 测试栈帧
 * <hr/>
 * date: 2022/6/28 15:41
 *
 * @author rui
 */
public class TestStackFrames {

    @Test
    public void testStackFrames() {
        Thread thread = new Thread(() -> {
            method1(20);
        });
        thread.setName("t1");
        thread.start();
        method1(10);
    }

    private void method1(int x) {
        int y = x + 1;
        Object obj = method2();
        System.out.println(obj);
    }

    private Object method2() {
        return new Object();
    }

}
