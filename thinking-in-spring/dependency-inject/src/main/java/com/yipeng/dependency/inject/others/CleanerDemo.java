package com.yipeng.dependency.inject.others;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sun.misc.Cleaner;

/**
 * {@link sun.misc.Cleaner} 示例
 * <p>
 * {@link sun.misc.Cleaner} 继承于 {@link java.lang.ref.PhantomReference} 是对 {@link Object#finalize()} 的一种优化手段。
 * {@link Object#finalize()} 、{@link sun.misc.Cleaner} 常被用于资源的清理工作。
 * 但是缺点在于清理的不及时，具有不可预见性，只有在系统进行垃圾回收的时候才会触发，这样导致清理的资源累计降低系统的性能。
 * 先通常处理资源回收的方式是 资源即用即关，资源实现 {@link AutoCloseable} 接口，借助 try-with-resource 语法糖实现垃圾的回收，
 * 而将 {@link Cleaner} 作为 safety net 安全网，使其作为清理资源的最后一道屏障
 *
 * @author yipeng
 */
public class CleanerDemo {

    public static void main(String[] args) {
        // testAutoClean();
        testManualClean();
    }

    public static void testAutoClean() {
        // 自动会清理 room 资源
        try (Room room = new Room(new Garbage(7))) {
            System.out.println("clean room start");
        }
    }

    public static void testManualClean() {
        // 手动清理 room 资源
        Room room = new Room(new Garbage(7));
        System.out.println("clean room start");
        // 此时有且仅有一个虚引用指向堆内存中的 Room 对象
        room = null;
        System.gc();
    }


    /**
     * Room 作为一个资源实现 {@link AutoCloseable} 可以使用 try-with-resource 的方式关闭资源
     */
    static class Room implements AutoCloseable {

        private final Cleaner cleaner;

        /**
         * 记录房间内的垃圾
         */
        private final Garbage garbage;

        public Room(Garbage garbage) {
            this.garbage = garbage;
            // Cleaner 会绑定一个回调函数用于清理资源
            this.cleaner = Cleaner.create(this, new State(this.garbage));
        }

        /**
         * 回调函数里面不能持有外部房间的引用，否则就是强引用会影响垃圾回收，所以只能以静态内部类的方式去实现清理垃圾的逻辑
         */
        private static class State implements Runnable {

            private final Garbage garbage;

            public State(Garbage garbage) {
                this.garbage = garbage;
            }

            @Override
            public void run() {
                System.out.println("cleaning room");
                // 设置房间的垃圾为0
                if (garbage.getNumber() != 0) {
                    garbage.setNumber(0);
                }
                System.out.println("clean room end");
            }
        }

        @Override
        public void close() {
            // 关闭的时候主动触发清理资源
            cleaner.clean();
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Garbage {
        private int number;
    }


}
