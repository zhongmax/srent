package com.csmaxwell.srent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by maxwell on 2019/12/5
 */
@SpringBootApplication(scanBasePackages = {"com.csmaxwell.srent"})
@MapperScan("com.csmaxwell.srent.db.dao")
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
