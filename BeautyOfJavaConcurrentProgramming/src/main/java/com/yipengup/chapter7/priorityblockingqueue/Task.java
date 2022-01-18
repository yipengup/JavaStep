package com.yipengup.chapter7.priorityblockingqueue;

import lombok.Data;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 根据任务的优先级排序
 *
 * @author yipengup
 * @date 2022/1/18
 */
@Data
public class Task implements Comparable<Task> {

    private Integer priority;
    private String name;

    @Override
    public int compareTo(Task o) {
        return Integer.compare(priority, o.priority);
    }

    public static void main(String[] args) {
        PriorityBlockingQueue<Task> tasks = new PriorityBlockingQueue<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            task.setPriority(random.nextInt(10));
            task.setName("taskName" + i);
            tasks.offer(task);
        }

        while (!tasks.isEmpty()) {
            Task task = tasks.poll();
            if (Objects.nonNull(task)) {
                System.out.println("task = " + task);
            }
        }
    }

}
