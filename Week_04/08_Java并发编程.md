# Java并发编程

会的东西越多，可能性越大

Java,go,C++，有一门够吃饭就够了，一专多能
把一门学通了就行，其他都当兴趣爱好

## 常用线程安全类型*

### JDK基础数据类型与集合类

List：ArrayList、LinkedList、Vector、Stack
Set：LinkedSet、HashSet、TreeSet
Queue->Deque->LinkedList
线性数据结构都源于Collection接口，并且拥有迭代器

Map：HashMap、LinkedHashMap、TreeMap
Dictionary->HashTable->Properties

原生类型，数组类型，对象引用类型

### ArrayList

基本特点：基于数组，便于按index访问，超过数组需要扩容，扩容成本较高
用途：大部分情况下操作一组数据都可以用ArrayList
原理：使用数组模拟列表，默认大小10，扩容x1.5，newCapacity = oldCapacity +
(oldCapacity >> 1)

安全问题：
1、写冲突：
- 两个写，相互操作冲突
2、读写冲突：
- 读，特别是iterator的时候，数据个数变了，拿到了非预期数据或者报错
- 产生ConcurrentModificationException

### LinkedList

基本特点：使用链表实现，无需扩容
用途：不知道容量，插入变动多的情况
原理：使用双向指针将所有节点连起来

安全问题：
1、写冲突：
- 两个写，相互操作冲突
2、读写冲突：
- 读，特别是iterator的时候，数据个数变了
，拿到了非预期数据或者报错
- 产生ConcurrentModificationException

### List线程安全的简单办法

既然线程安全是写冲突和读写冲突导致的
最简单办法就是，读写都加锁。
例如：
- 1.ArrayList的方法都加上synchronized -> Vector
- 2.Collections.synchronizedList，强制将List的操作加上同步
- 3.Arrays.asList，不允许添加删除，但是可以set替换元素
- 4.Collections.unmodifiableList，不允许修改内容，包括添加删除和set

### CopyOnWriteArrayList

核心改进原理：
1、写加锁，保证不会写混乱
2、写在一个Copy副本上，而不是原始数据上
（GC young区用复制，old区用本区内的移动）

读写分离
最终一致


### HashMap

基本特点：空间换时间，哈希冲突不大的情况下查找数据性能很高
用途：存放指定key的对象，缓存对象
原理：使用hash原理，存k-v数据，初始容量16，扩容x2，负载因子0.75
JDK8以后，在链表长度到8 & 数组长度到64时，使用红黑树。

安全问题：
1、写冲突，
2、读写问题，可能会死循环
3、keys()无序问题

### LinkedHashMap

基本特点：继承自HashMap，对Entry集合添加了一个双向链表
用途：保证有序，特别是Java8 stream操作的toMap时使用
原理：同LinkedList，包括插入顺序和访问顺序

安全问题：
同HashMap

可以有插入顺序，和访问顺序
可以最简单的实现一个LRU

### ConcurrentHashMap

分段锁
默认16个Segment，降低锁粒度。
concurrentLevel = 16
想想：
Segment[] ~ 分库
HashEntry[] ~ 分表

类似分库分表的机制
保证了安全性，得到并发的性能的提升

Java8中，去掉了分片锁机制，使用了红黑树机制

Java 7为实现并行访问，引入了Segment这一结构，实现了分段锁，理论上最大并发度与Segment个数相等。

Java 8为进一步提高并发性，摒弃了分段锁的方案，而是直接使用一个大的数组。

### 总结

arrayList 
linkedList 
并发读写不安全 使用副本机制改进
copyOnWriteArrayList

HashMap
LinkedHashMap
并发读写不安全，使用分段锁或者CAS
ConcurrentHashMap

## 并发编程相关内容

### 线程安全操作利器- ThreadLocal

- 线程本地变量
- 场景: 每个线程一个副本
- 不改方法签名静默传参
- 及时进行清理

可以看做是Context模式，减少显式传递参数

### 四两拨千斤- 并行Stream

### 伪并发问题

- 跟并发冲突问题类似的场景很多
- 比如浏览器端，表单的重复提交问题
-- 1、客户端控制（调用方），点击后按钮不可用，跳转到其他页
-- 2、服务器端控制（处理端），给每个表单生成一个编号，提交时判断重复

第二种方法会比较常用

浏览器可能会缓存get请求，post不会
如何防止，在get请求后面加一个随机数?=

### 分布式下的锁和计数器

- 分布式环境下，多个机器的操作，超出了线程的协作机制，一定是并行的
- 例如某个任务只能由一个应用处理，部署了多个机器，怎么控制
- 例如针对用户的限流是每分钟60次计数，API服务器有3台，用户可能随机访问到任何
一台，怎么控制？（秒杀场景是不是很像？库存固定且有限。）

## 并发编程经验总结*

加锁需要考虑的问题
1. 粒度
2. 性能
3. 重入
4. 公平
5. 自旋锁（spinlock）
6. 场景: 脱离业务场景谈性能都是耍流氓

线程间协作与通信
1. 线程间共享:
• static/实例变量(堆内存)
• Lock
• synchronized
2. 线程间协作:
• Thread#join()
• Object#wait/notify/notifyAll
• Future/Callable
• CountdownLatch
• CyclicBarrier

## 并发编程常见面试题*


## 第8课总结回顾与作业实践

每周要拿10-12个小时，否则会大打折扣
