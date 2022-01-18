package com.yipengup.chapter7.delayqueue;

import lombok.Data;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列测试
 *
 * @author yipengup
 * @date 2022/1/18
 */
public class DelayQueueTest {

    public static void main(String[] args) throws InterruptedException {
        poll();
    }

    public static void poll() throws InterruptedException {
        DelayQueue<Task> tasks = new DelayQueue<>();
        Task task = new Task();
        task.setCreateDate(new Date());
        task.setDelaySeconds(5L);
        task.setName("taskName");
        tasks.add(task);

        // 打印当前时间
        long start = System.currentTimeMillis();
        // 休眠两秒
        Thread.sleep(2000);
        // 此时还没有到延迟时间，阻塞一定的时间
        Task taskTask = tasks.take();
        // 最后计算时间应该还是5秒左右，延迟时间是实时计算的，动态变化。
        System.out.println(System.currentTimeMillis() - start);
        System.out.println("taskTask = " + taskTask);
    }

    @Data
    private static class Task implements Delayed {

        /**
         * 延迟时间
         */
        private Long delaySeconds;
        /**
         * 创建时间
         */
        private Date createDate;
        private String name;

        /**
         * 用于计算元素的过期时间（一般都会与当前时间进行绑定）
         *
         * @param unit TimeUnit
         * @return 返回当前元素的延迟时间
         */
        @Override
        public long getDelay(TimeUnit unit) {
            long nowSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            System.out.println("nowSecond = " + nowSecond);

            long delayTimeSecond = TimeUnit.MILLISECONDS.toSeconds(createDate.getTime()) + delaySeconds;
            System.out.println("delayTimeSecond = " + delayTimeSecond);

            // 最后的延迟时间
            long convert = unit.convert(delayTimeSecond - nowSecond, TimeUnit.SECONDS);
            System.out.println("unit = " + unit);
            System.out.println("convert = " + convert);
            return convert;
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(getDelay(TimeUnit.SECONDS), o.getDelay(TimeUnit.SECONDS));
        }
    }


}
