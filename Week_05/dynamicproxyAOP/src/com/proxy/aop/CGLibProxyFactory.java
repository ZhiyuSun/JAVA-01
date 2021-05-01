package com.proxy.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *  拦截方法
 */
public class CGLibProxyFactory implements MethodInterceptor {

    // 创建代理类对象实例 这里不需要传入接口信息
    // TODO 因为cglib动态代理采取的是创建原始类的子类的方式
    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    /**
     *
     * @param o             原始对象
     * @param method
     * @param args          方法的参数
     * @param methodProxy   方法级别的代理，子类调用父类方法？，
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        methodBefore();
        Object obj = methodProxy.invokeSuper(o, args);
        methodAfter();
        return obj;
    }

    private void methodBefore() {
        System.out.println("before");
    }

    private void methodAfter() {
        System.out.println("after");
    }
}
