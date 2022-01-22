package com.yipengup.chapter8;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可命名的线程创建工厂
 *
 * @author yipengup
 * @date 2022/1/20
 */
public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    /**
     * 与业务量有关的名称
     */
    private final String namePrefix;

    private NamedThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, namePrefix + "-thread-" + threadNumber.getAndIncrement());
    }

    public static ThreadFactory create(String namePrefix) {
        return new NamedThreadFactory(namePrefix);
    }


}
