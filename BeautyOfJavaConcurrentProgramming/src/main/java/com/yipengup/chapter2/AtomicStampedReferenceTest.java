package com.yipengup.chapter2;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 模拟CAS ABA问题
 *
 * @author yipengup
 * @date 2021/12/22
 */
public class AtomicStampedReferenceTest {


    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        System.out.println("o = " + o);
        AtomicStampedReference<Object> atomicStampedReference = new AtomicStampedReference<>(o, 1);

        new Thread(() -> {
            // 子线程直接改变邮戳模拟ABA问题
            atomicStampedReference.set(o, 2);
        }).start();

        Thread.sleep(1000);
        Object reference = atomicStampedReference.getReference();
        System.out.println("reference = " + reference);
        System.out.println(o == reference);

        System.out.println(atomicStampedReference.compareAndSet(o, new Object(), 1, 3));

    }

}
