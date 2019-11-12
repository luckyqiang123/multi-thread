package main.myReentrantLock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: MyReentrantLock
 * @Description: TODO 公平锁与非公平锁差别为如果一个锁是公平的，那么锁的获取顺序就应该符合请求上的绝对时间顺序；ReentrantLock的构造方法无参时是构造非公平锁；公平锁为了保证时间上的绝对顺序，需要频繁的上下文切换，而非公平锁会降低一定的上下文切换，降低性能开销。因此，ReentrantLock默认选择的是非公平锁，则是为了减少一部分上下文切换，保证了系统更大的吞吐量
 * @Author: zhangzhiqiang
 * @Date: 2019-11-12 20:17
 * @Company: www.luckyqiang.cn
 */
public class MyReentrantLock extends AbstractQueuedSynchronizer {

    /**
     * 非公平锁获取
     * 1. 在线程获取锁的时候，如果已经获取锁的线程是当前线程的话则直接再次获取成功；
     * 2. 由于锁会被获取n次，那么只有锁在被释放同样的n次之后，该锁才算是完全释放成功
     *
     * @param acquires
     * @return
     */
    final boolean nonfairTryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        //1. 如果该锁未被任何线程占有，该锁能被当前线程获取
        if (c == 0) {
            if (compareAndSetState(0, acquires)) {
                //设置持有锁的线程为当前线程
                setExclusiveOwnerThread(current);
                return true;
            }
        }
        //2.若被占有，检查占有线程是否是当前线程
        else if (current == getExclusiveOwnerThread()) {
            // 3. 再次获取，计数加一
            int nextc = c + acquires;
            if (nextc < 0) // overflow
                //nextc小于0，说明锁可重入溢出
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        return false;
    }

    /**
     * 获取公平锁
     * 公平锁每次获取到锁为同步队列中的第一个节点，保证请求资源时间上的绝对顺序
     * 而非公平锁有可能刚释放锁的线程下次继续获取该锁，则有可能导致其他线程永远无法获取到锁，造成“饥饿”现象。
     *
     * @param acquires
     * @return
     */
    protected final boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {
            //此处与非公平锁的获取不同，增加了hasQueuedPredecessors的逻辑判断
            //该方法用来判断当前节点在同步队列中是否有前驱结点
            //如果有前驱结点说明有线程比当前线程更早地请求资源，根据公平性，当前线程请求资源失败
            //如果没有，再进行后面的判断
            if (!hasQueuedPredecessors() &&
                    compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(current);
                return true;
            }
        }
        //如果当前线程已持有锁，则进行可重入操作
        else if (current == getExclusiveOwnerThread()) {
            int nextc = c + acquires;
            if (nextc < 0)
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        return false;
    }


    /**
     * 释放非公平锁
     *
     * @param releases
     * @return
     */
    protected final boolean tryRelease(int releases) {
        //1. 同步状态减1
        int c = getState() - releases;
        //释放锁的线程必须为当前线程，否则抛出异常
        if (Thread.currentThread() != getExclusiveOwnerThread())
            throw new IllegalMonitorStateException();
        boolean free = false;
        if (c == 0) {
            //2. 只有当同步状态为0时，锁成功被释放，返回true
            free = true;
            //设置持有锁的线程为null
            setExclusiveOwnerThread(null);
        }
        // 3. 锁未被完全释放，返回false
        setState(c);
        return free;
    }
}
