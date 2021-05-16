# SQL与性能优化

## 再聊聊性能优化

- 吞吐与延迟： 有些结论是反直觉的，指导我们关注什么
- 没有量化就没有改进：监控与度量指标，指导我们怎么去入手
- 80/20原则：先优化性能瓶颈问题，指导我们如何去优化
- 过早的优化是万恶之源：指导我们要选择优化的时机
- 脱离场景谈性能都是耍流氓：指导我们对性能要求要符合实际

脱离场景谈多少qps没意义

DB/SQL 优化是业务系统性能优化的核心

- 业务系统的分类：计算密集型、数据密集型
- 业务处理本身无状态，数据状态最终要保存到数据库
- 一般来说，DB/SQL 操作的消耗在一次处理中占比最大
- 业务系统发展的不同阶段和时期，性能瓶颈要点不同，类似木桶装水

例如传统软件改成SaaS 软件。（性能要求不一样）

## 关系数据库MySQL

E-R图

第一范式（1NF）：关系R 属于第一范式，当且仅当R中的每一个属性A的值域只包含原子项
第二范式（2NF）：在满足1NF 的基础上，消除非主属性对码的部分函数依赖
第三范式（3NF）：在满足2NF 的基础上，消除非主属性对码的传递函数依赖
BC 范式（BCNF）：在满足3NF 的基础上，消除主属性对码的部分和传递函数依赖
第四范式（4NF）：消除非平凡的多值依赖
第五范式（5NF）：消除一些不合适的连接依赖

常见关系数据库

开源：MySQL、PostgreSQL
商业：Oracle，DB2，SQL Server
内存数据库：Redis？，VoltDB
图数据库：Neo4j，Nebula
时序数据库：InfluxDB、openTSDB
其他关系数据库：Access、Sqlite、H2、Derby、Sybase、Infomix 等
NoSQL 数据库：MongoDB、Hbase、Cassandra、CouchDB
NewSQL/ 分布式数据库：TiDB、CockroachDB、NuoDB、OpenGauss、OB、TDSQL

## 深入数据库原理

MySQL简化执行流程

查询缓存——解析器——预处理器——查询优化器（选择哪个索引）——执行计划——查询执行引擎——存储引擎——数据

MySQL对SQL执行顺序

from——on——join——where——group by——having+聚合函数——select——order by——limit

MySQL索引原理

InnoDB使用B+树实现聚集索引

一般单表数据不能超过2000万

## MySQL配置优化

连接请求的变量
- max_connections
- back_log
- wait_timeout和interative_timeout

缓冲区变量
- key_buffer_size
- query_cache_size（查询缓存简称QC)
- max_connect_errors：
- sort_buffer_size：
- max_allowed_packet=32M
- join_buffer_size=2M
- thread_cache_size=300

配置Innodb 的几个变量
- innodb_buffer_pool_size
- innodb_flush_log_at_trx_commit
- innodb_thread_concurrency=0
- innodb_log_buffer_size
- innodb_log_file_size=50M
- innodb_log_files_in_group=3
- read_buffer_size=1M
- read_rnd_buffer_size=16M
- bulk_insert_buffer_size=64M
- binary log

## MySQL设计优化

- 如何恰当选择引擎？
- 库表如何命名？
- 如何合理拆分宽表？
- 如何选择恰当数据类型：明确、尽量小
- char、varchar 的选择
- （text/blob/clob）的使用问题？
- 文件、图片是否要存入到数据库？
- 时间日期的存储问题？
- 数值的精度问题？
- 是否使用外键、触发器？
- 唯一约束和索引的关系？
- 是否可以冗余字段？
- 是否使用游标、变量、视图、自定义函数、存储过程？ 都不建议使用
- 自增主键的使用问题？
- 能够在线修改表结构（DDL 操作）？
- 逻辑删除还是物理删除？
- 要不要加create_time,update_time 时间戳？
- 数据库碎片问题？
- 如何快速导入导出、备份数据？
