# 学习笔记

## Week07 作业题目（周三）：

1.（选做）用今天课上学习的知识，分析自己系统的 SQL 和表结构
2.（必做）按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率

订单表

``` SQL
create table if not exists `trade`(
    `id` int auto_increment comment '主键',
    `trade_id` varchar(30) not null comment '订单唯一id',
    `uid` varchar(20) not null comment '用户唯一id',
    `status` varchar(20) not null default 'init' comment '订单状态',
    `goods_info` text comment '购买商品信息',
    `pay_time` datetime default null comment '支付时间',
    `total_amount` decimal(10,2) default null comment '原价',
    `received_amount` decimal(10,2) default null comment '实收金额',
    `fail_msg` text comment '订单失败原因',
    `create_time` datetime default current_timestamp not null comment '创建时间',
    `modify_time` datetime default current_timestamp on update current_timestamp not null comment '修改时间',
    primary key (`id`),
	key `idx_uid` (`trade_id`),
	key `idx_trade_id` (`trade_id`),
	key `idx_create_time` (`create_time`)
) default charset=utf8mb4 comment '订单表';
```

todo

3.（选做）按自己设计的表结构，插入 1000 万订单模拟数据，测试不同方式的插入效
4.（选做）使用不同的索引或组合，测试不同方式查询效率
5.（选做）调整测试数据，使得数据尽量均匀，模拟 1 年时间内的交易，计算一年的销售报表：销售总额，订单数，客单价，每月销售量，前十的商品等等（可以自己设计更多指标）
6.（选做）尝试自己做一个 ID 生成器（可以模拟 Seq 或 Snowflake）
7.（选做）尝试实现或改造一个非精确分页的程序

## Week07 作业题目（周日）：
1.（选做）配置一遍异步复制，半同步复制、组复制
2.（必做）读写分离 - 动态切换数据源版本 1.0



3.（必做）读写分离 - 数据库框架版本 2.0



4.（选做）读写分离 - 数据库中间件版本 3.0
5.（选做）配置 MHA，模拟 master 宕机
6.（选做）配置 MGR，模拟 master 宕机
7.（选做）配置 Orchestrator，模拟 master 宕机，演练 UI 调整拓扑结构