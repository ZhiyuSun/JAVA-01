# Netty 原理与 API 网关

## 再谈谈什么是高性能*

高并发用户（Concurrent Users）  业务的理解
高吞吐量（Throughout） 研发运维的理解，QPS,TPS
低延迟（Latency）

高并发=高并发用户+高吞吐+低延迟

wck -c 40 -d 30s --latency url
-t是线程，-c是并发用户数，stdev统计的标准差

推荐书《数据密集型应用系统设计》

RTT比延迟多了客户端的到服务器的传递时间，延迟只是服务器的

一版情况下，延迟越低，吞吐越高

### 高性能的另一面

如果实现了高性能，有什么副作用呢？
1、系统复杂度 x10以上
2、建设与维护成本++++
3、故障或 BUG 导致的破坏性 x10以上

### 应对策略

稳定性建设（混沌工程）：
1、容量
2、爆炸半径。让影响范围足够的小
3、工程方面积累与改进

系统一定是会出问题的，需要手段定期检查，维护人的健康

## Netty 如何实现高性能*

网络应用开发框架
1. 异步
2. 事件驱动
3. 基于 NIO
适用于:
• 服务端
• 客户端
• TCP/UDP

阻塞说的是selector轮询和读写数据

BIO，每个线程自己处理所有流程，没有任何复用
参考多路归并。NIO里，多路指的是多个并发请求，复用selector/reactor/eventloop

### 从事件处理机制到 Reactor 模型

Reactor 模式首先是事件驱动的，有一个或者多个并发输入源，有一个 Service Handler和多个EventHandlers。
这个 Service Handler 会同步的将输入的请求多路复用的分发给相应的 Event Handler。

reactor单线程模型，多线程模型，主从模型

管道+过滤器模式

到底什么是eventloop

eventloop可以看出是一个线程

## Netty 网络程序优化

1、不要阻塞 EventLoop
2、系统参数优化
ulimit -a /proc/sys/net/ipv4/tcp_fin_timeout, TcpTimedWaitDelay
3、缓冲区优化
SO_RCVBUF/SO_SNDBUF/SO_BACKLOG/ REUSEXXX
4、心跳周期优化
心跳机制与短线重连
5、内存与 ByteBuffer 优化
DirectBuffer与HeapBuffer
6、其他优化
- ioRatio
- Watermark
- TrafficShaping

## 典型应用：API 网关

网关，在计算机领域，所有问题都可以增加一个中间层来解决

### API网关

四大职能：
- 请求接入。作为所有API接口服务请求的接入点
- 业务聚合。作为所有后端业务服务的聚合点
- 中介策略。实现安全、验证、路由、过滤、流控等策略
- 统一管理。对所有API服务和策略进行统一管理

网关的分类：

流量网关：关注稳定与安全
- 全局性流控
- 日志统计
- 防止 SQL 注入
- 防止 Web 攻击
- 屏蔽工具扫描
- 黑白 IP 名单
- 证书/加解密处理

openResty,Kong 性能非常好，适合流量网关

业务网关：提供更好的服务
- 服务级别流控
- 服务降级与熔断
- 路由与负载均衡、灰度策略
- 服务过滤、聚合与发现
- 权限验证与用户等级策略
- 业务规则与参数校验
- 多级缓存策略

springcloudgateway zuul2 扩展性好，适合业务网关，二次开发


## 自己动手实现 API 网关*

上节课代码在nio01里，这节课在nio02里
作业不会，直接参考nio02，outbound httpclient4

## 第 5 课总结回顾与作业实践