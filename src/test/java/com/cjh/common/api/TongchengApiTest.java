package com.cjh.common.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
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
        cookie = "{\n"
            + "    \"Host\": \"wx.17u.cn\",\n"
            + "    \"TCReferer\": \"page%2FAC%2Fsign%2Fmsindex%2Fmsindex\",\n"
            + "    \"TCSecTk\": \"ZfOeS2YX9IStsHx-3-C4u8thxKy4z8WUWSltm7lDI8tPWZdxUf88Baxakkt4TX42doAvLsi6pmc_DMiPLbcnRrU56vvZ_b8JL9VyY9rjxkowdszFN6wTw4jePfJ6bufs9j6Lv0MI6uUd3fBiM500JaCzGemYO0B1Wl4ZgjuE6ePfCS_jIzz99NQL-e7uqIFIPRXI5G4s-6nZjcD2vlY6WQ**4641\",\n"
            + "    \"apmat\": \"o498X0Zcbr5AvQoatOpUe3m9Gyh8\",\n"
            + "    \"wxapp\": \"0\",\n"
            + "    \"sectoken\": \"ZfOeS2YX9IStsHx-3-C4u8thxKy4z8WUWSltm7lDI8tPWZdxUf88Baxakkt4TX42doAvLsi6pmc_DMiPLbcnRrU56vvZ_b8JL9VyY9rjxkowdszFN6wTw4jePfJ6bufs9j6Lv0MI6uUd3fBiM500JaCzGemYO0B1Wl4ZgjuE6ePfCS_jIzz99NQL-e7uqIFIPRXI5G4s-6nZjcD2vlY6WQ**4641\",\n"
            + "    \"unionId\": \"ohmdTtxCLKfllUbfPa2bqIif3FN4\",\n"
            + "    \"openId\": \"o498X0Zcbr5AvQoatOpUe3m9Gyh8\",\n"
            + "    \"cookie\": \"__tctma=33778749.1639648696981735.1639648696131.1639648696131.1639648696131.1; __tctmu=33778749.0.0; __tctmz=33778749.1639648696131.1.1.utmccn=(referral)|utmcsr=qq.com|utmcct=connect/oauth2/authorize|utmcmd=referral; longKey=1639648696981735; __tctrack=0; __tctmc=33778749.218426255; __tctmd=33778749.737325; __tctmb=33778749.3456110088999740.1639648696131.1639649290411.2\",\n"
            + "    \"gameId\": \"83b5df9bc0b95475d69895024e0aa6d6\",\n"
            + "    \"token\": \"1e80789e0ff5da686ac102d5ea427e7c\"\n"
            + "}";
    }

    @Test
    void getSignInfo() {
        api.getSignInfo(openId, cookie);
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
        api.getTaskAward(openId, cookie, "T103");
    }
}

/**
 * 获取token
 */
//POST https://sgame.moxigame.cn/planttree_tc//game/local/logincheck HTTP/1.1
//Host: sgame.moxigame.cn
//Connection: keep-alive
//Content-Length: 566
//User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat
//Content-Type: application/json
//Accept: */*
//Sec-Fetch-Site: same-site
//Sec-Fetch-Mode: cors
//Sec-Fetch-Dest: empty
//Referer: https://game-cdn.moxigame.cn/ClickEliminate/LYPlant/Web/index.html?activeId=A_68492_R_0_D_20200903&refid=685339143&oid=sIa6L0ZffokX2Df1LboOdxHq0Yhgqa1NVk0Y5tm1mNs=&uid=DDlUP2SVYjcEkDjB6GVQLsg+54RnPQbU2G2KEQ+IR74=&img=https://thirdwx.qlogo.cn/mmopen/vi_32/wrK88GbrwEEQdQbq12ZsXibicZoW2n3z38hUyhlS2rufgoYrgrbjMq67kDibJSEV28yndib1DP5gNSw3U5SaGlNrBw/132&name=%e6%81%ad%e2%83%b0%e5%96%9c%e2%83%b0%e5%8f%91%e2%83%b0%e8%b4%a2%e2%83%b0%e7%87%95%e2%83%b0%e7%87%95%e2%83%b0&sectoken=ZfOeS2YX9IStsHx-3-C4uw6sBlotFcDXl8mgV3YN6Z1YJkr5YvFqm0lBrc7miV186tKsZwDcMimt2CZAfUpkl-h4-6reQFgKUsgAGEzzTJjZSwvQRjGFHukktBdHXgVt9j6Lv0MI6uUd3fBiM500JaCzGemYO0B1Wl4ZgjuE6eM3fo8zsulI96GCe_cv1unNQZK-3pLsUD7dtoDBlbp9yA**4641&time=1639648692&sign=3d44515b3ef4fc417e6e06dbbdc7ad03
//Accept-Encoding: gzip, deflate, br
//Accept-Language: en-us,en
//
//{"info":{"appId":"10015","userId":"83b5df9bc0b95475d69895024e0aa6d6","activeId":"A_68492_R_0_D_20200903","startTime":1599062400000,"endTime":1667145600000,"time":"1639648695940","openId":"DDlUP2SVYjcEkDjB6GVQLsg+54RnPQbU2G2KEQ+IR74=","nickname":"恭⃰喜⃰发⃰财⃰燕⃰燕⃰","pltId":"10539139","avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/wrK88GbrwEEQdQbq12ZsXibicZoW2n3z38hUyhlS2rufgoYrgrbjMq67kDibJSEV28yndib1DP5gNSw3U5SaGlNrBw/132","platform":"{\"money\":0,\"moneyId\":\"X029\"}","sign":"6d6b30273dc21f737c5cc3a1460184e6"},"sourceChannel":"685339143"}
//-->ed387dc6a5494e269feb9b46d5e0783a