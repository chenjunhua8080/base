package com.cjh.common.conifg;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    /**
     * 不加这个分页无效
     * https://blog.csdn.net/Mr_going/article/details/111646555
     */
    @Bean
    public PaginationInterceptor mybatisPlusInterceptor() {
        return new PaginationInterceptor();
    }
}
