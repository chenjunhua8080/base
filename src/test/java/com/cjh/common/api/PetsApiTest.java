package com.cjh.common.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class PetsApiTest {

    @Autowired
    private PetsApi petsApi;

    private String openId;
    private String cookie;

    @BeforeEach
    void setUp() {
        openId = "";
        cookie = "buildtime=20211206;wxapp_type=14;wxapp_version=6.9.130;wxapp_scene=1036;cid=5;visitkey=87261866270801617682482272;gender=0;province=;city=;country=;wxNickName=%E5%BE%AE%E4%BF%A1%E7%94%A8%E6%88%B7;wxAvatarUrl=https%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FPOgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg%2F132;nickName=%E5%BE%AE%E4%BF%A1%E7%94%A8%E6%88%B7;avatarUrl=https%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FPOgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg%2F132;jdpin=jd_55b363483f5b1;mcossmd=1d950c6a2c1e81af880ffeeba9cdf429;open_id=oTGnpnPmaTsb2gk7UNOb4qxMLOdc;pin=jd_55b363483f5b1;pinStatus=0;unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;wid=5077628157;wq_uin=5077628157;wq_unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;wxapp_openid=oZd_i5MIT-vkawZ0NDMjQPWjVXwY;skey=zw09824B44745EBA6912C9EF13C3F1CCB5898D96544CB3B5A67411F5E2D3F5AB81F071776D8F6FE5FFF9B05FF96157C0A7A45B93757CCF1CAB610352A30C9A990E31CAC75DDC2F4BCB43DED6CAEC57369E2D1707952B517F3BE8ACEE20C0A68EBC;ou=9B7DA9579B23E76EB7DC3CBD97B2A4DA717F4AAD85CDC9CC0DEF70A868DB6FF9E1BE96F55940C554D9468E4840FF1720E4830208363683D474517C07308C9DE8A06F29D7ADCB6BBCF733C713C264F361;wq_auth_token=3B7F46D61B939C6A96B97DEA63EE335B49C559820D0DF078EE1D4A9A3CC36BEF;PPRD_P=EA.17078.26.1-LOGID.1638930538271.1695162450;shshshfp=8ef1052498d8c579b6e16b0a06aeb4fc;cartLastOpTime=1638501374;__wga=1638930538230.1638930538230.1638758597290.1638499731327.1.3;shshshfpa=ea5669ee-bd3f-efb2-5f8f-a1c67d5fbbe1-1638499731;shshshfpb=fY%2BA%2FcqzjWr%2Bgo3%2FlnuZTGA%3D%3D;hf_time=1638930538498;wq_skey=zw09824B44745EBA6912C9EF13C3F1CCB5898D96544CB3B5A67411F5E2D3F5AB81F071776D8F6FE5FFF9B05FF96157C0A7A45B93757CCF1CAB610352A30C9A990E31CAC75DDC2F4BCB43DED6CAEC57369E2D1707952B517F3BE8ACEE20C0A68EBC;network=wifi;__jda=122270672.4bec2e6937cc24510bb07f58e738c278.1638930538168.1638930538168.1638930538168.1;__jdv=122270672%7Cweixin%7Ct_335139774_xcx_1036_appfxxx%7Cxcx%7C-%7C1638930538231;shshshsID=89668c234dc578482ab794c345a0a41b_1_1638930538289";
    }

    @Test
    void getSignReward() {
        petsApi.getSignReward(openId, cookie);
    }

    @Test
    void getThreeMeal() {
        petsApi.getThreeMeal(openId, cookie);
    }

    @Test
    void feedPets() {
        petsApi.feedPets(openId, cookie);
    }

    @Test
    void getFirstFeedReward() {
        petsApi.getFirstFeedReward(openId, cookie);
    }

    @Test
    void browseExec() throws InterruptedException {
        petsApi.browseExec(openId, cookie, 30);
    }
}