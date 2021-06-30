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
        openId = "";
        cookie = "buildtime=20210524;wxapp_type=1;wxapp_version=7.5.240;wxapp_scene=1035;cid=5;visitkey=45989107793251615949380409;PPRD_P=CT.138567.7.59-EA.17078.25.2-LOGID.1621911458733.1898294072;gender=1;province=Guangdong;city=Guangzhou;country=China;wxNickName=%E6%81%AD%E2%83%B0%E5%96%9C%E2%83%B0%E5%8F%91%E2%83%B0%E8%B4%A2%E2%83%B0;wxAvatarUrl=https%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FrhwWwianRPt4GFiaSiabOpWdbtTIygNkzohRpt5IjbrSsfxxSWv7QOicar4xKdhpnL7vvQKnG3MtibrTfhAey3tIicJQ%2F132;nickName=%E6%81%AD%E2%83%B0%E5%96%9C%E2%83%B0%E5%8F%91%E2%83%B0%E8%B4%A2%E2%83%B0;avatarUrl=https%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FrhwWwianRPt4GFiaSiabOpWdbtTIygNkzohRpt5IjbrSsfxxSWv7QOicar4xKdhpnL7vvQKnG3MtibrTfhAey3tIicJQ%2F132;shshshfp=8ef1052498d8c579b6e16b0a06aeb4fc;jdpin=jd_55b363483f5b1;mcossmd=1d950c6a2c1e81af880ffeeba9cdf429;open_id=oTGnpnPmaTsb2gk7UNOb4qxMLOdc;pin=jd_55b363483f5b1;pinStatus=0;unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;wid=5077628157;wq_uin=5077628157;wq_unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;wxapp_openid=oA1P50NL6OdsFy2lrwp0tN1tw7Ow;skey=zwAC65A9293A415B3B993825EC103D18A141A519003D6ED35A1BF83B57E1D0D1DE7CD51680EE26F7E88E90861C2FBC2A02BCAD77B7CF2325FEE2EF314B951AC7FC2A7069C80AEBD8D5F78AC44A0FBD256CA2F70BF67908D55725D0F34C55EA016D;ou=889A6CCC66C4456636860B7AE6DA48F7FEC0DA0BFC9DB569443E9AF0E581C15A8352288F6F2E49595FF443470D125C5CE4830208363683D474517C07308C9DE8A06F29D7ADCB6BBCF733C713C264F361;wq_uits=;wq_auth_token=A850BD5AAB5E293B3CB0E7C700E786DE016DBE266FF514247F8A4E30680DA94D;cartLastOpTime=1621395297;cartNum=100;wq_addr=2865567071%7C19_1601_3634_63211%7C%E5%B9%BF%E4%B8%9C_%E5%B9%BF%E5%B7%9E%E5%B8%82_%E6%B5%B7%E7%8F%A0%E5%8C%BA_%E6%B1%9F%E6%B5%B7%E8%A1%97%E9%81%93%7C%E5%B9%BF%E4%B8%9C%E5%B9%BF%E5%B7%9E%E5%B8%82%E6%B5%B7%E7%8F%A0%E5%8C%BA%E6%B1%9F%E6%B5%B7%E8%A1%97%E9%81%93%E4%B8%9C%E6%B5%B7%E5%A4%A7%E8%A1%97%E5%8D%9722%E5%8F%B7%EF%BC%88%E8%8F%9C%E9%B8%9F%E9%A9%BF%E7%AB%99%EF%BC%89%7C113.317%2C23.0905;__wga=1621911458675.1621911323123.1621409788572.1621394767950.8.4;shshshfpa=1d4d0784-d95d-1134-74cb-02398a1aac05-1621394768;shshshfpb=d2Hn0y5kyWuqzUhhNIAgLsg%3D%3D;hf_time=1621911323984;__jda=122270672.8b8395c81aa2983fee28bbd7171bac3b.1621911322605.1621911322605.1621911322605.1;network=wifi;__jdv=122270672%7Cweixin%7Ct_1000072662_xcx_1035_cdl%7Cxcx%7C-%7C1621911458675;shshshsID=b15e482e9a880ddf0adc294ed26d5a6f_6_1621911458292;wq_skey=zwAC65A9293A415B3B993825EC103D18A141A519003D6ED35A1BF83B57E1D0D1DE7CD51680EE26F7E88E90861C2FBC2A02BCAD77B7CF2325FEE2EF314B951AC7FC2A7069C80AEBD8D5F78AC44A0FBD256CA2F70BF67908D55725D0F34C55EA016D";
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
        jdApi.getFirstFeedReward(openId, cookie);
    }
}