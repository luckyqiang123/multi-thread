package main.singleton;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/6/1 14:14
 * @Description:
 */
public class Client {
    public static void main(String[] args) {
        LoadBalancer balancer, balancer1, balancer2, balancer3;
        balancer = LoadBalancer.getLoadBalancer();
        balancer1 = LoadBalancer.getLoadBalancer();
        balancer2 = LoadBalancer.getLoadBalancer();
        balancer3 = LoadBalancer.getLoadBalancer();

        if(balancer1 == balancer && balancer1 ==balancer2 && balancer3 ==balancer2){
            System.out.println("yizhixing");
        }

        balancer.addServer("s1");
        balancer.addServer("s12");
        balancer.addServer("s13");
        balancer.addServer("s14");

        for (int i = 0; i < 10; i++) {
            String server = balancer1.getServer();
            System.out.println("分发请求至服务器： " + server);
        }
    }
}
