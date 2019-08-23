package main.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/6/1 14:07
 * @Description:饿汉模式
 */
public class LoadBalancer {
    /**
    private static LoadBalancer instance = null;
     **/

    /**饿汉模式 **/
    private static LoadBalancer instance = new LoadBalancer();


    private List serverList = null;

    private LoadBalancer(){
        serverList = new ArrayList();
    }

    public static LoadBalancer getLoadBalancer(){
        /**
        if(instance == null){
            instance = new LoadBalancer();
        }
         **/

        return instance;
    }

    public void addServer(String server){
        serverList.add(server);
    }

    public void removeServer(String server){
        serverList.remove(server);
    }

    public String getServer(){
        Random random = new Random();
        int i = random.nextInt(serverList.size());
        return (String)serverList.get(i);
    }
}
