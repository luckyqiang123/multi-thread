package main.concurrency.c_006;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/7/28 16:48
 * @Description:synchronized给某个对象加锁
 */
public class T implements Runnable {
    private static int count = 10;

    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + "count = " + count);
    }

    public static void main(String[] args) {
        T t = new T();
        for(int i=0; i<5; i++) {
            new Thread(t, "THREAD" + i).start();
        }
    }
}
