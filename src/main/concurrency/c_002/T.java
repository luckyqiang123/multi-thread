package main.concurrency.c_002;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/7/28 16:48
 * @Description:synchronized给某个对象加锁
 * TODO 被volatile修饰的变量保证Java内存模型中的可见性和有序性。
 * 可见性：当一个线程修改了一个被volatile修饰的变量的值，新值会立即被刷新到主内存中，其他线程可以立即得知新值。
 * 有序性：禁止进行指令重排序。
 * volaitle底层是通过内存屏障来实现可见性和有序性。内存屏障是一个CPU的指令，他的作用有两个，一是保证特定操作的执行顺序，二是保证某些变量的内存可见性。内存屏障告诉编译器和CPU，不管什么指令都不能和这条内存屏障指令重排序，另一个作用是强制刷出各种CPU的缓存资源，因此任何CPU上的线程都能读取到这些数据的最新版本。
 */
public class T {
    private int count = 10;


    public void m() {
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}
