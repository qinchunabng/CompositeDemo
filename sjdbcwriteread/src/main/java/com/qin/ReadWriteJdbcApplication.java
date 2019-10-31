package com.qin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qin.module.*.mapper")
public class ReadWriteJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadWriteJdbcApplication.class,args);
    }
}
