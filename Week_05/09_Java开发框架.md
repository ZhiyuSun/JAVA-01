# Java开发框架

## Spring 技术发展

摆脱调对J2EE的强依赖
spring makes Java simple

14年 springBoot
15年 springCloud

## Spring 框架设计*

框架是工具，没有业务功能

计算机任何问题都可以加一个中间层解决

常用模块
1. Core：Bean/Context/AOP
2. Testing：Mock/TestContext
3. DataAccess: Tx/JDBC/ORM
4. Spring MVC/WebFlux: web

5. Integration: remoting/JMS/WS
6. Languages: Kotlin/Groovy

java语言只是皮毛，牛逼的是java技术体系和JVM
go还要很多东西重复造轮子
java和spring相互成就

## Spring AOP 详解*

AOP-面向切面编程
Spring 早期版本的核心功能，管理对象生命周期与对象装配。
为了实现管理和装配，一个自然而然的想法就是，加一个中间层代理（字节码增强）来实现所有对象的托管。

IoC-控制反转
也称为DI（Dependency Injection）依赖注入。
对象装配思路的改进。
从对象A 直接引用和操作对象B，变成对象A 里指需要依赖一个接口IB，系统启动和装配阶段，把IB 接口的实例对象注入到对象A，这样A 就不需要依赖一个IB 接口的具体实现，也就是类B。
从而可以实现在不修改代码的情况，修改配置文件，即可以运行时替换成注入IB 接口另一实现类C的一个对象实例。

## Spring Bean 核心原理*


## Spring XML 配置原理*


## Spring Messaging 等技术


## 第9课总结回顾与作业实践