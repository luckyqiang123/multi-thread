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
 *
 * 注意，应用这种方法必须保证t2先执行，也就是首先让t2监听才可以
 * todo 可以读到输出结果并不是size=5时t2退出，而是t1结束时t2才接受到通知而退出
 * @Author: zhangzhiqiang
 * @Date: 2019-09-15 18:10
 * @Company: www.luckyqiang.cn
 */
public class MyContainer3 {

    //添加volatile，使得t2能够得到通知
    volatile List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer3 myContainer3 = new MyContainer3();

        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 start");
                if (myContainer3.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 end");
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
                    myContainer3.add(new Object());
                    System.out.println("add " + i);
                    if(myContainer3.size() == 5) {
                        lock.notify();
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
