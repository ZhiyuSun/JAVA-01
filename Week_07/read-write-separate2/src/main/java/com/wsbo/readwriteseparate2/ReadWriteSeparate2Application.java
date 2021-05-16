package com.wsbo.readwriteseparate2;

import com.wsbo.readwriteseparate2.service.ReadService;
import com.wsbo.readwriteseparate2.util.ApplicationContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wsbo.readwriteseparate2.dal.mapper")
public class ReadWriteSeparate2Application {

	public static void main(String[] args) {
		SpringApplication.run(ReadWriteSeparate2Application.class, args);
		ReadService readService = ApplicationContextUtil.getBean(ReadService.class);
		System.out.println(readService.readFromReadDataSource(1));
		System.out.println(readService.readFromReadDataSource(1));
		System.out.println(readService.readFromReadDataSource(1));
		System.out.println(readService.readFromReadDataSource(1));
		readService.insert();
		readService.insert();
	}



}
