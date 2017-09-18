package com.pr_manager.http;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pr_manager.http.mvc.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
