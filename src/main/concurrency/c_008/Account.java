package main.concurrency.c_008;

import java.util.concurrent.TimeUnit;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/7/28 18:20
 * @Description:对业务代码写方法加锁，没对读方法加锁，容易造成脏读，读到在写的过程中还没有完成的数据
 */
public class Account {
    String name;
    double balance;

    public synchronized void set(String name, double balance) {
        this.name = name;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public /*synchronized */double getBalance(String name) {
        return this.balance;
    }

    public static void main(String[] args) {
        Account a = new Account();
        new Thread(()->a.set("zhangzj", 100.0)).start();
        try {
            TimeUnit.SECONDS.sleep((1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(a.getBalance("zhangzj"));

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(a.getBalance("zhangzj"));
    }
}
