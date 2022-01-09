package com.yipengup.chapter1.threadjoin;

/**
 * 线程调用Join之后， 就会将当前线程挂起等待子线程执行完毕后继续执行
 * <p>
 * 1、（有点类似于当前线程会wait挂起， 然后子线程执行完毕后通知主线程结束等待挂起恢复执行）
 * 2、也可以理解为在主线程中有一个while循环一直检测子线程是否执行完毕
 *
 * @author yipengup
 * @date 2021/12/17
 */
public class ThreadJoin {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName() + " exec end!!!");
        };

        Thread thread = new Thread(runnable);
        thread.setName("子线程");
        thread.start();

        System.out.println("子线程开始执行");

        try {
            // thread.join();
            join(thread);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("mainThread exec end");
    }

    private static void join(Thread thread) throws InterruptedException {

        // 主线程一直阻塞带子线程执行完毕
        while (thread.isAlive()) {
            if (Thread.interrupted()) {
                // 当前线程如果发生终端就会结束阻塞
                System.out.println("主线程发生中断");
                throw new InterruptedException();
            }
        }

        System.out.println("子线程执行完毕");
    }

}
