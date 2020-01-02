package com.cjh.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//加了第一次无法注册进去
//@EnableDiscoveryClient
public class DoubanApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoubanApplication.class, args);
    }

}
