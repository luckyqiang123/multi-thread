package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/8/3 17:06
 * @Description:
 */
public class ListTest {
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>(3);
        list1.add("name");
        list1.add("age");
        list1.add("phone");
        Iterator<String> stringIterator = list1.iterator();
        while (stringIterator.hasNext()) {
            if(stringIterator.next().equals("phone")){
                stringIterator.remove();
            }
        }
//        for (String str : list1){
//            if ("phone".equals(str)){
//                list1.remove(list1.indexOf(str));
//            }
//        }
    }

}
