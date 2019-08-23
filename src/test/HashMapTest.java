package test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/8/2 14:57
 * @Description:
 */
public class HashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> myMap = new ConcurrentHashMap<>();
        myMap.put("1", "2");
        myMap.put("3", "9");
        String value = myMap.put("10", "3");
//        for (String key : myMap.keySet()) {
//            if(key.equals("3")) {
//                myMap.put("12", "4");
//            }
//        }
        Iterator<String> iterator = myMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key.equals("10")) {
                iterator.remove();
            }
        }
        System.out.println(value);
        System.out.println("10".hashCode());
    }

}
