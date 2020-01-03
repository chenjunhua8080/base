package com.cjh.common.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
class AvatarApiTest {

    @Autowired
    private AvatarApi avatarApi;

    @Test
    void getAvatarByNew() {
        log.info(String.valueOf(avatarApi.getAvatarByNew()));
    }

    @Test
    void getAvatar() {
        log.info(avatarApi.getAvatar() + "aaaa");
    }
}