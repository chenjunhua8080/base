package com.cjh.common.feign;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class CloudFeignClientTest {

    @Autowired
    private CloudFeignClient cloudFeignClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    void pushErrorMsg() {
        String openId = "";
        cloudFeignClient.pushErrorMsg(openId, "测试推送");
    }
}