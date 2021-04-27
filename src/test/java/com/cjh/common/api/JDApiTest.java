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

    private String openId;
    private String cookie;

    @BeforeEach
    void setUp() {
        openId = "o1IsFt8QJMFhVZDE0W3ovHx15hes";
        cookie = "buildtime=20210319;wxapp_type=14;wxapp_version=6.9.130;wxapp_scene=1001;cid=5;visitkey=87261866270801617682482272;gender=1;province=Guangdong;city=Guangzhou;country=China;wxNickName=%E6%81%AD%E2%83%B0%E5%96%9C%E2%83%B0%E5%8F%91%E2%83%B0%E8%B4%A2%E2%83%B0;wxAvatarUrl=https%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FyctGCWkz1jK7YAlTjibHs53Bn2ia4wfSostn9eDm4Em9FBZGX4vcXH4icVibv9dXlsduqBrCN9J4j4YGV1KcKhttJg%2F132;nickName=%E6%81%AD%E2%83%B0%E5%96%9C%E2%83%B0%E5%8F%91%E2%83%B0%E8%B4%A2%E2%83%B0;avatarUrl=https%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FyctGCWkz1jK7YAlTjibHs53Bn2ia4wfSostn9eDm4Em9FBZGX4vcXH4icVibv9dXlsduqBrCN9J4j4YGV1KcKhttJg%2F132;jdpin=jd_55b363483f5b1;mcossmd=1d950c6a2c1e81af880ffeeba9cdf429;open_id=oTGnpnPmaTsb2gk7UNOb4qxMLOdc;pin=jd_55b363483f5b1;pinStatus=0;unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;wid=5077628157;wq_uin=5077628157;wq_unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;wxapp_openid=oZd_i5MIT-vkawZ0NDMjQPWjVXwY;skey=zwD487134353A57E4C7AEAEE33A045CDCF9930C89593BDEAB0F07B5ED693855005A0FECAF431826389F0A71BB41306EA07046C922C85A8D15A42D8E472CD90336CDBF060911AA843C6447B980757DEA9C3B7EB5CD2CF0519655697DD988D64DCB0;wq_skey=zwD487134353A57E4C7AEAEE33A045CDCF9930C89593BDEAB0F07B5ED693855005A0FECAF431826389F0A71BB41306EA07046C922C85A8D15A42D8E472CD90336CDBF060911AA843C6447B980757DEA9C3B7EB5CD2CF0519655697DD988D64DCB0;ou=98EB33ACA3478F3B4F79521144C28EFF717F4AAD85CDC9CC0DEF70A868DB6FF9E1BE96F55940C554D9468E4840FF1720E4830208363683D474517C07308C9DE8A06F29D7ADCB6BBCF733C713C264F361;wq_auth_token=E5209BBA11EAF4563907B3C27271CECBF1AC5C909F913EC17BE1ACE0A2C8276D;__jda=122270672.d21f7e15d98e29027fd1e4e34c9177f9.1617682538233.1617682538233.1617682538233.1;__wga=1617691431523.1617691431513.1617682538366.1617682538366.2.2;__jdv=122270672%7Cinteract_banner%7C-%7Cnone%7C-%7C1617682671316;PPRD_P=EA.17078.1.1-LOGID.1617691431709.368605720;shshshfpa=2fd508bf-6bb5-6504-6812-37ebf314f4d0-1617682538;shshshfp=8ef1052498d8c579b6e16b0a06aeb4fc;shshshfpb=ir9G0PpzsI2p5Zw2tDBYf%2Fg%3D%3D;hf_time=1617682538902;cartLastOpTime=1";
    }

    @Test
    void collectScore() {
        jdApi.collectScore(openId, cookie);
    }

    @Test
    void getHomeData() {
        jdApi.getHomeData("", "", true);
    }

    @Test
    void getSignReward() {
        jdApi.getSignReward(openId, cookie);
    }

    @Test
    void getThreeMeal() {
        jdApi.getThreeMeal(openId, cookie);
    }

    @Test
    void feedPets() {
        jdApi.feedPets(openId, cookie);
    }

    @Test
    void getSingleShop() {
        jdApi.getSingleShop(openId, cookie);
    }
}