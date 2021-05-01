## 分布式消息--Kafka消息中间件

## 1. Kafka概念和入门

### 什么是Kafka

Kafka 是一个消息系统，由 LinkedIn 于2011年设计开发，用作 LinkedIn 的活动流
（Activity Stream）和运营数据处理管道（Pipeline）的基础。

Kafka 是一种分布式的，基于发布 / 订阅的消息系统。主要设计目标如下：
1. 以时间复杂度为 O(1) 的方式提供消息持久化能力，即使对 TB 级以上数据也能保证常数时间复杂度的访问性能。
2. 高吞吐率。即使在非常廉价的商用机器上也能做到单机支持每秒 100K 条以上消息的传输。
3. 支持 Kafka Server 间的消息分区，及分布式消费，同时保证每个 Partition 内的消息顺序传输。
4. 同时支持离线数据处理和实时数据处理。
5. Scale out：支持在线水平扩展。

Kafka只实现了Topic模式

### Kafka的基本概念

1. Broker：Kafka 集群包含一个或多个服务器，这种服务器被称为 broker。
2. Topic：每条发布到 Kafka 集群的消息都有一个类别，这个类别被称为 Topic。
（物理上不同 Topic 的消息分开存储，逻辑上一个 Topic 的消息虽然保存于一个或
多个 broker 上，但用户只需指定消息的 Topic 即可生产或消费数据而不必关心数
据存于何处）。
3. Partition：Partition 是物理上的概念，每个 Topic 包含一个或多个 Partition。
4. Producer：负责发布消息到 Kafka broker。
5. Consumer：消息消费者，向 Kafka broker 读取消息的客户端。
6. Consumer Group：每个 Consumer 属于一个特定的 Consumer Group（可为每个
Consumer 指定 group name，若不指定 group name 则属于默认的 group）。

Partition和Consumer Group是Kafka特有的

### Topic和Partition

多Partition支持水平扩展和并行处理，顺序写入提升吞吐性能

### Partition和Replica

每个partition可以通过副本因子添加多个副本

### Topic特性

1. 通过partition增加可扩展性
2. 通过顺序写入达到高吞吐
3. 多副本增加容错性

## 2. Kafka的简单使用*

### 单机安装部署

1、kafka安装
http://kafka.apache.org/downloads
下载2.6.0或者2.7.0，解压。

2、启动kafka：
命令行下进入kafka目录
修改配置文件 vim config/server.properties
打开 listeners=PLAINTEXT://localhost:9092
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties

3、命令行操作Kafka
bin/kafka-topics.sh --zookeeper localhost:2181 --list
bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic testk --partitions 4 --
replication-factor 1
bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic testk
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --
topic testk
bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic testk

多个patition是无序的

4、简单性能测试
bin/kafka-producer-perf-test.sh --topic testk --num-records 100000 --record-size
1000 --throughput 2000 --producer-props bootstrap.servers=localhost:9092
bin/kafka-consumer-perf-test.sh --bootstrap-server localhost:9092 --topic testk --
fetch-size 1048576 --messages 100000 --threads 1

## 3. Kafka的集群配置*



## 4. Kafka的高级特性*



## 5. 总结回顾与作业实践