package com.cjh.common.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
            + "    \"TCSecTk\": \"ZfOeS2YX9IStsHx-3-C4uwGbHNV1okjW5XYrqEPEf0sEn2BQ3zuvDQkFIeLZDYqLdoAvLsi6pmc_DMiPLbcnRrU56vvZ_b8JL9VyY9rjxkowdszFN6wTw4jePfJ6bufs9j6Lv0MI6uUd3fBiM500JaCzGemYO0B1Wl4ZgjuE6eNA4FgyoBmLm4wcZiMZHszTIz8ti7oEwDtWCMVlYY5bMQ**4641\",\n"
            + "    \"apmat\": \"o498X0Zcbr5AvQoatOpUe3m9Gyh8\",\n"
            + "    \"sectoken\": \"ZfOeS2YX9IStsHx-3-C4uwGbHNV1okjW5XYrqEPEf0sEn2BQ3zuvDQkFIeLZDYqLdoAvLsi6pmc_DMiPLbcnRrU56vvZ_b8JL9VyY9rjxkowdszFN6wTw4jePfJ6bufs9j6Lv0MI6uUd3fBiM500JaCzGemYO0B1Wl4ZgjuE6eNA4FgyoBmLm4wcZiMZHszTIz8ti7oEwDtWCMVlYY5bMQ**4641\",\n"
            + "    \"cookie\": \"__tctmc=33778749.217916544; __tctmd=33778749.48811256; __tctma=33778749.1639535679851980.1639535679282.1639535679282.1639535679282.1; __tctmb=33778749.3205786149948308.1639535679282.1639535679282.1; __tctmu=33778749.0.0; __tctmz=33778749.1639535679282.1.1.utmccn=(referral)|utmcsr=qq.com|utmcct=connect/oauth2/authorize|utmcmd=referral; longKey=1639535679851980; __tctrack=0\",\n"
            + "    \"unionId\": \"ohmdTtxCLKfllUbfPa2bqIif3FN4\",\n"
            + "    \"openId\": \"o498X0Zcbr5AvQoatOpUe3m9Gyh8\",\n"
            + "    \"gameId\": \"83b5df9bc0b95475d69895024e0aa6d6\",\n"
            + "    \"token\": \"521bb938d167da6d883de8b3503fd62f\"\n"
            + "}";
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