package com.proxy.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *  组合模式
 */
public class DynamicProxyFactory implements InvocationHandler{
    private Object target;

    public DynamicProxyFactory(Object obj) {
        this.target = obj;
    }

    // public Object getProxy() {
    //     return  Proxy.newProxyInstance(
    //             target.getClass().getClassLoader(),
    //             target.getClass().getInterfaces(),
    //             this);
    // }

    @SuppressWarnings("unchecked")
    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodBefore();
        Object obj = method.invoke(target, args);
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
