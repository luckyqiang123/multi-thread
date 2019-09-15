package main.concurrency.c_019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: MyContainer2
 * @Description: TODO 实现一个容器 提供add和size方法
 * * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素个数，当个数到5个时，线程2给出提示并结束
 * * 分析下面程序是否能完成这个功能
 * 给list添加volatile之后，t2能够接到通知，但是t2线程的死循环很浪费cpu，不用死循环该怎么做
 * 使用wait和notify，wait会释放锁，而notify不会释放锁
 * 注意，应用这种方法必须保证t2先执行，也就是首先让t2监听才可以
 * todo 可以读到输出结果并不是size=5时t2退出，而是t1结束时t2才接受到通知而退出
 * 这里需要在notify之后，t1必须释放锁，t2退出后，也必须notify，通知t1继续继续
 * @Author: zhangzhiqiang
 * @Date: 2019-09-15 18:10
 * @Company: www.luckyqiang.cn
 */
public class MyContainer4 {

    //添加volatile，使得t2能够得到通知
    volatile List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer4 myContainer4 = new MyContainer4();

        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 start");
                if (myContainer4.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 end");
                //通知t1继续执行，线程结束，自动释放锁
                lock.notify();
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 start");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    myContainer4.add(new Object());
                    System.out.println("add " + i);
                    if(myContainer4.size() == 5) {
                        lock.notify();
                        //释放锁，让t2得以执行
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();
    }
}
