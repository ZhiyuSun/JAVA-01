package com.wsbo.readwriteseparate1.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DbContextHolder {
    public static final String WRITE = "write";
    public static final String READ = "read";

    private static final ThreadLocal<String> contextHolder= new ThreadLocal<>();

    public static void setDbType(String dbType) {
        if (dbType == null) {
            log.error("dbType为空");
            throw new NullPointerException();
        }
        log.info("设置dbType为：{}",dbType);
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return contextHolder.get() == null ? WRITE : contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
