package com.cjh.common.test;

import com.alibaba.fastjson.JSONObject;
import com.cjh.common.api.FarmApi;
import com.cjh.common.api.JDApi;
import com.cjh.common.service.ReqLogService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestFarmController {

    @Value("${douban.user.name}")
    private String username;
    @Autowired
    private FarmApi farmApi;
    @Autowired
    private JDApi jdApi;
    @Autowired
    private ReqLogService farmLogService;

    @Test
    public void getUser() {
        System.out.println(username);
    }

    @Test
    public void gotThreeMealForFarm() {
        String openId = "";
        String cookie = "";
        farmApi.gotThreeMealForFarm(openId, cookie);
    }

    @Test
    public void getTreeInfo() {
        String openId = "";

        String cookie = "pin=jd_55b363483f5b1;wskey=AAJgaH2RAEBfY00Y9MD3oEeiEvhkpzPd59tNek2baqljFUIu7A3zqNMrnQn8pGij-U2wnMzbuZWYsREMDgzeqfW3Ei4pKSCF;whwswswws=gXzBdVQWD6gDi+vHFZJFineCctq5BTqyGmoU0YvMKvRhXW1yeDMkTNVySHU6ockWx;unionwsws={\"devicefinger\":\"eidA2c4481233es2cDODdE0oQDGZAsqjipfs6nAzhOSbOtWyvbmq6irkk3V+DBzs8bnZ4XBot0EvrytRfYqmEiF9aQYIV4R2JEYZIQ+mxPHZbZWdOR4x\",\"jmafinger\":\"gXzBdVQWD6gDi+vHFZJFineCctq5BTqyGmoU0YvMKvRhXW1yeDMkTNVySHU6ockWx\"};";
        System.out.println(farmApi.waterGoodForFarm(openId, cookie));
    }


    @Test
    public void getSingleShop() {
        String openId = "";
        String cookie = "buildtime=20210524;wxapp_type=1;wxapp_version=7.5.240;wxapp_scene=1035;cid=5;visitkey=45989107793251615949380409;PPRD_P=CT.138567.7.59-EA.17078.25.2-LOGID.1621911458733.1898294072;gender=1;province=Guangdong;city=Guangzhou;country=China;wxNickName=%E6%81%AD%E2%83%B0%E5%96%9C%E2%83%B0%E5%8F%91%E2%83%B0%E8%B4%A2%E2%83%B0;wxAvatarUrl=https%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FrhwWwianRPt4GFiaSiabOpWdbtTIygNkzohRpt5IjbrSsfxxSWv7QOicar4xKdhpnL7vvQKnG3MtibrTfhAey3tIicJQ%2F132;nickName=%E6%81%AD%E2%83%B0%E5%96%9C%E2%83%B0%E5%8F%91%E2%83%B0%E8%B4%A2%E2%83%B0;avatarUrl=https%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FrhwWwianRPt4GFiaSiabOpWdbtTIygNkzohRpt5IjbrSsfxxSWv7QOicar4xKdhpnL7vvQKnG3MtibrTfhAey3tIicJQ%2F132;shshshfp=8ef1052498d8c579b6e16b0a06aeb4fc;jdpin=jd_55b363483f5b1;mcossmd=1d950c6a2c1e81af880ffeeba9cdf429;open_id=oTGnpnPmaTsb2gk7UNOb4qxMLOdc;pin=jd_55b363483f5b1;pinStatus=0;unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;wid=5077628157;wq_uin=5077628157;wq_unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;wxapp_openid=oA1P50NL6OdsFy2lrwp0tN1tw7Ow;skey=zwAC65A9293A415B3B993825EC103D18A141A519003D6ED35A1BF83B57E1D0D1DE7CD51680EE26F7E88E90861C2FBC2A02BCAD77B7CF2325FEE2EF314B951AC7FC2A7069C80AEBD8D5F78AC44A0FBD256CA2F70BF67908D55725D0F34C55EA016D;ou=889A6CCC66C4456636860B7AE6DA48F7FEC0DA0BFC9DB569443E9AF0E581C15A8352288F6F2E49595FF443470D125C5CE4830208363683D474517C07308C9DE8A06F29D7ADCB6BBCF733C713C264F361;wq_uits=;wq_auth_token=A850BD5AAB5E293B3CB0E7C700E786DE016DBE266FF514247F8A4E30680DA94D;cartLastOpTime=1621395297;cartNum=100;wq_addr=2865567071%7C19_1601_3634_63211%7C%E5%B9%BF%E4%B8%9C_%E5%B9%BF%E5%B7%9E%E5%B8%82_%E6%B5%B7%E7%8F%A0%E5%8C%BA_%E6%B1%9F%E6%B5%B7%E8%A1%97%E9%81%93%7C%E5%B9%BF%E4%B8%9C%E5%B9%BF%E5%B7%9E%E5%B8%82%E6%B5%B7%E7%8F%A0%E5%8C%BA%E6%B1%9F%E6%B5%B7%E8%A1%97%E9%81%93%E4%B8%9C%E6%B5%B7%E5%A4%A7%E8%A1%97%E5%8D%9722%E5%8F%B7%EF%BC%88%E8%8F%9C%E9%B8%9F%E9%A9%BF%E7%AB%99%EF%BC%89%7C113.317%2C23.0905;__wga=1621911458675.1621911323123.1621409788572.1621394767950.8.4;shshshfpa=1d4d0784-d95d-1134-74cb-02398a1aac05-1621394768;shshshfpb=d2Hn0y5kyWuqzUhhNIAgLsg%3D%3D;hf_time=1621911323984;__jda=122270672.8b8395c81aa2983fee28bbd7171bac3b.1621911322605.1621911322605.1621911322605.1;network=wifi;__jdv=122270672%7Cweixin%7Ct_1000072662_xcx_1035_cdl%7Cxcx%7C-%7C1621911458675;shshshsID=b15e482e9a880ddf0adc294ed26d5a6f_6_1621911458292;wq_skey=zwAC65A9293A415B3B993825EC103D18A141A519003D6ED35A1BF83B57E1D0D1DE7CD51680EE26F7E88E90861C2FBC2A02BCAD77B7CF2325FEE2EF314B951AC7FC2A7069C80AEBD8D5F78AC44A0FBD256CA2F70BF67908D55725D0F34C55EA016D";
        System.out.println(jdApi.continuousFeed(6,openId, cookie));
    }

    @Test
    public void signForFarm() {
        String openId = "";
        String cookie = "";
        farmApi.signForFarm(openId, cookie);
    }

    @Test
    public void waterGoodForFarm() {
        String openId = "";
        String cookie = "";
        farmApi.waterGoodForFarm(openId, cookie);
    }

    @Test
    public void firstWaterTaskForFarm() {
        String openId = "";
        String cookie = "";
        farmApi.firstWaterTaskForFarm(openId, cookie);
    }

    @Test
    public void getOpenId() {
        Pattern pattern = Pattern.compile("wxapp_openid=(.*?);");
        Matcher matcher = pattern.matcher(
            "avatarUrl=https%3A%2F%2Fimg11.360buyimg.com%2Fjdphoto%2Fs120x120_jfs%2Ft1%2F82031%2F9%2F5133%2F19337%2F5d36bcadE9cdb240c%2Fccc58f73b721ec2d.png;buildtime=20191205;cid=5;city=Shenzhen;country=China;gender=1;jdpin=jd_55b363483f5b1;mcossmd=1d950c6a2c1e81af880ffeeba9cdf429;network=wifi;nickName=%E4%BA%AC%E4%B8%9C%E7%94%A8%E6%88%B7;open_id=oTGnpnPmaTsb2gk7UNOb4qxMLOdc;pin=jd_55b363483f5b1;pinStatus=0;province=Guangdong;shshshfp=7d792e82a51b89d240ae99a46661852a;shshshfpa=db548e5d-53c1-d5f2-8d36-3ed531f1be3e-1573104913;shshshfpb=npJNCBEfqMQe0IYhpb9FxaA%3D%3D;unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;visitkey=46662873544821088;wid=5077628157;wq_uin=5077628157;wq_unionid=oCwKwuHgS1L_iGpf04ysUhY-ea_E;wxAvatarUrl=https%3A%2F%2Fimg11.360buyimg.com%2Fjdphoto%2Fs120x120_jfs%2Ft1%2F82031%2F9%2F5133%2F19337%2F5d36bcadE9cdb240c%2Fccc58f73b721ec2d.png;wxNickName=%E4%BA%AC%E4%B8%9C%E7%94%A8%E6%88%B7;wxapp_openid=oA1P50NL6OdsFy2lrwp0tN1tw7Ow;wxapp_scene=1008;wxapp_type=1;wxapp_version=6.1.26;new=new;PPRD_P=EA.17078.5.1-LOGID.1575875624156.1678463219;__jda=122270672.0c0820216c4df619e60461c07c3717db.1575875589442.1575875589442.1575875589442.1;__jdv=122270672%7Cweixin%7Ct_1000578832_xcx_1008_qlxxkp%7Cxcx%7C-%7C1575875624117;__wga=1575875624116.1575875589558.1575688421774.1573104913305.4.8;hf_time=1575875625100;maxAge=86400;ou=A57BE5395C4C2D2D9A1BFC3A0A3CEC61FEC0DA0BFC9DB569443E9AF0E581C15A8352288F6F2E49595FF443470D125C5CE4830208363683D474517C07308C9DE8A06F29D7ADCB6BBCF733C713C264F361;shshshsID=9d075394f9423b74ee62bf9cf2d32e65_2_1575875624148;skey=zw87A8C09701AA01D9FA7BB72829C361F8B5AEF51E0ECAE8B391E8CDE8F768DC0AFB405762ABBA1C599901D17DB82647A7A45B93757CCF1CAB610352A30C9A990E31CAC75DDC2F4BCB43DED6CAEC57369E2D1707952B517F3BE8ACEE20C0A68EBC;wq_auth_token=28BE380832D27DEA527B95EB58CFAA1A1347A5219E37436DCEB4437AC4D2547D;wq_skey=zw87A8C09701AA01D9FA7BB72829C361F8B5AEF51E0ECAE8B391E8CDE8F768DC0AFB405762ABBA1C599901D17DB82647A7A45B93757CCF1CAB610352A30C9A990E31CAC75DDC2F4BCB43DED6CAEC57369E2D1707952B517F3BE8ACEE20C0A68EBC;");
        if (matcher.find()) {
            System.out.println(matcher.group(1));
        } else {
            System.out.println("cookie中为匹配到wxapp_openid...");
        }
    }

    @Test
    public void getTodayFarmLog() {
        System.out.println(farmLogService.getTodayReqLog("oA1P50NL6OdsFy2lrwp0tN1tw7Ow"));
    }

    @Test
    public void testjson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderCode", "12321321321");
        String s = JSONObject.toJSONString(jsonObject);
        System.out.println(s);
    }

}
