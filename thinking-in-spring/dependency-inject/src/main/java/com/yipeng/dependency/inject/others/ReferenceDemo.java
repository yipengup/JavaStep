package com.yipeng.dependency.inject.others;

import com.yipeng.common.domain.User;
import org.springframework.util.ReflectionUtils;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * {@link java.lang.ref.Reference} 抽象类示例
 * {@link Reference} 抽象类对应 Java 中的引用类型，该类与垃圾回收机制息息相关。
 * <p>
 * 常见的引用关系有四种：
 * 1、强引用
 * 2、软引用 {@link SoftReference} 在内存不足时回收只被软引用指向的对象
 * 3、弱引用 {@link WeakReference} 触发垃圾回收时就会回收只被弱引用指向的对象
 * 4、虚引用 {@link PhantomReference} 对生存时间无影响，虚引用并不会决定指向的对象的生命周期，虚引用的作用在于跟踪垃圾回收。
 * 在 GC 的过程中，如果发现有虚引用，GC 则会将 Reference对象 放到 ReferenceQueue 中，由程序员自己处理。
 * 当调用 {@link ReferenceQueue#poll()} 方法时，将引用从队列中移除后，Reference 对象就会变成 Inactive 状态，意味着被指向的对象可以被回收了（适用于JDK9及以上版本）
 * 在Java 8以及之前的版本中，在虚引用回收后，虚引用指向的对象才会回收。
 * <p>
 * 软引用、弱引用指向的对象，因为垃圾回收时会被回收掉，所以它们时常被用来作缓存。
 * <p>
 * 与引用相关的类有一个 {@link ReferenceQueue} 用于管理 Reference 的队列。
 * {@link ReferenceQueue} 主要就是存储封装的待回收的 Reference 对象。
 *
 * @author yipeng
 */
public class ReferenceDemo {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        // testReference();
        // testSoftReference();
        // testPostGCSoftReference();
        // testWeakReference();
        testPhantomReference();
    }

    public static void testReference() {
        User user = User.createUser();
        // userCopy 保存堆中 User 对象的引用地址信息
        User userCopy = user;
        user = null;
        System.out.println("userCopy = " + userCopy);
    }

    public static void testSoftReference() {
        // 此时堆空间的 User 对象只有一个软引用指向它
        SoftReference<User> userSoftReference = new SoftReference<>(User.createUser());
        // userSoftReference.get() 用于获取到引用类中指向的对象
        System.out.println("pre System.gc() userSoftReference.get() = " + userSoftReference.get());
        // 此时触发垃圾收集，因为此时堆空间比较充足，所以 User 对象不会被回收（有一个软引用指向它）
        System.gc();
        System.out.println("post System.gc() userSoftReference.get() = " + userSoftReference.get());
    }

    /**
     * 模拟内存空间不足时 软引用指向的对象是否会被回收
     * 设置相关 JVM 参数，此时
     * -Xms20M
     * -Xmx20M
     * -Xmn10M 指定新生代大小，否则新生代占堆空间的 1/3
     * -XX:+UseSerialGC
     * -XX:+PrintGCDetails
     */
    public static void testPostGCSoftReference() {
        // 创建一个数组对象，并有一个软引用指向它
        SoftReference<byte[]> softReference = new SoftReference<>(new byte[_1MB * 6]);
        System.out.println("softReference.get() = " + Objects.isNull(softReference.get()));
        // 此时超出堆空间触发GC，看是否软引用指向的对象是否被回收
        byte[] allocation2 = new byte[_1MB * 6];
        byte[] allocation3 = new byte[_1MB * 6];
        System.out.println("softReference.get() = " + Objects.isNull(softReference.get()));
    }

    public static void testWeakReference() {
        WeakReference<User> userWeakReference = new WeakReference<>(User.createUser());
        System.out.println(Objects.nonNull(userWeakReference.get()));
        // 触发gc
        System.gc();
        System.out.println(Objects.nonNull(userWeakReference.get()));
    }

    public static void testPhantomReference() {
        // PhantomReference 虚引用必须指定 ReferenceQueue
        ReferenceQueue<User> userReferenceQueue = new ReferenceQueue<>();
        PhantomReference<User> userPhantomReference = new PhantomReference<>(User.createUser(), userReferenceQueue);
        // PhantomReference#get() 方法始终返回 null
        System.out.println(Objects.nonNull(userPhantomReference.get()));
        // 获取到引用队列的长度
        System.out.println("pre gc queue size = " + getReferenceQueueSize(userReferenceQueue));
        System.gc();
        // gc 之后会将虚引用的对象添加到 ReferenceQueue 中
        System.out.println("post gc queue size = " + getReferenceQueueSize(userReferenceQueue));
        Reference<? extends User> userReference = userReferenceQueue.poll();
        if (Objects.nonNull(userReference)) {
            // 因为 userReference 是 PhantomReference 类型，所以不能直接调用 get() 方法
            indicateReference(userReference);
        }
        System.out.println("post poll queue size = " + getReferenceQueueSize(userReferenceQueue));
    }

    private static void indicateReference(Reference<? extends User> userReference) {
        Field referentField = ReflectionUtils.findField(Reference.class, "referent");
        Objects.requireNonNull(referentField);
        ReflectionUtils.makeAccessible(referentField);
        System.out.println(Objects.nonNull(ReflectionUtils.getField(referentField, userReference)));
    }

    private static Long getReferenceQueueSize(ReferenceQueue<User> userReferenceQueue) {
        Field queueLengthField = ReflectionUtils.findField(ReferenceQueue.class, "queueLength", long.class);
        Objects.requireNonNull(queueLengthField);
        ReflectionUtils.makeAccessible(queueLengthField);
        return (Long) ReflectionUtils.getField(queueLengthField, userReferenceQueue);
    }

}
