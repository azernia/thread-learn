package com.rui.sharingmodel.optimisticlocking.actomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * description: 原子整数
 * <hr/>
 * date: 2022/11/2
 *
 * @author rui
 */
@Slf4j(topic = "rui.Atom_AtomicInteger")
public class Atom_AtomicInteger {

    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(0);
        i.incrementAndGet();
        i.updateAndGet(x -> x * 10);
        System.out.println(i.get());
    }

}
