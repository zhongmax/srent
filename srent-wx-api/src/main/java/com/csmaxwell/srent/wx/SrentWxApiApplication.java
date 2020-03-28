package com.csmaxwell.srent.wx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.csmaxwell.srent.db", "com" +
        ".csmaxwell.srent.core", "com.csmaxwell.srent.wx"})
@MapperScan("com.csmaxwell.srent.db.dao")
public class SrentWxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrentWxApiApplication.class, args);
    }

}
