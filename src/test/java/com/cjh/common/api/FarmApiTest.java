package com.cjh.common.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class FarmApiTest {

    @Autowired
    private FarmApi api;

    private String openId;
    private String cookie;

    @BeforeEach
    void setUp() {
        openId = "";
        cookie = "buildtime=20211202;wxapp_type=1;wxapp_version=7.12.20;wxapp_scene=1036;cid=5;visitkey=45989107793251615949380409;PPRD_P=CT.137854.1.26-EA.17078.26.1-LOGID.1638758699516.1794860330;gender=1;province=Guangdong;city=Guangzhou;country=China;wxNickName=%E6%81%AD%E2%83%B0%E5%96%9C%E2%83%B0%E5%8F%91%E2%83%B0%E8%B4%A2%E2%83%B0;wxAvatarUrl=https%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FrhwWwianRPt4GFiaSiabOpWdbtTIygNkzohRpt5IjbrSsfxxSWv7QOicar4xKdhpnL7vvQKnG3MtibrTfhAey3tIicJQ%2F132;nickName=%E6%81%AD%E2%83%B0%E5%96%9C%E2%83%B0%E5%8F%91%E2%83%B0%E8%B4%A2%E2%83%B0;avatarUrl=https%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FrhwWwianRPt4GFiaSiabOpWdbtTIygNkzohRpt5IjbrSsfxxSWv7QOicar4xKdhpnL7vvQKnG3MtibrTfhAey3tIicJQ%2F132;shshshfp=8ef1052498d8c579b6e16b0a06aeb4fc;jdpin=jd_55b363483f5b1;mcossmd=1d950c6a2c1e81af880ffeeba9cdf429;open_id=oTGnpnPmaTsb2gk7UNOb4qxMLOdc;pin=jd_55b363483f5b1;pinStatus=0;unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;wid=5077628157;wq_uin=5077628157;wq_unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;wxapp_openid=oA1P50NL6OdsFy2lrwp0tN1tw7Ow;skey=zw09824B44745EBA6912C9EF13C3F1CCB5898D96544CB3B5A67411F5E2D3F5AB81F071776D8F6FE5FFF9B05FF96157C0A7A45B93757CCF1CAB610352A30C9A990E31CAC75DDC2F4BCB43DED6CAEC57369E2D1707952B517F3BE8ACEE20C0A68EBC;ou=411C1917BCAD24C392D7A77061EB1ED8FEC0DA0BFC9DB569443E9AF0E581C15A8352288F6F2E49595FF443470D125C5CE4830208363683D474517C07308C9DE8A06F29D7ADCB6BBCF733C713C264F361;wq_uits=;wq_auth_token=243669B93A08931CEDC59B326F0141677352962A7CDA3B9AC5BC398F81E7CA78;cartLastOpTime=1621912940;cartNum=100;wq_addr=2865567071%7C19_1601_3634_63211%7C%E5%B9%BF%E4%B8%9C_%E5%B9%BF%E5%B7%9E%E5%B8%82_%E6%B5%B7%E7%8F%A0%E5%8C%BA_%E6%B1%9F%E6%B5%B7%E8%A1%97%E9%81%93%7C%E5%B9%BF%E4%B8%9C%E5%B9%BF%E5%B7%9E%E5%B8%82%E6%B5%B7%E7%8F%A0%E5%8C%BA%E6%B1%9F%E6%B5%B7%E8%A1%97%E9%81%93%E4%B8%9C%E6%B5%B7%E5%A4%A7%E8%A1%97%E5%8D%9722%E5%8F%B7%EF%BC%88%E8%8F%9C%E9%B8%9F%E9%A9%BF%E7%AB%99%EF%BC%89%7C113.317%2C23.0905;__wga=1638758699401.1638758699396.1638497442989.1638349398525.2.3;shshshfpa=f68aecf2-6d68-880b-a2bd-a8cf1b975ac9-1638349398;shshshfpb=ncUWdd5Y0URfgdoq9oIcS5A%3D%3D;hf_time=1638758699996;wq_skey=zw09824B44745EBA6912C9EF13C3F1CCB5898D96544CB3B5A67411F5E2D3F5AB81F071776D8F6FE5FFF9B05FF96157C0A7A45B93757CCF1CAB610352A30C9A990E31CAC75DDC2F4BCB43DED6CAEC57369E2D1707952B517F3BE8ACEE20C0A68EBC;network=wifi;__jda=122270672.f03f3f9c25208c422c07b910e21cdb89.1638758699185.1638758699185.1638758699185.1;__jdv=122270672%7Candroidapp%7Ct_335139774%7Cappshare%7CWxfriends%7C1638758699401;shshshsID=592f0d4f85d80cde12c129dc72503d15_1_1638758699787";
    }

    @Test
    void getTreeInfoV13() {
        System.out.println(api.getTreeInfoV13(openId, cookie));
    }

    @Test
    void signForFarm() {
    }

    @Test
    void signForFarmV13() {
        System.out.println(api.signForFarmV13(openId, cookie));
    }

    @Test
    void waterGoodForFarm() {
        System.out.println(api.waterGoodForFarm(openId, cookie));
    }

    @Test
    void continuousWater() {
    }

    @Test
    void firstWaterTaskForFarm() {
        System.out.println(api.firstWaterTaskForFarm(openId, cookie));
    }

    @Test
    void gotThreeMealForFarm() {
    }

    @Test
    void gotThreeMealForFarmV13() {
        System.out.println(api.gotThreeMealForFarmV13(openId, cookie));
    }

    @Test
    void task() {
        System.out.println(api.taskList(openId, cookie));
    }
}