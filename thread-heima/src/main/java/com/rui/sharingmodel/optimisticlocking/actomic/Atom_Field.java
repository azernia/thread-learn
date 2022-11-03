package com.rui.sharingmodel.optimisticlocking.actomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * description: 字段更新器
 * <hr/>
 * date: 2022/11/3
 * AtomicReferenceFieldUpdater
 * AtomicIntegerFieldUpdater
 * AtomicLongFieldUpdater
 *
 *
 * @author rui
 */
@Slf4j(topic = "rui.Atom_Field")
public class Atom_Field {

    public static void main(String[] args) {
        Student stu = new Student();

        AtomicReferenceFieldUpdater<Student, String> updater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        System.out.println(updater.compareAndSet(stu, null, "张三"));
        System.out.println(stu);
    }

}

class Student {
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
