# Java并发编程

JVM，NIO, 并发编程，基础里必问的
数据库相关优化，分库分表，分布式，缓存，队列，rpc，微服务

最后三个模块，讲技术架构，重构，软实力

作业30%，面技术没问题

## 多线程基础

### 为什么会有多线程

本质原因是摩尔定律失效-> 多核+分布式时代的来临。

知鱼君注：硬件发展的快，但现在摩尔定律到头了
单机多核还是不够，这时候就要分布式

多CPU 核心意味着同时操作系统有更多的并行计算资源可以使用。
操作系统以**线程**作为基本的调度单元。

单线程是最好处理不过的。
线程越多，管理复杂度越高。

跟我们程序员都喜欢自己单干一样。
《人月神话》里说加人可能干得更慢。

可见多核时代的编程更有挑战。

线程和进程的区别：
进程是操作系统分配资源的基本单位，CPU运行的时候以线程为调度单位
进程是资源的分配单位，线程是CPU调度的最小单位

秦老师：90%的程序员写的多线程都是错的

## Java多线程*

### Java线程的创建过程

Java提供了Thread类，Java层面
start方法，JVM的JavaThread
创建了操作系统线程,os层面

Java里的线程是裸线程，只要new起来，就对应操作系统里的一个真实的线程

守护线程，setDaemon，可以跟主线程一起终止掉

Thread#start():创建新线程
Thread#run()：本线程调用

基础接口Runnable，穿件一个示例，或者实现接口

### 线程状态

start() Runnable Running Non-Runnable notify 

## Thread类

各个方法的含义
wait & notify

### Thread的状态改变操作

Thread.sleep是当前的线程释放掉对cpu的占用
yield用处不大
t.join()
obj.wait()
obj.notify()

1. Thread.sleep(long millis)，一定是当前线程调用此方法，当前线程进入 TIMED_WAITING 状态，但不释放对象锁，
millis 后线程自动苏醒进入就绪状态。作用：给其它线程执行机会的最佳方式。
2. Thread.yield()，一定是当前线程调用此方法，当前线程放弃获取的 CPU 时间片，但不释放锁资源，由运行状态变为就
绪状态，让 OS 再次选择线程。作用：让相同优先级的线程轮流执行，但并不保证一定会轮流执行。实际中无法保证yield() 达到让步目的，因为让步的线程还有可能被线程调度程序再次选中。Thread.yield() 不会导致阻塞。该方法与sleep() 类似，只是不能由用户指定暂停多长时间。
3. t.join()/t.join(long millis)，当前线程里调用其它线程 t 的 join 方法，当前线程进入WAITING/TIMED_WAITING 状态，当前线程不会释放已经持有的对象锁，因为内部调用了t.wait，所以会释放t这个对象上的同步锁。线程t执行完毕或者millis 时间到，当前线程进入就绪状态。其中，wait操作对应的notify是由jvm底层的线程执行结束前触发的。
4. obj.wait()，当前线程调用对象的 wait() 方法，当前线程释放obj对象锁，进入等待队列。依靠 notify()/notifyAll() 唤醒或者 wait(long timeout) timeout 时间到自动唤醒。唤醒会，线程恢复到wait时的状态。
5. obj.notify() 唤醒在此对象监视器上等待的单个线程，选择是任意性的。notifyAll() 唤醒在此对象监视器上等待的所有线程。

### Thread的中断与异常处理

1. 线程内部自己处理异常，不溢出到外层（Future可以封装）。
2. 如果线程被 Object.wait, Thread.join和Thread.sleep 三种方法之一阻塞，此时调用该线程的
interrupt() 方法，那么该线程将抛出一个 InterruptedException 中断异常（该线程必须事先
预备好处理此异常），从而提早地终结被阻塞状态。如果线程没有被阻塞，这时调用
interrupt() 将不起作用，直到执行到 wait/sleep/join 时,才马上会抛出InterruptedException。
3. 如果是计算密集型的操作怎么办？
分段处理，每个片段检查一下状态，是不是要终止。

### Thread状态

这张图表现了Thread状态的全过程

阻塞是被动的操作
RWB
可运行，等待，block

项目代码地址：ConCurrency/0301
0301作为根目录打开

waitandnotify的那个例子, 生产和消费者模型

## 线程安全*

多个线程竞争同一资源时，如果对资源的访问顺序敏感，就称存在竞态条件。
导致竞态条件发生的代码区称作临界区。
不进行恰当的控制，会导致线程安全问题同步/加

### 并发相关的性质

**原子性**：原子操作，注意跟事务ACID 里原子性的区别与联系

对基本数据类型的变量的读取和赋值操作是原子性操作，即这些操作是不可被中断的，要么执行，要么不执行。

**可见性**：对于可见性，Java 提供了volatile 关键字来保证可见性。

当一个共享变量被volatile 修饰时，它会保证修改的值会立即被更新到主存，当有其他线程需要读取时，它会去内存中读取新值。

另外，通过synchronized 和Lock 也能够保证可见性，synchronized 和Lock 能保证同一时刻只有一个线程获取锁然后执行同步代码，并且在释放锁之前会将对变量的修改刷新到主存当中。

volatile 并不能保证原子性。

**有序性**：Java 允许编译器和处理器对指令进行重排序，但是重排序过程不会影响到单线程程序的执行，却会影
响到多线程并发执行的正确性。可以通过volatile 关键字来保证一定的“有序性”（synchronized 和Lock
也可以）。
happens-before 原则（先行发生原则）：
1. 程序次序规则：一个线程内，按照代码先后顺序
2. 锁定规则：一个unLock 操作先行发生于后面对同一个锁的lock 操作
3. Volatile 变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作
4. 传递规则：如果操作A 先行发生于操作B，而操作B 又先行发生于操作C，则可以得出A 先于C
5. 线程启动规则：Thread 对象的start() 方法先行发生于此线程的每个一个动作
6. 线程中断规则：对线程interrupt() 方法的调用先行发生于被中断线程的代码检测到中断事件的发生
7. 线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join() 方法结束、
Thread.isAlive() 的返回值手段检测到线程已经终止执行
8. 对象终结规则：一个对象的初始化完成先行发生于他的finalize() 方法的开始

### 一个简单的实际例子

给incr加Syncroized同步块

### Synchronized的实现

1. 使用对象头标记字(Object monitor)
2. Synchronized 方法优化
3. 偏向锁: BiaseLock

锁的粒度块越小越好

### volatile

1. 每次读取都强制从主内存刷数据
2. 适用场景： 单个线程写；多个线程读
3. 原则： 能不用就不用，不确定的时候也不用
4. 替代方案： Atomic 原子操作类

### final

表示不变

final static 常量

例子里面，可以sleep，然后jstack -l去分析

物理线程说的就是CPU核心，编程意义上说的线程，是CPU的调度单位

空的main函数run起来有几个线程，一般情况下是5个

## 线程池原理与应用*

### 线程池

1. Excutor: 执行者– 顶层接口
2. ExcutorService: 接口API
3. ThreadFactory: 线程工厂
4. Executors: 工具类

### Executor-执行者

线程池从功能上看，就是一个任务执行器

submit 方法 -> 有返回值，用 Future 封装
execute 方法 -> 无返回值

submit 方法还异常可以在主线程中get捕获到
execute 方法执行任务是捕捉不到异常的

### ExecutorService

shutdown()：停止接收新任务，原来的任务继续执行
shutdownNow()：停止接收新任务，原来的任务停止执行
boolean awaitTermination(timeOut, unit)：阻塞当前线程，返回是否线程都执行完

### ThreadFactory

ThreadPoolExecutor 提交任务逻辑:
1. 判断 corePoolSize 【创建】
2. 加入 workQueue
3. 判断 maximumPoolSize 【创建】
4. 执行拒绝策略处理器

### 线程池参数

缓冲队列

BlockingQueue 是双缓冲队列。BlockingQueue 内部使用两条队列，允许两个线程同
时向队列一个存储，一个取出操作。在保证并发安全的同时，提高了队列的存取效率。

1. ArrayBlockingQueue:规定大小的 BlockingQueue，其构造必须指定大小。其所含
的对象是 FIFO 顺序排序的。
2. LinkedBlockingQueue:大小不固定的 BlockingQueue，若其构造时指定大小，生成
的 BlockingQueue 有大小限制，不指定大小，其大小有 Integer.MAX_VALUE 来
决定。其所含的对象是 FIFO 顺序排序的。
3. PriorityBlockingQueue:类似于 LinkedBlockingQueue，但是其所含对象的排序不
是 FIFO，而是依据对象的自然顺序或者构造函数的 Comparator 决定。
4. SynchronizedQueue:特殊的 BlockingQueue，对其的操作必须是放和取交替完成。

拒绝策略

1. ThreadPoolExecutor.AbortPolicy: 丢弃任务并抛出 RejectedExecutionException
异常。
2. ThreadPoolExecutor.DiscardPolicy：丢弃任务，但是不抛出异常。
3. ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新提
交被拒绝的任务
4. ThreadPoolExecutor.CallerRunsPolicy：由调用线程（提交任务的线程）处理该任
务

### 创建线程池方法

1. newSingleThreadExecutor
创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有
任务。如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。此线程池保证所有
任务的执行顺序按照任务的提交顺序执行。

2. newFixedThreadPool
创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。
线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会
补充一个新线程。

3. newCachedThreadPool
创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，
那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添
加新线程来处理任务。此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或
者说JVM）能够创建的最大线程大小。

4. newScheduledThreadPool
创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。

### 创建固定线程池的经验

不是越大越好，太小肯定也不好：
假设核心数为N，
1. 如果是CPU密集型应用，则线程池大小设置为N或N+1
2. 如果是IO密集型应用，则线程池大小设置为2N或2N+2

### Callable-基础接口

对比:
- Runnable#run()没有返回值
- Callable#call()方法有返回值

### Future – 基础接口

## 总结回顾与作业实践

非常推荐《Java并发编程实践》

不建议一个机器部署多个jvm，相互干扰

计算机密集型：CPU核心数，IO密集型，一倍以上

复盘了才能真正的总结，变成自己的，形成体系
作业做了，过了自己的手，脑子，才能形成直观的感性的认识

怎样提升自己的能力：把手上的项目重构好了，或者参加开源项目

如果项目中没有用到高并发，怎么应对高并发面试：先把这个课学完

课讲到一半后，会参与开源，上一期有200人参与了开源，给项目提代码的有80人

动手跑一下示例代码