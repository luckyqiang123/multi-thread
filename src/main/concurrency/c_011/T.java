package main.concurrency.c_011;

import java.util.concurrent.TimeUnit;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/7/28 16:48
 * @Description:程序在执行过程中，如果出现异常，默认情况锁会被释放
 * 在并发处理中，要注意可能因为异常发生不一致情况
 * 在第一个线程中抛出异常，其他线程就会进入同步代码区，有可能会访问到异常产生的数据
 */
public class T {

    int count = 0;

    synchronized void m1() {
        System.out.println("m1 start:" + Thread.currentThread().getName());
        while (true) {
            count ++;
            System.out.println(Thread.currentThread().getName() + "count = " +count);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count ==5) {
                int i = 1/0;//抛出异常
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                t.m1();
            }
        };
        new Thread(r, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(r, "t2").start();
    }



}


