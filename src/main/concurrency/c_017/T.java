package main.concurrency.c_017;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: T
 * @Description: TODO 锁定某对象o，如果o的属性发生改变，不影响锁的使用
 * 但是如果o变成另外一个对象，则锁定的对象发生改变，应尽量避免将锁定对象的引用变成另外的对象
 * 锁是锁在堆内存里new出来的对象，不是锁在栈内存中对o的引用(指向堆内存的)
 * @Author: zhangzhiqiang
 * @Date: 2019-08-25 16:57
 * @Company: www.luckyqiang.cn
 */
public class T {
    Object o = new Object();

    void m(){
        synchronized (o) {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();
        new Thread(t::m, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(t::m, "t2");
        //锁对象发生改变，所以t2线程得以执行，如果没有new一个新对象，线程2将永远不会执行
        t.o = new Object();
        t2.start();
    }
}
