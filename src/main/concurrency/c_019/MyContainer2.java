package main.concurrency.c_019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: MyContainer2
 * @Description: TODO 实现一个容器 提供add和size方法
 *  * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素个数，当个数到5个时，线程2给出提示并结束
 *  * 分析下面程序是否能完成这个功能
 *  给list添加volatile之后，t2能够接到通知，但是t2线程的死循环很浪费cpu，不用死循环该怎么做
 * @Author: zhangzhiqiang
 * @Date: 2019-09-15 18:10
 * @Company: www.luckyqiang.cn
 */
public class MyContainer2 {

    //添加volatile，使得t2能够得到通知
    volatile List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer2 myContainer2 = new MyContainer2();

        new Thread(() -> {
            for (int i=0; i<10; i++ ) {
                myContainer2.add(new Object());
                System.out.println("add " + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                if(myContainer2.size() == 5) {
                    break;
                }
            }
            System.out.println("t2 end");
        }, "t2").start();

    }
}
