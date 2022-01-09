package com.yipeng.understandingjvm.chapter3.objectdead;


/**
 * 此代码演示两点：
 * 1，对象可以在被GC时自我针拯救。
 * 2，自救的机会只会有一次，因为一个对象的finalize方法最多只会被系统自动调用一次
 *
 * @author yipengup
 * @date 2022/1/6
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC saveHook = null;

    public void isAlive() {
        System.out.println("yes, i am still alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed");
        // 类进行变量处在方法区，方法区的类静态变量是GCRoot的一种
        // 通过将当前对象的引用绑定给类静态变量， 可防止对象被GC一次。
        FinalizeEscapeGC.saveHook = this;
    }

    public static void main(String[] args) throws InterruptedException {
        saveHook = new FinalizeEscapeGC();
        saveHook = null;
        // 对象第一次拯救自己
        System.gc();
        // 因为 Finalizer方法优先级很低，暂停0.5秒已等待它。
        Thread.sleep(500);
        if (saveHook != null) {
            saveHook.isAlive();
        } else {
            System.out.println("no, i am dead : (");
        }

        // 再次进行gc
        saveHook = null;
        System.gc();
        Thread.sleep(500);
        if (saveHook != null) {
            saveHook.isAlive();
        } else {
            System.out.println("no, i am dead : (");
        }
    }
}
