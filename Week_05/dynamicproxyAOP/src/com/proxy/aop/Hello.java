package com.proxy.aop;

public class Hello implements Say {
    public void say() {
        System.out.println("hello");
    }

    public void talk() {
        System.out.println("talk");
    }
}
