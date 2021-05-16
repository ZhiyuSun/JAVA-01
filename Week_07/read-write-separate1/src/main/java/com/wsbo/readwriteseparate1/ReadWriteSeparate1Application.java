package com.wsbo.readwriteseparate1;

import com.wsbo.readwriteseparate1.service.ReadService;
import com.wsbo.readwriteseparate1.util.ApplicationContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wsbo.readwriteseparate1.dal.mapper")
public class ReadWriteSeparate1Application {

	public static void main(String[] args) {
		SpringApplication.run(ReadWriteSeparate1Application.class, args);
		ReadService readService = ApplicationContextUtil.getBean(ReadService.class);
		System.out.println(readService.readFromWriteDataSource(1).getTradeId());
		System.out.println(readService.readFromReadDataSource(1).getTradeId());
		System.out.println(readService.readFromReadDataSource(1).getTradeId());
		System.out.println(readService.readFromReadDataSource(1).getTradeId());
		System.out.println(readService.readFromReadDataSource(1).getTradeId());
	}

}
