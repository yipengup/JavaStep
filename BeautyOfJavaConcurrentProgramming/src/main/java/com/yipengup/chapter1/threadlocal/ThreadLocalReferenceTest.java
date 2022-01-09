package com.yipengup.chapter1.threadlocal;

/**
 * 在其他线程里面修改threadLocal对象
 * <p>
 * 会导致原线程中保存的数据获取不到
 *
 * @author yipengup
 * @date 2021/12/18
 */
public class ThreadLocalReferenceTest {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {

        Thread threadA_change = new Thread(() -> {
            System.out.println("set pre...");
            threadLocal.set("threadA change");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("get pre...");
            System.out.println(threadLocal.get());
        });
        threadA_change.setName("threadA_change");
        threadA_change.start();

        Thread.sleep(1000);

        System.out.println("main change...");
        threadLocal = new ThreadLocal<>();

        System.out.println("main end");

    }


}
