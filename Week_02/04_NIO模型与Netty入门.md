# NIO模型与Netty入门

面试高级开发都会面JVM知识

## Java Socket 编程*：如何基于 Socket 实现 Server

### socket通信模型

每个应用程序入驻了一个房间，占了一个端口，port 65535
0-1024 系统保留的

### Java实现一个最简的HTTP服务器

代码在JavaCource00/JavaCourceCodes

运行起来，用curl -i，或浏览器访问

问题：所有的请求都是单线程的
解决方案：每个请求启动一个新的线程，见HttpServer02

缺点：线程资源比较宝贵，Java会去申请操作系统的线程资源
解决方案：创建一个固定大小的线程池处理请求 见HttpServer03

可以用wrk或sb进行压测，观察QPS

总结：
类似于饭店服务员
- 单线程处理socket
- 每个请求一个线程
- 固定大小线程池处理

sleep时间，创建的线程数，-Xmx内存，这几个可以在测试的时候调整

## 深入讨论 IO*：Server 处理时到底发生了什么

### 服务器通信过程分析

这个过程中，存在两种类型操作：
- CPU 计算/业务处理
- IO 操作与等待/网络、磁盘、数据库

对于一个 IO 相关应用来说，例如通过网络访问，服务器端读取本地文件，再返回给客户端（如左图）。
这种情况下，大部分 CPU 等资源，可能就被浪费了。

不仅面临线程 /CPU 的问题，还要面对数据来回复制的问题。
这个一来，对每个业务处理过程，使用一个线程以一竿子通到底的方式，性能不是最优的，还有提升空间。

## IO 模型与相关概念*：怎么理解 NIO

阻塞、非阻塞，
同步、异步，
有什么关系和区别？
- 同步异步 是通信模式。
- 阻塞、非阻塞 是 线程处理模式。

五种IO模型

### 阻塞式IO BIO

4种同步，1种异步

#### 阻塞式IO

一般通过在 while(true) 循环中服务端会调用 accept() 方法等待接收客户端的连接的方式监听请求，请求一旦接收到一个连接请求，就可以建立通信套接字在这个通信套接字上进行读写操作，此时不能再接收其他客户端连接请求，只能等待同当前连接的客户端的操作执行完成， 不过可以通过多线程来支持多个客户端的连接

#### 非阻塞式IO

和阻塞 IO 类比，内核会立即返回，返回后获得足够的 CPU 时间继续做其它的事情。
用户进程第一个阶段不是阻塞的,需要不断的主动询问 kernel 数据好了没有；第二个阶段依然总是阻塞的。

#### IO多路复用

IO 多路复用(IO multiplexing)，也称事件驱动 IO(event-driven IO)，就是在单个线程里同时监控多个套接字，通过select 或 poll 轮询所负责的所有socket，当某个 socket 有数据到达了，就通知用户进程。

IO 复用同非阻塞 IO 本质一样，不过利用了新的 select 系统调用，由内核来负责本来是请求进程该做的轮询操作。看似比非阻塞 IO 还多了一个系统调用开销，不过因为可以支持多路 IO，才算提高了效率。

进程先是阻塞在 select/poll 上，再是阻塞在读操作的第二个阶段上。

select/poll 的几大缺点：
（1）每次调用 select，都需要把 fd 集合从用户态拷贝到内核态，这个开销在 fd 很多时会很大
（2）同时每次调用 select 都需要在内核遍历传递进来的所有 fd，这个开销在 fd 很多时也很大
（3）select 支持的文件描述符数量太小了，默认是1024

epoll（Linux 2.5.44内核中引入,2.6内核正式引入,可被用于代替 POSIX select 和 poll 系统调用）：
（1）内核与用户空间共享一块内存
（2）通过回调解决遍历问题
（3）fd 没有限制，可以支撑10万连接

一切皆文件
内核态和用户太

reactor

#### 信号驱动IO

信号驱动 IO 与 BIO 和 NIO 最大的区别就在于，在 IO 执行的数据准备阶段，不会阻塞用户进程。

如图所示：当用户进程需要等待数据的时候，会向内核发送一个信号，告诉内核我要什么数据，然后用户进程就继续做别的事情去了，而当内核中
的数据准备好之后，内核立马发给用户进程一个信号，说”数据准备好了，快来查收“，用户进程收到信号之后，立马调用 recvfrom，去查收数据。

线程池——EDA——SEDA

#### 异步式IO

异步 IO 真正实现了 IO 全流程的非阻塞。用户进程发出系统调用后立即返回，内核等待数据准备完成，然后将数据拷贝到用户进程缓冲区，然后发
送信号告诉用户进程 IO 操作执行完毕（与 SIGIO 相比，一个是发送信号告诉用户进程数据准备完毕，一个是IO执行完毕）。

proactor

#### 总结

一个场景，去打印店打印文件。
• 同步阻塞
直接排队，别的啥也干不成，直到轮到你使用打印机了，自己打印文件
• Reactor
拿个号码，回去该干嘛干嘛，等轮到你使用打印机了，店主通知你来用打印机，打印文件
（发号员就是select）
• Proactor
拿个号码，回去该干嘛干嘛，等轮到你使用打印机了，店主直接给你打印好文件，通知你来拿。

可以参考nio里面老师给的书

Java直接用操作系统的线程，裸线程

同步阻塞IO： 去饭馆吃饭 去了之后都在门口等着不能离开 
同步非阻塞IO： 去了饭馆吃饭  就可以去干别的了 时不时回来看看
同步阻塞IO复用：去了饭馆门口，饭馆有个服务员，付服务员 有位了就让你进去
同步非阻塞信号驱动： 去了饭馆吃饭 去了之后领个号 就可以去干别的了 到你了 直接微信给你弹消息让你来
异步IO模型： 你去签个到 就去干别的了 做好了 直接找你去送过来

关于java的网络,有个比喻形象的总结
例子：有一个养鸡的农场，里面养着来自各个农户（Thread）的鸡（Socket），每家农户都在农场中建立了自己的鸡舍（SocketChannel）

1、BIO：Block IO，每个农户盯着自己的鸡舍，一旦有鸡下蛋，就去做捡蛋处理；

2、NIO：No-BlockIO-单Selector，农户们花钱请了一个饲养员（Selector），并告诉饲养员（register）如果哪家的鸡有任何情况  
（下蛋）均要向这家农户报告（selectkeys）;

3、NIO：No-BlockIO-多Selector，当农场中的鸡舍(Selector)逐渐增多时，一个饲养员巡视（轮询）一次所需时间就会不断地加长，这  
样农户知道自己家的鸡有下蛋的情况就会发生较大的延迟。怎么解决呢？没错，多请几个饲养员（多Selector），每个饲养员分配管理鸡舍，这  
样就可以减轻一个饲养员的工作量，同时农户们可以更快的知晓自己家的鸡是否下蛋了；

4、Epoll模式：如果采用Epoll方式，农场问题应该如何改进呢？其实就是饲养员不需要再巡视鸡舍，而是听到哪间鸡舍(Selector)的鸡
打鸣了（活跃连接），就知道哪家农户的鸡下蛋了；

5、AIO：AsynchronousI/O,鸡下蛋后，以前的NIO方式要求饲养员通知农户去取蛋，AIO模式出现以后，事情变得更加简单了，取蛋工作由
饲养员自己负责，然后取完后，直接通知农户来拿即可，而不需要农户自己到鸡舍去取蛋。

## Netty 框架简介：什么是 Netty

网络应用开发框架
1. 异步
2. 事件驱动
3. 基于 NIO

适用于:
- 服务端
- 客户端
- TCP/UDP

高性能的协议服务器:
- 高吞吐
- 低延迟
- 低开销
- 零拷贝
- 可扩容
- 松耦合: 网络和业务逻辑分离
- 使用方便、可维护性好

**Channel**

通道，Java NIO 中的基础概念,代表一个打开的连接,可执行读取/写入 IO 操作。
Netty 对 Channel 的所有 IO 操作都是非阻塞的。

**ChannelFuture**

Java 的 Future 接口，只能查询操作的完成情况, 或者阻塞当前线程等待操作完
成。Netty 封装一个 ChannelFuture 接口。

我们可以将回调方法传给 ChannelFuture，在操作完成时自动执行。

**Event & Handler**

Netty 基于事件驱动，事件和处理器可以关联到入站和出站数据流。

**Encoder & Decoder**

处理网络 IO 时，需要进行序列化和反序列化, 转换 Java 对象与字节流。
对入站数据进行解码, 基类是 ByteToMessageDecoder。
对出站数据进行编码, 基类是 MessageToByteEncoder。

**ChannelPipeline**

数据处理管道就是事件处理器链。
有顺序、同一 Channel 的出站处理器和入站处理器在同一个列表中。

## Netty 使用示例*：如何使用 Netty 实现 NIO




## 第 4 课总结回顾与作业实践

现在各种跟web和网络相关的库、服务器，底层基本上都是Netty
Java socket是对操作系统socket网关访问机制的封装，Netty底层还是socket

