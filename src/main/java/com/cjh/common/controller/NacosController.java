package com.cjh.common.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.cjh.common.feign.CloudFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/nacos")
public class NacosController {

    @Autowired
    private CloudFeignClient feignClient;

//    @HystrixCommand(fallbackMethod = "helloError")
//    @RateLimiter(name = "default")
    @CircuitBreaker(name = "test1", fallbackMethod = "circuitBreaker")
    @GetMapping("/test/1")
    public R test1() {
        return R.ok(feignClient.pushErrorMsg("xxx", "hello test"));
    }

    //如果name相同会一起熔断
    @CircuitBreaker(name = "test2", fallbackMethod = "circuitBreaker")
    @GetMapping("/test/2")
    public R test2() {
        return R.ok(feignClient.pushErrorMsg("xxx", "hello test"));
    }

    //熔断回调方法，名称一定要和fallbackMethod中的一样
    public R circuitBreaker() {
        return R.ok("终于熔断了...");
    }

    public R circuitBreaker(Throwable throwable) {
        if (throwable != null) {
            log.error(throwable.getMessage());
        }
        return R.ok("fallback");
    }

}
