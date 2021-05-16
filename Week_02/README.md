# 学习笔记

## Week02 作业题目（周三）：

1.（选做）使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例。

https://github.com/ZhiyuSun/JAVA-01/blob/main/Week_02/homework/01_GC%E6%97%A5%E5%BF%97%E5%88%86%E6%9E%90.md

2.（选做）使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。

3.（选做）如果自己本地有可以运行的项目，可以按照 2 的方式进行演练。

4.（必做）根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 和堆内存的总结，提交到 GitHub。

自己Java基础太差，要把总结写的完美很难。自己在本地模拟了老师课上跑的几个例子，并且参考一些其他资料，总结了各个GC特点如下：

串行GC（Serial GC）: 单线程执行，应用需要暂停；
并行GC（ParNew、Parallel Scavenge、Parallel Old）: 多线程并行地执行垃圾回收，
关注与高吞吐；
CMS（Concurrent Mark-Sweep）: 多线程并发标记和清除，关注与降低延迟；
G1（G First）: 通过划分多个内存区域做增量整理和回收，进一步降低延迟；
ZGC（Z Garbage Collector）: 通过着色指针和读屏障，实现几乎全部的并发执行，几毫秒级别的延迟，线性可扩展；

## Week02 作业题目（周日）：

1.（选做）运行课上的例子，以及 Netty 的例子，分析相关现象。
1.（必做）写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801 ，代码提交到 GitHub

