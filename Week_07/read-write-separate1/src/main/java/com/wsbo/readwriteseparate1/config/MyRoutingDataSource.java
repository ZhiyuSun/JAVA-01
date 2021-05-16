package com.wsbo.readwriteseparate1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Random;

@Slf4j
public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String dbType = DbContextHolder.getDbType();
        if (DbContextHolder.WRITE.equals(dbType)) {
            log.info("使用了写库");
            return dbType;
        }
        //使用随机数决定使用哪个读库
        Random random = new Random();
        int index = random.nextInt(2) + 1;
        log.info("使用了读库{}", index);
        return DbContextHolder.READ + index;
    }
}
