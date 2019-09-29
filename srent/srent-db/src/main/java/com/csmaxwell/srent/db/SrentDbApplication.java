package com.csmaxwell.srent.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.csmaxwell.srent.db"})
@MapperScan("com.csmaxwell.srent.db.dao")
public class SrentDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrentDbApplication.class, args);
    }

}
