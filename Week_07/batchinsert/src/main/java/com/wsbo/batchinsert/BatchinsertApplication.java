package com.wsbo.batchinsert;

import com.wsbo.batchinsert.inserter.SimpleInserter;
import com.wsbo.batchinsert.inserter.ThreadPoolSimpleInserter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wsbo.batchinsert.dal.mapper")
public class BatchinsertApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchinsertApplication.class, args);
//		SimpleInserter.insert();
		ThreadPoolSimpleInserter.insert();
	}

}
