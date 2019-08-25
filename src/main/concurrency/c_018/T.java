package main.concurrency.c_018;

/**
 * @ClassName: T
 * @Description: TODO 不要以字符串常量作为锁定对象，在下面，m1和m2锁定的其实是同一个对象
 * 有时候可能会和类库中锁定的字符串是同一个，造成死锁阻塞，因为和类库用了同一把锁
 * @Author: zhangzhiqiang
 * @Date: 2019-08-25 17:12
 * @Company: www.luckyqiang.cn
 */
public class T {
    String s1 = "Hello";
    String s2 = "Hello";

    void m1() {
        synchronized (s1) {

        }
    }

    void m2() {
        synchronized (s2) {
        }
    }
}
