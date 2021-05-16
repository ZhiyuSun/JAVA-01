package com.wsbo.sharding;

import com.wsbo.sharding.service.TradeService;
import com.wsbo.sharding.util.ApplicationContextUtil;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
@MapperScan("com.wsbo.sharding.dal.mapper")
public class ShardingApplication {

	public static void main(String[] args)  {
		SpringApplication.run(ShardingApplication.class, args);
		TradeService tradeService = ApplicationContextUtil.getBean(TradeService.class);
		for (int i = 0; i < 10000; i++) {
			tradeService.insert((long) i);
		}
	}

}
