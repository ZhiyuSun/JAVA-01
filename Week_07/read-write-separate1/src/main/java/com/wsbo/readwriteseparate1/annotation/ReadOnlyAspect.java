package com.wsbo.readwriteseparate1.annotation;

import com.wsbo.readwriteseparate1.config.DbContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ReadOnlyAspect {
    @Around("@annotation(ReadOnly)")
    public Object setRead(ProceedingJoinPoint joinPoint) throws Throwable{
        try{
            DbContextHolder.setDbType(DbContextHolder.READ);
            return joinPoint.proceed();
        }finally {
            //清楚DbType一方面为了避免内存泄漏，更重要的是避免对后续在本线程上执行的操作产生影响
            DbContextHolder.clearDbType();
            log.info("清除threadLocal");
        }
    }
}
