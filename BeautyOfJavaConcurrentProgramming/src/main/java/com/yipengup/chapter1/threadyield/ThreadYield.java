package com.yipengup.chapter1.threadyield;

/**
 * @author yipengup
 * @date 2021/12/17
 */
public class ThreadYield {

    public static void main(String[] args) {
        new YieldRunnable();
        new YieldRunnable();
        new YieldRunnable();
    }

    public static class YieldRunnable implements Runnable {

        public YieldRunnable() {
            new Thread(this).start();
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 5; i++) {
                if (i == 0) {
                    System.out.println(name + "yield ... ");
                    // 当前线程让出自己获取到的CPU执行时间片，等待下一次调度（可能下一次调度的还是它自己）
                    Thread.yield();
                }
            }
            System.out.println(name + "exec end ... ");
        }
    }

}
