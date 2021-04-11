# 分布式缓存-Redis高可用/Redisson/Hazelcast

智商和情商差异没那么大

很多都是信息差

## 1.Redis集群与高可用

### Redis 主从复制：从单机到多节点~ mysql主从

极简的风格, 从节点执行:
> SLAVEOF 127.0.0.1 6379
也可以在配置文件中设置。
注意：从节点只读、异步复制。

分布式，一般要求节点为奇数个

当节点变成从库时，就会从主库来同步，差异的地方给去掉

主从状态下，从库不让写

failover，主库down了，从库变主

### Redis Sentinel 主从切换：走向高可用-MHA

可以做到监控主从节点的在线状态，并做切换（基于raft协议）。
两种启动方式：
> redis-sentinel sentinel.conf
> redis-server redis.conf --sentinel

sentinel.conf配置：
sentinel monitor mymaster 127.0.0.1 6379 2
sentinel down-after-milliseconds mymaster 60000
sentinel failover-timeout mymaster 180000
sentinel parallel-syncs mymaster 1

不需要配置从节点，也不需要配置其他sentinel信息

redis sentinel原理介绍：http://www.redis.cn/topics/sentinel.html
redis复制与高可用配置：https://www.cnblogs.com/itzhouq/p/redis5.html

### Redis Cluster：走向分片~ 全自动分库分表

主从复制从容量角度来说，还是单机。
Redis Cluster通过一致性hash的方式，将数据分散到多个服务器节点：先设计16384
个哈希槽，分配到多台redis-server。当需要在Redis Cluster中存取一个key时，
Redis 客户端先对key 使用crc16 算法计算一个数值，然后对16384 取模，这样每个
key 都会对应一个编号在0-16383 之间的哈希槽，然后在此槽对应的节点上操作。
> cluster-enabled yes
注意：
1、节点间使用gossip通信，规模<1000
2、默认所有槽位可用，才提供服务
3、一般会配合主从模式使用
redis cluster介绍：http://redisdoc.com/topic/cluster-spec.html
redis cluster原理：https://www.cnblogs.com/williamjie/p/11132211.html
redis cluster详细配置：https://www.cnblogs.com/renpingsheng/p/9813959.html

## 2.Redisson介绍

基于Netty NIO，API线程安全。
亮点：大量丰富的分布式功能特性，比如JUC的线程安全集合和工具的分布式版本，分布
式的基本数据类型和锁等。
官网：https://github.com/redisson/redisson

jedis线程不安全
lettuce官方推荐，线程安全

示例1：
分布式锁，RLock ==> 能实现跨节点的锁状态
示例2：
分布式的Map，RMap ==> 全集群共享的，一个机器改了，其他都会自动同步
代码演示。

## 3.Hazelcast介绍

Hazelcast IMGD(in-memory data grid) 是一个标准的内存网格系统；它具有以下的一
些基本特性：
1. 分布式的：数据按照某种策略尽可能均匀的分布在集群的所有节点上。
2. 高可用：集群的每个节点都是active 模式，可以提供业务查询和数据修改事务；部
分节点不可用，集群依然可以提供业务服务。
3. 可扩展的：能按照业务需求增加或者减少服务节点。
4. 面向对象的：数据模型是面向对象和非关系型的。在java 语言应用程序中引入
hazelcast client api是相当简单的。
5. 低延迟：基于内存的，可以使用堆外内存。
文档：https://docs.hazelcast.org/docs/4.1.1/manual/html-single/index.html

## 4.总结回顾与作业实践