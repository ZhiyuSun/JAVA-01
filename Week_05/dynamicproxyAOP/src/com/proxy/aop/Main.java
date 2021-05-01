package com.proxy.aop;

public class Main {

    public static void main(String[] args) {
        Hello hello = new Hello();
        // 原始对象调用
        hello.say();

        System.out.println("---------JDK proxy----------");

        // 测试代理对象调用
        DynamicProxyFactory dynamicProxyFactory = new DynamicProxyFactory(hello);
        Say helloProxy = dynamicProxyFactory.getProxy();
        helloProxy.say();

        System.out.println("---------CGLib proxy----------");

        CGLibProxyFactory cgLibProxyFactory = new CGLibProxyFactory();
        // 代理类实例
        Hello helloCGProxy = cgLibProxyFactory.getProxy(hello.getClass());
        helloCGProxy.say();
        // 不需要接口也能代理
        helloCGProxy.talk();

    }
}
