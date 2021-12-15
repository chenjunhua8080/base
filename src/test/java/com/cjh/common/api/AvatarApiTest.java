package com.cjh.common.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AvatarApiTest {

    @Autowired
    private AvatarApi avatarApi;

    @Test
    void getAvatarByNew() {
        log.info(String.valueOf(avatarApi.getAvatarByNew(2)));
    }

    @Test
    void getAvatar() {
        log.info(avatarApi.getAvatar() + "aaaa");
    }
}