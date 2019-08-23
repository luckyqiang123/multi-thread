package main.concurrency.c_012;

import java.util.concurrent.TimeUnit;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/08/14 21:09
 * @Description:volatile 关键字，使一个变量在多个线程间可见
 * A,B线程都用到一个变量，java默认是A线程保留一份copy，这样如果B线程修改了该变量，则A线程未必指导
 * 使用volatile关键字，会让所有的线程都会读到变量的修改值,加了这个关键字是指，
 * 当变量发生更改后，发出缓存过期通知，使缓冲区重新去读变量
 */
public class T {
    /*volatile*/ boolean running = true;
    void m() {
        System.out.println("m start");
        while (running) {
            //cpu有空闲时，有可能会去主内存读变量
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        T t = new T();

        new Thread(t::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.running = false;
    }
}
