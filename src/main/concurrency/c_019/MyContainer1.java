package main.concurrency.c_019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: MyContainer1
 * @Description: TODO 实现一个容器 提供add和size方法
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素个数，当个数到5个时，线程2给出提示并结束
 * 分析下面程序是否能完成这个功能
 * 这个不能完成功能，list两个线程间不可见
 * @Author: zhangzhiqiang
 * @Date: 2019-09-15 17:31
 * @Company: www.luckyqiang.cn
 */
public class MyContainer1 {

    List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer1 myContainer1 = new MyContainer1();

        new Thread(() -> {
            for (int i=0; i<10; i++ ) {
                myContainer1.add(new Object());
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
                if(myContainer1.size() == 5) {
                    break;
                }
            }
            System.out.println("t2 end");
        }, "t2").start();

    }
}
