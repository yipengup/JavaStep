package com.yipengup.chapter5;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 查看 CopyOnWriteArrayList 弱一致性问题
 *
 * @author yipengup
 * @date 2021/12/30
 */
public class CopyOnWriteArrayListTest {
    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add(1);
        copyOnWriteArrayList.add(2);
        copyOnWriteArrayList.add(3);
        copyOnWriteArrayList.add(4);
        copyOnWriteArrayList.add(5);

        // 迭代器获取的时COW此时底层数组的快照
        Iterator<Integer> iterator = copyOnWriteArrayList.iterator();

        // 开启一个线程删除，修改部分元素
        Thread thread = new Thread(() -> {
            copyOnWriteArrayList.set(0, -1);
            copyOnWriteArrayList.remove(1);
            copyOnWriteArrayList.remove(2);
        });

        thread.join();

        // 即使子线程改变了原队列元素，但是不会影响已经获取的迭代器的数据
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println("next = " + next);
        }

    }
}
