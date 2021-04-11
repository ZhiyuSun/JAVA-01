# 学习笔记

Week12 作业题目：

## 必做

周三作业：

### 1.（必做）配置 redis 的主从复制，sentinel 高可用，Cluster 集群。**

#### 主从复制操作步骤：

- 复制一份Redis-x64-3.2.100的文件夹，为Redis-x64-3.2.100_slave
- 修改Redis-x64-3.2.100_slave/redis.windows.conf里的配置文件

```
port 6380
```

- 分别启动两个redis的节点，端口分别以6379和6380启动

```
redis-server redis.windows.conf
```

- 分别进入两个redis

```
redis-cli -h 127.0.0.1 -p 6379
redis-cli -h 127.0.0.1 -p 6380
```

- 在6380节点的终端上敲命令，变成从节点

```
slaveof 127.0.0.1 6379
```

- 测试。
    - 6379上写数据，看6380上数据是否同步
    - 在从库上写数据，从库写入不成功


![](https://raw.githubusercontent.com/ZhiyuSun/JAVA-01/main/Week_12/pic/%E4%B8%BB%E4%BB%8E1.png)

![](https://raw.githubusercontent.com/ZhiyuSun/JAVA-01/main/Week_12/pic/%E4%B8%BB%E4%BB%8E2.png)

#### sentinel高可用

- 复制一份Redis-x64-3.2.100的文件夹，重命名为Redis-x64-3.2.100_sentinel
- 给Redis-x64-3.2.100_sentinel新增sentinel的配置文件sentinel.conf

```
port 26379
# master
sentinel monitor master 127.0.0.1 6379 1
sentinel down-after-milliseconds master 5000
sentinel failover-timeout master 180000
sentinel parallel-syncs master 1
```

- 在原来的主6379上面执行shutdown，观察主从切换情况
- sentinel检查到变化，发生主从切换

- 6379和6380变换了主从关系，同时redis.conf文件也被修改
- 测试
    - 6379已变成从库，无法写数据
    - 6380上写数据，可同步到主库

![](https://raw.githubusercontent.com/ZhiyuSun/JAVA-01/main/Week_12/pic/sentinel.png)

#### redis集群

todo

周日作业：

### 1.（必做）搭建 ActiveMQ 服务，基于 JMS，写代码分别实现对于 queue 和 topic 的消息生产和消费，代码提交到 github。

代码地址：

queue:

https://github.com/ZhiyuSun/JAVA-01/tree/main/Week_12/activemq/src/main/java/queue

topic:

https://github.com/ZhiyuSun/JAVA-01/tree/main/Week_12/activemq/src/main/java/topic


## 选做

2.（选做）练习示例代码里下列类中的作业题:
08cache/redis/src/main/java/io/kimmking/cache/RedisApplication.java

3.（选做☆）练习 redission 的各种功能。

4.（选做☆☆）练习 hazelcast 的各种功能。

5.（选做☆☆☆）搭建 hazelcast 3 节点集群，写入 100 万数据到一个 map，模拟和演 示高可用。

2.（选做）基于数据库的订单表，模拟消息队列处理订单：

一个程序往表里写新订单，标记状态为未处理 (status=0);
另一个程序每隔 100ms 定时从表里读取所有 status=0 的订单，打印一下订单数据，然后改成完成 status=1；
（挑战☆）考虑失败重试策略，考虑多个消费程序如何协作。
3.（选做）将上述订单处理场景，改成使用 ActiveMQ 发送消息处理模式。

4.（选做）使用 java 代码，创建一个 ActiveMQ Broker Server，并测试它。

5.（挑战☆☆）搭建 ActiveMQ 的 network 集群和 master-slave 主从结构。

6.（挑战☆☆☆）基于 ActiveMQ 的 MQTT 实现简单的聊天功能或者 Android 消息推送。

7.（挑战☆）创建一个 RabbitMQ，用 Java 代码实现简单的 AMQP 协议操作。

8.（挑战☆☆）搭建 RabbitMQ 集群，重新实现前面的订单处理。

9.（挑战☆☆☆）使用 Apache Camel 打通上述 ActiveMQ 集群和 RabbitMQ 集群，实现所有写入到 ActiveMQ 上的一个队列 q24 的消息，自动转发到 RabbitMQ。

10.（挑战☆☆☆）压测 ActiveMQ 和 RabbitMQ 的性能。