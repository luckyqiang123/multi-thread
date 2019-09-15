package main.concurrency.c_019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
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
 * <p>
 * 简单方式：使用Latch(门闩)替代wait notify来进行通知，通信方式简单，也可以指定等待时间
 * 使用await和countdown方法替代wait和notify
 * CountDownLatch不涉及锁定，当count的值为0时，当前线程继续运行
 * 当不涉及同步，只是涉及线程通信的时候，用synchronize + wait/notify 就显得太重了
 * 这时应该考虑countdownlatch/cyclicbarrier/semaphore
 * @Author: zhangzhiqiang
 * @Date: 2019-09-15 18:10
 * @Company: www.luckyqiang.cn
 */
public class MyContainer5 {

    //添加volatile，使得t2能够得到通知
    volatile List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer5 myContainer5 = new MyContainer5();

        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            synchronized (latch) {
                System.out.println("t2 start");
                if (myContainer5.size() != 5) {
                    try {
                        //也可以指定等待时间，
                        //latch.await(5000, TimeUnit.MILLISECONDS);
                        latch.await();
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

            for (int i = 0; i < 10; i++) {
                myContainer5.add(new Object());
                System.out.println("add " + i);
                if (myContainer5.size() == 5) {
                    //打开门闩，让t2得以执行，调用一次，CountDownLatch(1) -1，CountDownLatch(n) n=0时放开
                    latch.countDown();

                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "t1").start();
    }
}
