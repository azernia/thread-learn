package com.rui.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * description:
 * <hr/>
 * date: 2022/11/3
 *
 * @author rui
 */
public class UnsafeAccessor {
    private static final Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }
}
