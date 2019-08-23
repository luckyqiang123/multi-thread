package main.concurrency.c_014;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/08/14 21:09
 * @Description:volatile并不能保证多个线程共同修改变量时带来的不一致问题，也就是说volatile不能替代synchronized
 * volatile保证可见性，synchronized保证可见性和原子性
 */
public class T {

   int count = 0;

   synchronized void m() {
       for (int i= 0; i<10000; i++) {
//           count ++;
           count = count+1;
       }
   }

    public static void main(String[] args) {
        T t= new T();

        List<Thread> threads = new ArrayList<>();

        for (int i=0; i<10; i++) {
            threads.add(new Thread(t::m, "thread-"+i));
        }

        threads.forEach((o)->o.start());

        threads.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.count);
    }

}
