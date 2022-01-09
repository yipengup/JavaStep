package com.yipengup.chapter1.threadinterrupt;

/**
 * @author yipengup
 * @date 2021/12/17
 */
public class ThreadInterrupt {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (; ; ) {

            }
        });

        thread.setName("threadA");
        thread.start();

        Thread mainThread = Thread.currentThread();
        mainThread.setName("main");

        System.out.println("interrupt: （threadA）" + thread.isInterrupted());
        thread.interrupt();
        System.out.println("interrupt: （threadA）" + thread.isInterrupted());

        System.out.println("interrupt: （main）" + mainThread.isInterrupted());
        mainThread.interrupt();
        System.out.println("interrupt: （main）" + mainThread.isInterrupted());
        // 将当前线程的中断状态进行重置（返回的是重置前的中断状态）
        System.out.println("interrupt: （main）" + Thread.interrupted());
        System.out.println("interrupt: （main）" + mainThread.isInterrupted());

    }
}
