package main;

import java.lang.reflect.Method;

/**
 * @author: luckyqiang
 * @company: www.luckyqiang.cn
 * @Date: 2019/4/28 19:41
 * @Description:
 */
public interface Interceptor {
    public boolean before(Object proxy, Object target, Method method, Object[] args);

    public void around(Object proxy, Object target, Method method, Object[] args);

    public void after(Object proxy, Object target, Method method, Object[] args);
}
