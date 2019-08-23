package main.concurrency.c_002;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/7/28 16:48
 * @Description:synchronized给某个对象加锁
 */
public class T {
    private int count = 10;


    public void m() {
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }
}
