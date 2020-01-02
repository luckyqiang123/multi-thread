package main.threadpool;

import java.util.concurrent.*;

/**
 * @ClassName: ThreadPoolDemo
 * @Description: TODO
 * @Author: zhangzhiqiang
 * @Date: 2020-01-02 22:27
 * @Company: www.luckyqiang.cn
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        /**
         * 创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用他们。对于执行
         * 很多短期异步任务的程序而言，这些线程池通常可提高程序性能。调用 execute 将重用以前构造
         * 的线程(如果线程可用)。如果现有线程没有可用的，则创建一个新线程并添加到池中。终止并
         * 从缓存中移除那些已有 60 秒钟未被使用的线程。因此，长时间保持空闲的线程池不会使用任何资
         * 源。
         */
        ExecutorService executorService1 = Executors.newCachedThreadPool();

        /**
         * 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。在任意点，在大
         * 多数 nThreads 线程会处于处理任务的活动状态。如果在所有线程处于活动状态时提交附加任务，
         * 则在有可用线程之前，附加任务将在队列中等待。如果在关闭前的执行期间由于失败而导致任何
         * 线程终止，那么一个新线程将代替它执行后续的任务(如果需要)。在某个线程被显式地关闭之
         * 前，池中的线程将一直存在。
         */
        ExecutorService executorService2 = Executors.newFixedThreadPool(10);

        /**
         * Executors.newSingleThreadExecutor()返回一个线程池(这个线程池只有一个线程),
         * 这个线程池可以在线程死后(或发生异常时)重新启动一个线程来替代原来的线程继续执行下去!
         */
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();

        /**
         * 上述三种线程池，都是通过ThreadPoolExecutor的构造方法构造出线程池
         * corePoolSize：核心线程数
         * maximumPoolSize：最大线程数
         * keepAliveTime：非核心线程存活时间
         * BlockingQueue<Runnable> workQueue：等待队列
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 10, TimeUnit.SECONDS,  new ArrayBlockingQueue<>(10));
        for (int i=0; i<100; i++) {
//            threadPoolExecutor.execute(new Mytask(i));
            executorService2.execute(new Mytask(i));
        }
    }
}

class Mytask implements Runnable {
    int i;

    public Mytask(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+ "-------" + i);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
