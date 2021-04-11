# Java相关框架

## 从Spring 到Spring Boot

脚手架，用最少的精力run起来，普罗大众都能开箱即用

配置的发展方向：
XML--全局
注解--类
配置类--方法
Spring 4 以上的新特性，走向Spring Boot

### SpringBoot的出发点

Spring 臃肿以后的必然选择。
一切都是为了简化。
- 让开发变简单：
- 让配置变简单：
- 让运行变简单：
怎么变简单？关键词：整合
就像是SSH、SSM，国产的SpringSide
基于什么变简单：约定大于配置。

### SpringBoot如何做到简化

为什么能做到简化：
1、Spring 本身技术的成熟与完善，各方面第三方组件的成熟集成
2、Spring 团队在去web 容器化等方面的努力
3、基于MAVEN 与POM 的Java 生态体系，整合POM 模板成为可能
4、避免大量maven 导入和各种版本冲突
Spring Boot 是Spring 的一套快速配置脚手架，关注于自动配置，配置驱动。

### 什么是Spring Boot

Spring Boot 使创建独立运行、生产级别的Spring 应用变得容易，你可以直接运行它。
我们对Spring 平台和第三方库采用限定性视角，以此让大家能在最小的成本下上手。
大部分Spring Boot 应用仅仅需要最少量的配置。

功能特性
1. 创建独立运行的Spring 应用
2. 直接嵌入Tomcat 或Jetty，Undertow，无需部署WAR 包
3. 提供限定性的starter 依赖简化配置（就是脚手架）
4. 在必要时自动化配置Spring 和其他三方依赖库
5. 提供生产production-ready 特性，例如指标度量，健康检查，外部配置等
6. 完全零代码生产和不需要XML 配置

## Spring Boot 核心原理*

1、自动化配置：简化配置核心
基于Configuration，EnableXX，Condition

2、spring-boot-starter：脚手架核心
整合各种第三方类库，协同工具

### 为什么要约定大于配置

为什么要约定大于配置？

举例来说，JVM 有1000多个参数，但是我们不需要一个参数，就能java Hello。

优势在于，开箱即用：
一、Maven 的目录结构：默认有resources 文件夹存放配置文件。默认打包方式为jar。
二、默认的配置文件：application.properties 或application.yml 文件
三、默认通过spring.profiles.active 属性来决定运行环境时的配置文件。
四、EnableAutoConfiguration 默认对于依赖的starter 进行自动装载。
五、spring-boot-start-web 中默认包含spring-mvc 相关依赖以及内置的web容器，使得构建一个web 应用更加简单。

## Spring Boot Starter 详解*


## JDBC 与数据库连接池*

### JDBC

JDBC 定义了数据库交互接口：
DriverManager
Connection
Statement
ResultSet
后来又加了DataSource--Pool

### 数据库连接池

C3P0
DBCP--Apache CommonPool
Druid
Hikari

## ORM-Hibernate/MyBatis*

### Hibernate

ORM（Object-Relational Mapping） 表示对象关系映射。

Hibernate 是一个开源的对象关系映射框架，它对JDBC 进行了非常轻量级的对象封装，它将POJO 与数据库表建立映射关系，是一个全自动的orm 框架，hibernate 可以自动生成SQL 语句，自动执行，使得Java 程序员可以使用面向对象的思维来操纵数据库。

Hibernate 里需要定义实体类和hbm 映射关系文件（IDE 一般有工具生成）。
Hibernate 里可以使用HQL、Criteria、Native SQL三种方式操作数据库。
也可以作为JPA 适配实现，使用JPA 接口操作。

#### MyBatis

MyBatis和Hibernate在国内使用比是7:3，国外是1:1

MyBatis 是一款优秀的持久层框架，它支持定制化SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的XML或注解来配置和映射原生信息，将接口和Java 的POJOs(Plain Old Java Objects,普通的Java 对象)映射成数据库中的记录。

### 半自动化ORM

1、需要使用映射文件mapper.xml 定义map规则和SQL

2、需要定义mapper/DAO，基于xml 规则，操作数据库

可以使用工具生成基础的mapper.xml 和mapper/DAO

一个经验就是，继承生成的mapper，而不是覆盖掉

也可以直接在mapper 上用注解方式配置SQL


### MyBatis 与Hibernate 比较

MyBatis 与Hibernate 的区别与联系？
Mybatis 优点：原生SQL（XML 语法），直观，对DBA 友好
Hibernate 优点：简单场景不用写SQL（HQL、Cretiria、SQL）
Mybatis 缺点：繁琐，可以用MyBatis-generator、MyBatis-Plus 之类的插件
Hibernate 缺点：对DBA 不友好

## Spring 集成ORM/JPA*

JPA 的全称是Java Persistence API，
即Java 持久化API，是一套基于ORM 的规范，
内部是由一系列的接口和抽象类构成。
JPA 通过JDK 5.0 注解描述对象-关系表映射关系
，并将运行期的实体对象持久化到数据库中。
核心EntityManager

### Spring管理事务

JDBC 层，数据库访问层，怎么操作事务？编程式事务管理
Spring 怎么做到无侵入实现事务？声明式事务管理：事务管理器+AOP

Spring 声明式事务配置参考
事务的传播性：
@Transactional(propagation=Propagation.REQUIRED)
事务的隔离级别：
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
读取未提交数据(会出现脏读, 不可重复读) 基本不使用
只读：
@Transactional(readOnly=true)
该属性用于设置当前事务是否为只读事务，设置为true 表示只读，false 则表示可读写，默认值为false。
事务的超时性：
@Transactional(timeout=30)
回滚：
指定单一异常类：@Transactional(rollbackFor=RuntimeException.class)
指定多个异常类：@Transactional(rollbackFor={RuntimeException.class, Exception.class})

非常不建议一个service调用另一个service

## Spring Boot 集成ORM/JPA

### Spring/Spring Boot 使用ORM 的经验

1、本地事务（事务的设计与坑）
2、多数据源（配置、静态制定、动态切换）
3、线程池配置（大小、重连）
4、ORM 内的复杂SQL，级联查询
5、ORM 辅助工具和插件

## 第10 课总结回顾与作业实践