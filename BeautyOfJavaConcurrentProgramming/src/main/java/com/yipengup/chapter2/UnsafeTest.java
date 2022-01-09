package com.yipengup.chapter2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author yipengup
 * @date 2021/12/22
 */
public class UnsafeTest {

    private static final Unsafe UNSAFE;
    private static final long STATE_OFFSET;

    public static final String STATE_FIELD_NAME = "state";

    static {
        try {
            // 利用反射获取到 unsafe 对象
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            // 静态变量传递null就可以获取到值
            UNSAFE = (Unsafe) theUnsafeField.get(null);
            // objectFieldOffset 获取某个属性所在对象的内存的偏移量
            STATE_OFFSET = UNSAFE.objectFieldOffset(UnsafeTest.class.getDeclaredField(STATE_FIELD_NAME));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new Error(e);
        }

    }

    private volatile long state = 1;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("UnsafeTest.main" + UNSAFE);
        UnsafeTest unsafeTest = new UnsafeTest();
        // 获取对象指定偏移量的值(触发volatile语义)
        System.out.println(UNSAFE.getLongVolatile(unsafeTest, STATE_OFFSET));
        // 利用CAS操作替换指定的属性的值
        System.out.println(UNSAFE.compareAndSwapLong(unsafeTest, STATE_OFFSET, 1, 2));
        System.out.println(UNSAFE.getLongVolatile(unsafeTest, STATE_OFFSET));
        // 设置某个值（触发volatile语义）
        UNSAFE.putLongVolatile(unsafeTest, STATE_OFFSET, 3);
        System.out.println(unsafeTest.state);

        // 测试unsafe#park 是否会释放对象锁
        Object resource = new Object();
        Thread threadA = new Thread(() -> {
            synchronized (resource) {
                System.out.println("threadA get resource");
                // 一直阻塞当前线程（不会释放获取到的对象锁）
                UNSAFE.park(false, 0L);
                System.out.println("threadA end park");
            }
        });
        threadA.start();

        Thread.sleep(1000);

        new Thread(() -> {
            synchronized (resource) {
                System.out.println("ThreadB get resource");
            }
        }).start();

        Thread.sleep(5000);

        System.out.println("main end");
        UNSAFE.unpark(threadA);
    }

}
