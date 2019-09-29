package com.csmaxwell.srent.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.csmaxwell.srent.db", "com.csmaxwell.srent.core"})
@MapperScan("org.linlinjava.litemall.db.dao")
public class SrentCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrentCoreApplication.class, args);
    }

}
