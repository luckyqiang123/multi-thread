package main.concurrency.c_004;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/7/28 16:48
 * @Description:synchronized给某个对象加锁
 */
public class T {
    private static int count = 10;

    //等同于synchronized(main.concurrency.c_004.T.class),锁定静态方法，相当于锁定class类对象
    public synchronized static void m() {

        count--;
        System.out.println(Thread.currentThread().getName() + "count = " + count);

    }

    public static  void mm() {
        synchronized (T.class) {
            count--;
        }
    }
}
