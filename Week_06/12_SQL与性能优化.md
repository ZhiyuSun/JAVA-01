# SQL与性能优化


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
