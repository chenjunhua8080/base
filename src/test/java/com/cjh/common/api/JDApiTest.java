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
        cookie = "buildtime=20200525;wxapp_type=1;wxapp_version=6.5.250;wxapp_scene=1001;PPRD_P=EA.17078.1.1-LOGID.1591004594803.598666169-CT.138567.20.7;gender=1;province=Guangdong;city=Guangzhou;country=China;nickName=%E0%B8%85%5E._.%5E%E0%B8%85%E6%81%AD%E5%96%9C%E5%8F%91%E8%B4%A2;avatarUrl=https%3A%2F%2Fwx.qlogo.cn%2Fmmopen%2Fvi_32%2FrhwWwianRPt4GFiaSiabOpWdbtTIygNkzohRpt5IjbrSsfxxSWv7QOicar4xKdhpnL7v8uRa4cQBhpx4oHOEXicJib1Q%2F132;wxNickName=%E0%B8%85%5E._.%5E%E0%B8%85%E6%81%AD%E5%96%9C%E5%8F%91%E8%B4%A2;wxAvatarUrl=https%3A%2F%2Fwx.qlogo.cn%2Fmmopen%2Fvi_32%2FrhwWwianRPt4GFiaSiabOpWdbtTIygNkzohRpt5IjbrSsfxxSWv7QOicar4xKdhpnL7v8uRa4cQBhpx4oHOEXicJib1Q%2F132;shshshfp=7d792e82a51b89d240ae99a46661852a;visitkey=46662873544821088;mcossmd=1d950c6a2c1e81af880ffeeba9cdf429;wid=5077628157;wxapp_openid=oA1P50NL6OdsFy2lrwp0tN1tw7Ow;unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;wq_unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;skey=zwE87AD0323A01A9BD442261E08DFED6D8D2AADA38C1CAF8BD954197814AE737B0E9F9E9DF5112C3703C60D6FCAC7E5025BCAD77B7CF2325FEE2EF314B951AC7FC2A7069C80AEBD8D5F78AC44A0FBD256CA2F70BF67908D55725D0F34C55EA016D;cid=5;maxAge=7200;ou=F7B2B400A6795B2CC7869604D9734426FEC0DA0BFC9DB569443E9AF0E581C15A8352288F6F2E49595FF443470D125C5CE4830208363683D474517C07308C9DE8A06F29D7ADCB6BBCF733C713C264F361;wq_auth_token=AB6EBA8BD8B8F359BAD75639EC22E98C62B5E6046656353AE3EB9F9B47BE01BC;cartLastOpTime=1590634948;__wga=1591004594759.1591004584746.1590634562867.1590288416310.2.3;shshshfpa=e8bae911-0a78-8c57-345c-ca55c194ee4e-1590288416;shshshfpb=zO%2BSiSAfNKoKtzj3igyvlWQ%3D%3D;hf_time=1591004586286;jdpin=jd_55b363483f5b1;open_id=oTGnpnPmaTsb2gk7UNOb4qxMLOdc;pin=jd_55b363483f5b1;pinStatus=0;wq_uin=5077628157;wq_uits=;mainSkuCount=47;cartNum=47;__jda=122270672.0cf5451cbdd5f62bf0fae8118fee7b7c.1591004584391.1591004584391.1591004584391.1;network=wifi;__jdv=122270672%7Cdirect%7Ct_1000578828_xcx_1001_fxrk%7Cxcx%7C-%7C1591004594759;shshshsID=c770c47d8a656ab80025aa7c8c4835d8_2_1591004594693;wq_skey=zwE87AD0323A01A9BD442261E08DFED6D8D2AADA38C1CAF8BD954197814AE737B0E9F9E9DF5112C3703C60D6FCAC7E5025BCAD77B7CF2325FEE2EF314B951AC7FC2A7069C80AEBD8D5F78AC44A0FBD256CA2F70BF67908D55725D0F34C55EA016D";
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