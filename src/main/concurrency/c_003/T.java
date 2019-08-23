package main.concurrency.c_003;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/7/28 16:48
 * @Description:synchronized给某个对象加锁
 */
public class T {
    private int count = 10;

    //等同于在方法的代码执行时要synchronized(this)
    public synchronized void m() {

        count--;
        System.out.println(Thread.currentThread().getName() + "count = " + count);

    }
}
