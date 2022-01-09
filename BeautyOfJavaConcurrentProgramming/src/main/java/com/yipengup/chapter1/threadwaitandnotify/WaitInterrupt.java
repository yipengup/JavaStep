package com.yipengup.chapter1.threadwaitandnotify;

/**
 * 线程被中断后就结束等待，获取到对象锁之后继续执行同步代码块
 *
 * @author yipengup
 * @date 2021/12/16
 */
public class WaitInterrupt {
    private static final Object RESOURCE = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            synchronized (RESOURCE) {
                System.out.println("threadA获取到resource资源");
                try {
                    RESOURCE.wait();
                } catch (InterruptedException e) {
                    System.out.println("线程强行中断，结束无限期等待状态");
                } finally {
                    System.out.println("finally的逻辑");
                }
                System.out.println("终端后的逻辑");
            }
        });

        thread.start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (RESOURCE) {
                System.out.println("threadB获取到resource资源");
                while (true) {
                    // 不释放锁， 查看线程A被中断后是否需要获取到resource资源
                }
            }
        }).start();

        // 主线程休眠执行子进程
        Thread.sleep(1000);
        System.out.println("main 终端子线程");
        thread.interrupt();

        System.out.println("main end");
    }


}
