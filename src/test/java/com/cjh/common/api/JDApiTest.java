package com.cjh.common.api;

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
class JDApiTest {

    @Autowired
    private JDApi jdApi;

    @BeforeEach
    void setUp() {
    }

    @Test
    void collectScore() {
        jdApi.collectScore("user.getOpenId()", "cookie");
    }

    @Test
    void getHomeData() {
        jdApi.getHomeData("","", true);
    }
}