package com.csmaxwell.srent.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.csmaxwell.srent.db", "com.csmaxwell.srent.core", "com.csmaxwell.srent.admin"})
@MapperScan("org.csmaxwell.srent.db.dao")
public class SrentAdminApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrentAdminApiApplication.class, args);
    }

}
