# 性能与SQL优化

## 1.MySQL 事务与锁*

事务可靠性模型ACID:
- Atomicity: 原子性, 一次事务中的操作要么全部成功, 要么全部失败。
- Consistency: 一致性, 跨表、跨行、跨事务, 数据库始终保持一致状态。
- Isolation: 隔离性, 可见性, 保护事务不会互相干扰, 包含4种隔离级别。
- Durability:, 持久性, 事务提交成功后,不会丢数据。如电源故障, 系统崩溃。

InnoDB:
双写缓冲区、故障恢复、操作系统、fsync() 、磁盘存储、缓存、UPS、网络、备份策略……

表级锁

行级锁

死锁

四种事务隔离级别
- 读未提交: READ UNCOMMITTED
- 读已提交: READ COMMITTED
- 可重复读: REPEATABLE READ
- 可串行化: SERIALIZABLE

事务隔离是数据库的基础特征。
MySQL:
- 可以设置全局的默认隔离级别
- 可以单独设置会话的隔离级别
- InnoDB 实现与标准之间的差异


读未提交: READ UNCOMMITTED
- 很少使用
- 不能保证一致性
- 脏读(dirty read) : 使用到从未被确认的数据(例如: 早期版本、回滚)

锁:
- 以非锁定方式执行
- 可能的问题: 脏读、幻读、不可重复读

读已提交: READ COMMITTED
- 每次查询都会设置和读取自己的新快照。
- 仅支持基于行的bin-log
- UPDATE 优化: 半一致读(semi-consistent read)
- 不可重复读: 不加锁的情况下, 其他事务UPDATE 或DELETE 会对查询结果有影响
- 幻读(Phantom): 加锁后, 不锁定间隙, 其他事务可以INSERT。

锁:
- 锁定索引记录, 而不锁定记录之间的间隙
- 可能的问题: 幻读、不可重复读

可重复读: REPEATABLE READ
- InnoDB 的默认隔离级别
- 使用事务第一次读取时创建的快照
- 多版本技术

锁:
- 使用唯一索引的唯一查询条件时, 只锁定查找到的索引记录, 不锁定间隙。
- 其他查询条件, 会锁定扫描到的索引范围, 通过间隙锁或临键锁来阻止其他会话在这个范围中插入值。
- 可能的问题: InnoDB 不能保证没有幻读, 需要加锁

串行化: SERIALIZABLE
最严格的级别，事务串行执行，资源消耗最大；

问题回顾:
- 脏读(dirty read) : 使用到从未被确认的数据(例如: 早期版本、回滚)
- 不可重复读: 不加锁的情况下, 其他事务update 或delete 会对结果集有影响
- 幻读(Phantom): 加锁之后, 相同的查询语句, 在不同的时间点执行时, 产生不同的结果集
怎么解决?
提高隔离级别、使用间隙锁或临键锁

undo log: 撤消日志
- 保证事务的原子性
- 用处: 事务回滚, 一致性读、崩溃恢复。
- 记录事务回滚时所需的撤消操作
- 一条INSERT 语句，对应一条DELETE 的undo log
- 每个UPDATE 语句，对应一条相反UPDATE 的undo log
保存位置:
- system tablespace (MySQL 5.7默认)
- undo tablespaces (MySQL 8.0默认)
回滚段(rollback segment)

MVCC: 多版本并发控制

演示

《高性能MySQL》
《高可用MySQL》

## 2.DB 与SQL 优化*

如何发现需要优化的SQL?
你了解的SQL 优化方法有哪些?
SQL 优化有哪些好处?

utf8mb4是真正的utf8

B-树每个块都会有索引和数据
B+树只有叶子有数据，叶子节点之间有链表，更适合做范围查询的操作

聚簇索引不需要回表，其他索引需要回表

字段选择性，最左原则
某个字段值的重复程度，称为该字段的选择性
F = DISTINCT(col) / count(*)

写入优化
- 大批量写入的优化
- PreparedStatement 减少SQL 解析
- Multiple Values/Add Batch 减少交互
- Load Data，直接导入
- 索引和约束问题

数据更新
- 数据的范围更新
- 注意GAP Lock 的问题
- 导致锁范围扩大

模糊查询
- Like 的问题
- 前缀匹配
- 否则不走索引
- 全文检索，
- solr/ES

连接查询
- 连接查询优化
- 驱动表的选择问题
- 避免笛卡尔积

索引失效
- 索引失效的情况汇总
- NULL，not，not in，函数等
- 减少使用or，可以用union（注意union all 的区别），以及前面提到的like
- 大数据量下，放弃所有条件组合都走索引的幻想，出门左拐“全文检索”
- 必要时可以使用force index 来强制查询走某个索引

查询SQL到底怎么设计
- 查询数据量和查询次数的平衡
- 避免不必须的大量重复数据传输
- 避免使用临时文件排序或临时表
- 分析类需求，可以用汇总表

## 3.常见场景分析*

怎么实现主键ID
- 自增
- sequence
- 模拟seq
- UUID
- 时间戳/随机数
- snowflake(常用)

高效分页
- 分页：count/pageSize/pageNum, 带条件的查询语句
- 常见实现-分页插件：使用查询SQL，嵌套一个count，性能的坑？
- 改进一下1，重写count
- 大数量级分页的问题，limit 100000,20
- 改进一下2，反序
- 继续改进3，技术向：带id，
- 继续改进4，需求向：非精确分页
- 所有条件组合？ 索引？

乐观锁和悲观锁
select * from xxx for update
update xxx
commit；

并发效率比较低

select * from xxx
update xxx where value=oldValue
为什么叫乐观锁

## 4.总结回顾与作业实践