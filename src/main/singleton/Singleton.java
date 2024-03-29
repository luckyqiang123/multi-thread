package main.singleton;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/6/1 14:47
 * @Description:(IoDH)
 */
public class Singleton {
    private Singleton() {
    }
    private static class HolderClass {
        private final static Singleton instance = new Singleton();
    }
    public static Singleton getInstance() {
        return HolderClass.instance;
    }
    public static void main(String args[]) {
        Singleton s1, s2;
        s1 = Singleton.getInstance();
        s2 = Singleton.getInstance();
        System.out.println(s1==s2);
    }
}
