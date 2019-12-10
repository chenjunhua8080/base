package com.cjh.douban.test;

import com.cjh.douban.service.FarmLogService;
import com.cjh.douban.service.FarmService;
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

    @Test
    public void getUser() {
        System.out.println(username);
    }

    @Autowired
    private FarmService farmService;
    @Autowired
    private FarmLogService farmLogService;

    @Test
    public void gotThreeMealForFarm() {
        farmService.gotThreeMealForFarm();
    }

    @Test
    public void signForFarm() {
        farmService.signForFarm();
    }

    @Test
    public void waterGoodForFarm() {
        farmService.waterGoodForFarm();
    }

    @Test
    public void firstWaterTaskForFarm() {
        farmService.firstWaterTaskForFarm();
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
        System.out.println(farmLogService.getTodayFarmLog());
    }

}
