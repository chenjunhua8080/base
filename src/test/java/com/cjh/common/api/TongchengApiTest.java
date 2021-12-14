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
class TongchengApiTest {

    @Autowired
    private TongchengApi api;

    private String openId;
    private String cookie;

    @BeforeEach
    void setUp() {
        openId = "";
        cookie = "__tctma=33778749.1639463063858344.1639463063512.1639463063512.1639463063512.1; __tctmu=33778749.0.0; __tctmz=33778749.1639463063512.1.1.utmccn=(referral)|utmcsr=qq.com|utmcct=connect/oauth2/authorize|utmcmd=referral; longKey=1639463063858344; __tctrack=0; __tctmc=33778749.213722114; __tctmd=33778749.737325; __tctmb=33778749.2431630404607266.1639463063512.1639463109125.2";
    }

    @Test
    void sign() {
        api.sign(openId, cookie);
    }

    @Test
    void waterSign() {
        api.waterSign(openId, cookie);
    }

    @Test
    void waterTree() {
        api.waterTree(openId, cookie);
    }

    @Test
    void getAutoRestoreWater() {
        api.getAutoRestoreWater(openId, cookie);
    }

    @Test
    void getTaskAward() {
        api.getTaskAward(openId, cookie, "T107");
    }
}