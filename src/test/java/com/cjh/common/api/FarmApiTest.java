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
class FarmApiTest {

    @Autowired
    private FarmApi api;

    private String openId;
    private String cookie;

    @BeforeEach
    void setUp() {
        openId = "";
        cookie = "abtest=20210831171935259_98; pt_pin=jd_55b363483f5b1; pwdt_id=jd_55b363483f5b1; __jdc=122270672; shshshfpa=865cd45e-3829-ae80-16a7-91900fc107b3-1627446206; shshshfpb=k6ofREoT8asjP4h76%20WM1oA%3D%3D; webp=1; visitkey=36027343577382353; share_cpin=; share_open_id=; share_gpin=; channel=; source_module=; erp=; cartNum=82; wq_addr=4924833482%7C19_1601_3634_63211%7C%u5E7F%u4E1C_%u5E7F%u5DDE%u5E02_%u6D77%u73E0%u533A_%u6C5F%u6D77%u8857%u9053%7C%u5E7F%u4E1C%u5E7F%u5DDE%u5E02%u6D77%u73E0%u533A%u6C5F%u6D77%u8857%u9053%u6842%u7530%u897F%u7EA6%u516B%u5DF7%u4E00%u53F7%u83DC%u9E1F%u9A7F%u7AD9%7C113.316513%2C23.091683; jdAddrId=19_1601_3634_63211; jdAddrName=%u5E7F%u4E1C_%u5E7F%u5DDE%u5E02_%u6D77%u73E0%u533A_%u6C5F%u6D77%u8857%u9053; mitemAddrId=19_1601_3634_63211; mitemAddrName=%u5E7F%u4E1C%u5E7F%u5DDE%u5E02%u6D77%u73E0%u533A%u6C5F%u6D77%u8857%u9053%u6842%u7530%u897F%u7EA6%u516B%u5DF7%u4E00%u53F7%u83DC%u9E1F%u9A7F%u7AD9; addrId_1=4924833482; cartLastOpTime=1631851318; wq_logid=1631851318_1804289383; cd_eid=IUMMKBQZXV3Q5KL6EGBJRPILE564TDVK66K2CJXOSRVBKEYYYYXEHSHY5NV542CTFGJRV5LTSD5MT5QVLR272SXQFI; shareChannel=; 3AB9D23F7A4B3C9B=IUMMKBQZXV3Q5KL6EGBJRPILE564TDVK66K2CJXOSRVBKEYYYYXEHSHY5NV542CTFGJRV5LTSD5MT5QVLR272SXQFI; qd_uid=KTPGF82J-EKE0FFR8HZ91Q4832IPB; qd_fs=1631949208212; __wga=1635767407657.1635767403148.1631932629203.1630552826742.2.5; shshshfp=3a06ec3c720c6086c8a83914a190b78b; unpl=V2_ZzNtbUtURRUnW0BVKElZUGIFFw4RX0YdcwFHXXpKXQdvABVVclRCFnUURlRnGFkUZgIZXkBcQRVFOEVVexlbAG4KFl1yZ3MWdThHZHseXgRjBxFbRFBLFnAIQVRyGlwBZQUTbXJXSxJFOHZRexhdAGcBEFtLZ0IldwlHVnMQWgxuBSIWLFYOFXIKR1B%2FGloDYAsRWEJQQxx2CEJWfRhsB2cDEFhyVA%3D%3D%7CADC_ER8EVOtczlAXwIdGAc%2Bbt4EfI37a35CGuzWd1NWUhpQx7%2FeVvij2V1IcBBZdyvU5K%2FhReE4Wm5MjgOoPN4qKxlptINBDU5rR%2B%2BQAJdszVcvCFZDBh%2BdsaZExkM8TjIHAreNCrhs4Nthgmz34NCdDOA9yGjNanOd6Zaj0RNCWKTh48Rtk%2FMl%2FxpZ6MhSUwser%2Fjqw9KSTznh5qKDKXF1XXNqhigKMO9AbPpYmYsOv%2FpzkNW6ZFM9mMftcVCPOiWNgeN2TngzOmQZ%2FX%2FK%2FaNhnPBgAKRibaFCh3R4Tmsf9MSTtoCkucHev6UF06Yt1MkqdZhUZwkIVB9NiCL%2FLpEQRHDN%2F6vIDkP2f9MpupDXqwByEwICD45yAmqWFg8%2BBrTUnvWxUoiWZDdcBtIJUOSPTCMy1ub9K6IPCI%2F8LPKenB0AWnvpj1fwQpoRLJadq0mIFuIeZ7BZvZL7f2oHroiI%2BTdIwcKlgtjwRgzyk1G1a6QvtuBvZT7Ukh%2FGFaqkErHmhNHSdT83q1HXBYsaXY3MQogAtgdIZJ6UuAdi6y4pIm9oRhVmjG2CdZx%2FdkAyv%2B7epkrIAIMVGQTcL28SB8jE9Pg%3D%3D; plusCustomBuryPointToken=1636903299853_4020; ipLoc-djd=19_1601_3634_0; BATQW722QTLYVCRD={\"tk\":\"jdd01L2JLMBI2L4P3SFYM5WO3IZ5SOL3ECZ3U7LXDR3KPNXGE3JHTLOD42QR7QDKQ4EOZOWXD5UGEFKJ4NT4FFIBMDTF5TGTH7JFO26IH5ZI01234567\",\"t\":1638347928786}; qd_ls=1637113727492; qd_ts=1638347992191; qd_sq=10; pt_key=app_openAAJhqYPRADAzFyU95Hr8FfW2BwKbhReJKA16kyOO3lhoColpQrPUEvaQ-KHorOGxc1TwpwxaQnw; sid=dafd0da2ec8d7e14e4649d4ae1d1b42w; __jda=122270672.16305527769241618215370.1630552776.1638347191.1638499369.44; __jdb=122270672.1.16305527769241618215370|44.1638499369; mba_sid=126.4; pre_session=lld4hLUeqJY5L1+eBexQrq1/z4QQ93tV|205; pre_seq=2; __jdv=122270672%7Ckong%7Ct_2011648851_%7Cjingfen%7C8271cb70ba4d474bb94978080b039269%7C1635956610000; __jd_ref_cls=Babel_dev_other_DDNC_Pot; mba_muid=16305527769241618215370.126.1638499388250";
    }

    @Test
    void getTreeInfo() {
        System.out.println(api.getTreeInfo(openId, cookie));
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
    void signForFarmV14() {
        System.out.println(api.signForFarmV14(openId, cookie));
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
}