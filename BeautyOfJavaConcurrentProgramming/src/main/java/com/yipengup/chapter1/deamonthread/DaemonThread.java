package com.yipengup.chapter1.deamonthread;

/**
 * @author yipengup
 * @date 2021/12/17
 */
public class DaemonThread {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (; ; ) {

            }
        });

        thread.setName("daemonThread");
        thread.setDaemon(true);
        thread.start();
        System.out.println("main end");
    }
}
