package com.rui.sharingmodel.optimisticlocking.actomic;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * description: 处理 ABA 问题
 * <hr/>
 * date: 2022/11/3
 *
 * @author rui
 */
@Slf4j(topic = "rui.Atom_AtomicStampedReference")
public class Atom_AtomicStampedReference {

    private static final AtomicStampedReference<String> REFERENCE = new AtomicStampedReference<>("A", 0);

    public static void main(String[] args) {
        log.debug("main start");
        String prev = REFERENCE.getReference();
        int version = REFERENCE.getStamp();
        log.debug("version is {}", version);
        other(version);
        Sleeper.sleep(1);
        log.debug("change A -> C {}, version is {}", REFERENCE.compareAndSet(prev, "C", version, version + 1), REFERENCE.getStamp());
    }

    private static void other(int version) {
        new Thread(() -> {
            log.debug("change A -> B {}, version is {}", REFERENCE.compareAndSet(REFERENCE.getReference(), "B", version, version + 1), REFERENCE.getStamp());
        }, "t1").start();
        Sleeper.sleep(0.5);
        new Thread(() -> {
            log.debug("change B -> A {}, version is {}", REFERENCE.compareAndSet(REFERENCE.getReference(), "A", version, version + 1), REFERENCE.getStamp());
        }, "t2").start();
    }



}
