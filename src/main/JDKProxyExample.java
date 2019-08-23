package main;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/4/27 14:40
 * @Description:
 */
public class JDKProxyExample implements InvocationHandler {

    private Object target = null;

    public Object bind(Object target) {
        this.target = target;
        Object obje = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        return obje;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入代理逻辑方法");
        System.out.println("真实对象前服务");
        Object obj = method.invoke(target, args);
        System.out.println("真实对象后服务");
        return obj;
    }
}
