# Java并发编程

90后是工作的主力军，但是大家好的事情没赶上

越往后，仅有的几个通路会越来越小，阶层固化越来越严重

计算机行业，是为数不多的还可以靠技术吃饭的
传统行业做到头就20000

能力模型：
技术能力，业务能力
技术是最基础的原料，用技术去解决问题
架构能力，综合性的解决问题
软实力，让做事更有成效，乘法的关系

## Java并发包（JUC）

### JDK核心的包

java.util.concurrency

java最基础
javax扩展包
sun sun的jdk实现包

### JUC

Synchronized/wait 锁
sum++ 多线程安全 原子类
new Thread()管理 线程池
线程间协作信号量 工具类
线程安全集合类 集合类

锁机制类 Locks : Lock, Condition, ReentrantLock, ReadWriteLock,LockSupport
原子操作类 Atomic : AtomicInteger, AtomicLong, LongAdder
线程池相关类 Executer : Future, Callable, Executor, ExecutorService
信号量三组工具类 Tools : CountDownLatch, CyclicBarrier, Semaphore
并发集合类 Collections : CopyOnWriteArrayList, ConcurrentMap

## 到底什么是锁*

回忆一下，上节课讲过的，
synchronized 可以加锁，
wait/notify 可以看做加锁和解锁。

那为什么还需要一个显式的锁呢？

synchronized方式的问题：
1、同步块的阻塞无法中断（不能Interruptibly）
2、同步块的阻塞无法控制超时（无法自动解锁）
3、同步块无法异步处理锁（即不能立即知道是否可以拿到锁）
4、同步块无法根据条件灵活的加锁解锁（即只能跟同步块范围一致）

1. 使用方式灵活可控
2. 性能开销小
3. 锁工具包: java.util.concurrent.locks
思考: Lock 的性能比 synchronized 高吗？

已经完全听不懂了

## 用锁的最佳实践

《Java并发编程：设计原则与模式》

Doug Lea《Java 并发编程：设计原则与模式》一书中，
推荐的三个用锁的最佳实践，它们分别是：
1. 永远只在更新对象的成员变量时加锁
2. 永远只在访问可变的成员变量时加锁
3. 永远不在调用其他对象的方法时加锁

KK总结-最小使用锁：
1、降低锁范围：锁定代码的范围/作用域
2、细分锁粒度：讲一个大锁，拆分成多个小锁

## 并发原子类*

### Atomic 工具类

### 锁和无锁之争


## 并发工具类详解*

更复杂的应用场景，比如
- 我们需要控制实际并发访问资源的并发数量
- 我们需要多个线程在某个时间同时开始运行
- 我们需要指定数量线程到达某个状态再继续处理

### AQS

### Semaphore - 信号量

### CountdownLatch

### CountDownLatch与CyclicBarrier比较

### Future/FutureTask/CompletableFuture

## 第 7 课总结回顾与作业实践

 