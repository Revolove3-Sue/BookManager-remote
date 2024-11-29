package com.rabbiter.bms;

import com.rabbiter.bms.utils.PathUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(value = "com.rabbiter.bms.mapper")
public class BookManagerApplication {

    public static void main(String[] args) {
        log.info("Project Path: {}", PathUtils.getClassLoadRootPath());
        SpringApplication.run(BookManagerApplication.class, args);
    }

}
