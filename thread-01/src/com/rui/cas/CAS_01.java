package com.rui.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS Compare And Swap
 * 无锁优化 自旋 AtomicXXX 通过CAS保证线程安全
 * ABA 问题 A->B->A
 *  加 version
 *  cas(version)
 * @author rui
 * @since 2022/5/17 14:29
 */
public class CAS_01 {
    AtomicInteger count = new AtomicInteger(0);

    void m() {
        for (int i = 0; i < 10000; i++) {
            count.incrementAndGet(); // count++
        }
    }

    public static void main(String[] args) {
        CAS_01 cas_01 = new CAS_01();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(cas_01::m, "thread-" + i));
        }
        threads.forEach(Thread::start);
        threads.forEach(o -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("cas_01.count = " + cas_01.count);
    }
}
