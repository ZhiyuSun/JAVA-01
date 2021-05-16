package com.wsbo.readwriteseparate1.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "mysql.datasource.write")
    public DataSource writeDataSource() {
        return new HikariDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "mysql.datasource.read1")
    public DataSource readDataSource1() {
        return new HikariDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "mysql.datasource.read2")
    public DataSource readDataSource2() {
        return new HikariDataSource();
    }

    @Bean
    public AbstractRoutingDataSource routingDataSource(DataSource writeDataSource, @Qualifier("readDataSource1") DataSource readDataSource1, @Qualifier("readDataSource2") DataSource readDataSource2) {
        MyRoutingDataSource proxy = new MyRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(8);
        dataSourceMap.put(DbContextHolder.WRITE, writeDataSource);
        dataSourceMap.put(DbContextHolder.READ+"1", readDataSource1);
        dataSourceMap.put(DbContextHolder.READ+"2", readDataSource2);
        proxy.setDefaultTargetDataSource(writeDataSource);
        proxy.setTargetDataSources(dataSourceMap);
        return proxy;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(AbstractRoutingDataSource routingDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(routingDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(AbstractRoutingDataSource routingDataSource) {
        return new DataSourceTransactionManager(routingDataSource);
    }
}
