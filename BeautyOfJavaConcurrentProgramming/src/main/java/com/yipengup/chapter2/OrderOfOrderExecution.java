package com.yipengup.chapter2;

/**
 * 乱序执行，指令重排序
 *
 * @author yipengup
 * @date 2021/12/22
 */
public class OrderOfOrderExecution {

    // 这里不能用volatile关键字修饰，因为volatile关键字本身就有防止指令重排序的语义
    private static int num = 0;
    private static boolean ready = false;

    public static class ReadThread extends Thread {

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                if (ready) {
                    System.out.println("num * num = " + (num * num));
                }
            }
        }
    }

    public static class WriteThread extends Thread {

        @Override
        public void run() {
            // 这里先修改num的值，然后在修改ready的值
            // 指令没有从重排序的话，read线程一定是输出4， 但是指令重排序之后read线程可能输出0，此时num = 2还没有执行
            num = 2;
            ready = true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadThread readThread = new ReadThread();
        readThread.start();

        WriteThread writeThread = new WriteThread();
        writeThread.start();

        Thread.sleep(1);

        // 最后中断读线程
        readThread.interrupt();
    }


}
