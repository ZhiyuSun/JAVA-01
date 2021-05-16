package com.wsbo.sharding.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource () throws IOException, SQLException {
        // Configure actual data sources
        Map<String, DataSource> dataSourceMap = new HashMap<>();

// Configure the first data source
        HikariDataSource dataSource1 = new HikariDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setJdbcUrl("jdbc:mysql://localhost:3306/trade0");
        dataSource1.setUsername("root");
        dataSource1.setPassword("123456");
        dataSourceMap.put("trade0", dataSource1);

// Configure the second data source
        HikariDataSource dataSource2 = new HikariDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setJdbcUrl("jdbc:mysql://localhost:3306/trade1");
        dataSource2.setUsername("root");
        dataSource2.setPassword("123456");
        dataSourceMap.put("trade1", dataSource2);

// Configure order table rule
        ShardingTableRuleConfiguration orderTableRuleConfig = new ShardingTableRuleConfiguration("trade", "trade${0..1}.trade${0..15}");

// Configure database sharding strategy
        orderTableRuleConfig.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("uid", "dbShardingAlgorithm"));

// Configure table sharding strategy
        orderTableRuleConfig.setTableShardingStrategy(new StandardShardingStrategyConfiguration("trade_id", "tableShardingAlgorithm"));

// Omit t_order_item table rule configuration ...
// ...

// Configure sharding rule
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTables().add(orderTableRuleConfig);

// Configure database sharding algorithm
        Properties dbShardingAlgorithmrProps = new Properties();
        dbShardingAlgorithmrProps.setProperty("algorithm-expression", "trade${uid % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("dbShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", dbShardingAlgorithmrProps));

// Configure table sharding algorithm
        Properties tableShardingAlgorithmrProps = new Properties();
        tableShardingAlgorithmrProps.setProperty("algorithm-expression", "trade${trade_id % 16}");
        shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", tableShardingAlgorithmrProps));

// Create ShardingSphereDataSource
        return ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, Collections.singleton(shardingRuleConfig), new Properties());
    }

    @Bean
    public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/mapper/*.xml"));
        return sessionFactory.getObject();
    }
}
