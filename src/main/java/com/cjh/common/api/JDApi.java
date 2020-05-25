package com.cjh.common.api;

import com.alibaba.fastjson.JSONObject;
import com.cjh.common.entity.ReqLog;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.feign.CloudFeignClient;
import com.cjh.common.resp.CollectCakeResp;
import com.cjh.common.resp.HomeDataResp;
import com.cjh.common.resp.HomeDataResp.DataBean.ResultBean.CakeBakerInfoBean.RaiseInfoBean;
import com.cjh.common.service.ReqLogService;
import com.cjh.common.util.HttpUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class JDApi {

    /**
     * 点击小天使获取金币, 限制不详
     * <p>
     * POST http://api.m.jd.com/client.action?functionId=cakebaker_ckCollectScore HTTP/1.1
     * Host: api.m.jd.com
     * Connection: keep-alive
     * Content-Length: 499
     * Accept: application/json, text/plain, /
     * Origin: https://bunearth.m.jd.com
     * User-Agent: jdapp;android;8.5.12;10;867092042012392-5885e9fcd3c1;network/wifi;model/RMX1991;addressid/2082865096;aid/637bd8ae1589f566;oaid/;osVer/29;appBuild/73078;psn/867092042012392-5885e9fcd3c1|66;psq/6;uid/867092042012392-5885e9fcd3c1;adk/;ads/;pap/JA2015_311210|8.5.12|ANDROID
     * 10;osv/10;pv/68.7;jdv/0|kong|t_35460321_|tuiguang|72fc343003794a5d81c8fd5927c51d4e|1587882752;ref/com.jingdong.app.mall.home.JDHomeFragment;partner/oppo;apprpd/Home_Main;Mozilla/5.0
     * (Linux; Android 10; RMX1991 Build/QKQ1.191201.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0
     * Chrome/66.0.3359.126 MQQBrowser/6.2 TBS/045136 Mobile Safari/537.36
     * Content-Type: application/x-www-form-urlencoded
     * Referer: https://bunearth.m.jd.com/babelDiy/Zeus/3xAU77DgiPoDvHdbXUZb95a7u71X/index.html?babelChannel=fc1&lng=113.320497&lat=23.021689&sid=2cb2c5f0ae4df2e2a0f3a284af64819w&un_area=19_1601_36953_51883
     * Accept-Encoding: gzip, deflate, br
     * Accept-Language: zh-CN,en-US;q=0.9
     * Cookie: __jdc=122270672; unpl=V2_ZzNtbURUFEd3DURVehtaDWJTFghLVxEcIl1CXHgfDgFnVhcJclRCFnQUR1FnGlsUZwYZX0FcQRBFOERQfh5dB2QDIm1yVEMldDhGUHIfVQxkBRdVRVRAFXEJTlF8GVoMZTMiWkJecyU%2BXRo6PVgeNWIDGl1KVkMWcjhHZHkYXQRgBRZeS1ZzXhsJC1R%2FEFoMbgAUWEpQQBZ1DEdcfh5cA24BIl1DVkIUdQtBU3kpXw%3D%3D;
     * pt_pin=jd_55b363483f5b1; pwdt_id=jd_55b363483f5b1; 3AB9D23F7A4B3C9B=NGFVAAG6QW65MTDKUZFX4T5GJAJ25TOI6AH3MDQ5IXLV5YAMHFM2LOX3QSLVHHJFCKOJMCM7EGVQVLS53ZJI6Q4MSQ;
     * __tak=01a965cb7ad9bfef46230a9988fbc6bc2c76f0564e79cb83d4d1d09ae510257c10a4b7632202f039d173cdbd8913f9960fc9de0bb01bdb1703885ebc9f0c214d4f533e5b6db73eacece1440cbb01a39b;
     * mobilev=touch; qd_uid=KAAGJTR3-APOKH09POYBFEC51H8VA; qd_fs=1589683565915; qd_ls=1589683565915;
     * pt_key=app_openAAJeyTjWADCJB7PGpuLJ3LY5MBOvmNeEmijEICmUdGkW50PLVT35VdkQ9ZVQAP0my71nXzSUrjI;
     * sid=2cb2c5f0ae4df2e2a0f3a284af64819w; __jdv=122270672%7Ckong%7Ct_35460321_%7Ctuiguang%7C72fc343003794a5d81c8fd5927c51d4e%7C1587882752000;
     * qd_ts=1590245648150; qd_sq=2; BATQW722QTLYVCRD={"tk":"jdd01DGUK4S3YVHZC4BSKMP6ABXU4GDOBDFLD6RRPKKZNBSZ4H4NE7QQJ7UVPZYJUQ5K7Q7PBTZ3SSEKIYK5I3DW4KEL6A47RTFJN76E22CQ01234567","t":1590245648236};
     * shshshfpb=nEl9f9zYYVAJ6%20JVSY3EhqQ%3D%3D; shshshfpa=8455c840-b96a-1556-eba0-cc96b5cf47b6-1581992010;
     * qd_ad=-%7C-%7C-%7C-%7C0; pre_seq=2; __jda=122270672.15896822787641340345762.1589682278.1590287328.1590291299.5;
     * __jdb=122270672.1.15896822787641340345762|5.1590291299; pre_session=867092042012392-5885e9fcd3c1|66;
     * shshshfp=cfc926b46fcdef2893f7a0058425f65b; shshshsID=9e63e0710fff0a4ae4478cc11541620d_1_1590291300090;
     * __jd_ref_cls=Babel_dev_adv_elfin; mba_sid=68.7; mba_muid=15896822787641340345762.68.1590291979166
     * X-Requested-With: com.jingdong.app.mall
     * <p>
     * functionId=cakebaker_ckCollectScore&body={"taskId":35,"itemId":"1","safeStr":"{\"is_trust\":true,\"sign\":\"70F04F0F710D2DD9952BBD40730C558A24D5031D7E2903726CE4F1724C701F2B\",\"fpb\":\"nEl9f9zYYVAJ6
     * JVSY3EhqQ==\",\"time\":1590291978174,\"encrypt\":4,\"nonstr\":\"ZtrYMsH8G4\",\"info\":\"\",\"token\":\"\",\"appid\":\"50072\",\"sceneid\":\"goldElfinBtn\",\"secretp\":\"Vl4IStM3QydXJKE38Wr7YB6e33c\",\"buttonid\":\"goldElfin\",\"uid\":\"867092042012392-5885e9fcd3c1\"}"}&client=wh5&clientVersion=1.0.0
     * <p>
     * {"code":0,"data":{"bizCode":0,"bizMsg":"success","result":{"maxTimes":30,"score":"7","taskStatus":1,"times":17,"userScore":"1233"},"success":true},"msg":"调用成功"}
     */
    private final String url_cakebaker_ckCollectScore = "http://api.m.jd.com/client.action?functionId=cakebaker_ckCollectScore";
    /**
     * 我的金币
     * <p>
     * POST http://api.m.jd.com/client.action?functionId=cakebaker_getHomeData HTTP/1.1
     * Host: api.m.jd.com
     * Connection: keep-alive
     * Content-Length: 71
     * Accept: application/json, text/plain, /
     * Origin: https://bunearth.m.jd.com
     * User-Agent: jdapp;android;8.5.12;10;867092042012392-5885e9fcd3c1;network/wifi;model/RMX1991;addressid/2082865096;aid/637bd8ae1589f566;oaid/;osVer/29;appBuild/73078;psn/867092042012392-5885e9fcd3c1|66;psq/4;uid/867092042012392-5885e9fcd3c1;adk/;ads/;pap/JA2015_311210|8.5.12|ANDROID
     * 10;osv/10;pv/68.5;jdv/0|kong|t_35460321_|tuiguang|72fc343003794a5d81c8fd5927c51d4e|1587882752;ref/com.jingdong.app.mall.home.JDHomeFragment;partner/oppo;apprpd/Home_Main;Mozilla/5.0
     * (Linux; Android 10; RMX1991 Build/QKQ1.191201.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0
     * Chrome/66.0.3359.126 MQQBrowser/6.2 TBS/045136 Mobile Safari/537.36
     * Content-Type: application/x-www-form-urlencoded
     * Referer: https://bunearth.m.jd.com/babelDiy/Zeus/3xAU77DgiPoDvHdbXUZb95a7u71X/index.html?babelChannel=fc1&lng=113.320497&lat=23.021689&sid=2cb2c5f0ae4df2e2a0f3a284af64819w&un_area=19_1601_36953_51883
     * Accept-Encoding: gzip, deflate, br
     * Accept-Language: zh-CN,en-US;q=0.9
     * Cookie: __jdc=122270672; unpl=V2_ZzNtbURUFEd3DURVehtaDWJTFghLVxEcIl1CXHgfDgFnVhcJclRCFnQUR1FnGlsUZwYZX0FcQRBFOERQfh5dB2QDIm1yVEMldDhGUHIfVQxkBRdVRVRAFXEJTlF8GVoMZTMiWkJecyU%2BXRo6PVgeNWIDGl1KVkMWcjhHZHkYXQRgBRZeS1ZzXhsJC1R%2FEFoMbgAUWEpQQBZ1DEdcfh5cA24BIl1DVkIUdQtBU3kpXw%3D%3D;
     * pt_pin=jd_55b363483f5b1; pwdt_id=jd_55b363483f5b1; 3AB9D23F7A4B3C9B=NGFVAAG6QW65MTDKUZFX4T5GJAJ25TOI6AH3MDQ5IXLV5YAMHFM2LOX3QSLVHHJFCKOJMCM7EGVQVLS53ZJI6Q4MSQ;
     * __tak=01a965cb7ad9bfef46230a9988fbc6bc2c76f0564e79cb83d4d1d09ae510257c10a4b7632202f039d173cdbd8913f9960fc9de0bb01bdb1703885ebc9f0c214d4f533e5b6db73eacece1440cbb01a39b;
     * mobilev=touch; qd_uid=KAAGJTR3-APOKH09POYBFEC51H8VA; qd_fs=1589683565915; qd_ls=1589683565915;
     * pt_key=app_openAAJeyTjWADCJB7PGpuLJ3LY5MBOvmNeEmijEICmUdGkW50PLVT35VdkQ9ZVQAP0my71nXzSUrjI;
     * sid=2cb2c5f0ae4df2e2a0f3a284af64819w; __jdv=122270672%7Ckong%7Ct_35460321_%7Ctuiguang%7C72fc343003794a5d81c8fd5927c51d4e%7C1587882752000;
     * qd_ts=1590245648150; qd_sq=2; BATQW722QTLYVCRD={"tk":"jdd01DGUK4S3YVHZC4BSKMP6ABXU4GDOBDFLD6RRPKKZNBSZ4H4NE7QQJ7UVPZYJUQ5K7Q7PBTZ3SSEKIYK5I3DW4KEL6A47RTFJN76E22CQ01234567","t":1590245648236};
     * shshshfpb=nEl9f9zYYVAJ6%20JVSY3EhqQ%3D%3D; shshshfpa=8455c840-b96a-1556-eba0-cc96b5cf47b6-1581992010;
     * qd_ad=-%7C-%7C-%7C-%7C0; pre_seq=2; __jda=122270672.15896822787641340345762.1589682278.1590287328.1590291299.5;
     * __jdb=122270672.1.15896822787641340345762|5.1590291299; pre_session=867092042012392-5885e9fcd3c1|66;
     * shshshfp=cfc926b46fcdef2893f7a0058425f65b; shshshsID=9e63e0710fff0a4ae4478cc11541620d_1_1590291300090;
     * mba_sid=68.5; __jd_ref_cls=Babel_dev_adv_elfin; mba_muid=15896822787641340345762.68.1590291851344
     * X-Requested-With: com.jingdong.app.mall
     * <p>
     * functionId=cakebaker_getHomeData&body={}&client=wh5&clientVersion=1.0.0
     * <p>
     * {"code":0,"data":{"bizCode":0,"bizMsg":"success","result":{"activityInfo":{"activityEndTime":1592668799000,"activityStartTime":1589903999000,"mainAwardStartTime":1592481600000,"mainWaitLotteryStartTime":1592467200000,"nowTime":1590291850715},"cakeBakerInfo":{"jdCipher":0,"raiseInfo":{"brandFlag":true,"buttonStatus":1,"curLevelStartScore":"600","firstStatus":0,"fullFlag":false,"levelImageList":["https://m.360buyimg.com/babel/jfs/t1/123756/31/152/48661/5eb3b25aE9bd0a75a/c6a314732a2a1d84.png","https://m.360buyimg.com/babel/jfs/t1/111636/5/5442/45217/5eb3b2afEfb9ba929/15cf8a90ae00dd0c.png","https://m.360buyimg.com/babel/jfs/t1/121050/11/155/44829/5eb3b2c3Ebb2a7a8d/708e52f055ddc5f9.png"],"maxLevelScore":"156850","nextLevelScore":"1400","pkButtonStatus":1,"raiseButtonShow":2,"remainScore":"626","scoreLevel":3,"signPopStatus":0,"totalScore":"1226","usedScore":"600","wxPayStatus":0},"secretp":"Vl4IStM3QydXJKE38Wr7YB6e33c","shareMiniprogramSwitch":0,"userType":1}},"success":true},"msg":"调用成功"}
     */
    private final String url_cakebaker_getHomeData = "http://api.m.jd.com/client.action?functionId=cakebaker_getHomeData";
    private ReqLogService reqLogService;
    private CloudFeignClient cloudFeignClient;

    public static void main(String[] args) {
        String cookie =
            "__jdc=122270672; unpl=V2_ZzNtbURUFEd3DURVehtaDWJTFghLVxEcIl1CXHgfDgFnVhcJclRCFnQUR1FnGlsUZwYZX0FcQRBFOERQfh5dB2QDIm1yVEMldDhGUHIfVQxkBRdVRVRAFXEJTlF8GVoMZTMiWkJecyU%2BXRo6PVgeNWIDGl1KVkMWcjhHZHkYXQRgBRZeS1ZzXhsJC1R%2FEFoMbgAUWEpQQBZ1DEdcfh5cA24BIl1DVkIUdQtBU3kpXw%3D%3D; pt_pin=jd_55b363483f5b1; pwdt_id=jd_55b363483f5b1; 3AB9D23F7A4B3C9B=NGFVAAG6QW65MTDKUZFX4T5GJAJ25TOI6AH3MDQ5IXLV5YAMHFM2LOX3QSLVHHJFCKOJMCM7EGVQVLS53ZJI6Q4MSQ; __tak=01a965cb7ad9bfef46230a9988fbc6bc2c76f0564e79cb83d4d1d09ae510257c10a4b7632202f039d173cdbd8913f9960fc9de0bb01bdb1703885ebc9f0c214d4f533e5b6db73eacece1440cbb01a39b; mobilev=touch; qd_uid=KAAGJTR3-APOKH09POYBFEC51H8VA; qd_fs=1589683565915; qd_ls=1589683565915; qd_ts=1590245648150; qd_sq=2; BATQW722QTLYVCRD={\"tk\":\"jdd01DGUK4S3YVHZC4BSKMP6ABXU4GDOBDFLD6RRPKKZNBSZ4H4NE7QQJ7UVPZYJUQ5K7Q7PBTZ3SSEKIYK5I3DW4KEL6A47RTFJN76E22CQ01234567\",\"t\":1590245648236}; shshshfpb=nEl9f9zYYVAJ6%20JVSY3EhqQ%3D%3D; shshshfpa=8455c840-b96a-1556-eba0-cc96b5cf47b6-1581992010; pre_seq=2; pt_key=app_openAAJey4-EADDDl1ZOPldaBFGPwAC1Q4TgXt-uCkmElrW1E-Q2L8Z8v8swdRvvJGQathIel7pzdgI; sid=fdeb69ac3803b6cda4c1d8acd0ca4b4w; __jda=122270672.15896822787641340345762.1589682278.1590293348.1590398854.7; __jdv=122270672%7Ckong%7Ct_35460321_%7Ctuiguang%7C72fc343003794a5d81c8fd5927c51d4e%7C1587882752000; __jdb=122270672.2.15896822787641340345762|7.1590398854; mba_sid=71.3; pre_session=867092042012392-5885e9fcd3c1|69; shshshfp=1ee6c2b62777c51ce04557de59a1efb3; shshshsID=c4f7179cc4f924899308de9069085349_2_1590398910441; __jd_ref_cls=Babel_dev_adv_elfin; mba_muid=15896822787641340345762.71.1590398929269[[{\"taskId\":35,\"itemId\":\"1\",\"safeStr\":\"{\\\"is_trust\\\":true,\\\"sign\\\":\\\"D76E4A7C27A866B277494430FD30BC0A6172C953BE6C7976C40DAF9F10AF4E79\\\",\\\"fpb\\\":\\\"nEl9f9zYYVAJ6 JVSY3EhqQ==\\\",\\\"time\\\":1590398928487,\\\"encrypt\\\":4,\\\"nonstr\\\":\\\"PjQLnxb6ti\\\",\\\"info\\\":\\\"\\\",\\\"token\\\":\\\"\\\",\\\"appid\\\":\\\"50072\\\",\\\"sceneid\\\":\\\"goldElfinBtn\\\",\\\"secretp\\\":\\\"Vl4IStM3QydXJKE38Wr7YB6e33c\\\",\\\"buttonid\\\":\\\"goldElfin\\\",\\\"uid\\\":\\\"867092042012392-5885e9fcd3c1\\\"}\"}]]";
        log.info(String.valueOf(cookie.length()));
        //        String[] strings = parseCookie(cookie);
//        log.info(cookie);
//        log.info(strings[0]);
//        log.info(strings[1]);
//        JDApi jdApi = new JDApi(null, null);
//        jdApi.getHomeData("ccc", cookie, false);
//        jdApi.collectScore("ccc", cookie);
    }

    /**
     * cookie 里存了cookie/req body
     */
    private static String[] parseCookie(String cookie) {
        String[] strings = new String[2];
        Pattern pattern = Pattern.compile("(.*?)\\[\\[");
        Matcher matcher = pattern.matcher(cookie);
        if (matcher.find()) {
            strings[0] = matcher.group(1);
        }
        pattern = Pattern.compile("\\[\\[(.*?)]]");
        matcher = pattern.matcher(cookie);
        if (matcher.find()) {
            strings[1] = matcher.group(1);
        }
        return strings;
    }

    /**
     * 收集金币
     *
     * @param cookie 拼接cookie
     */
    public String collectScore(String openId, String cookie) {
//        String cookie = apiConfig.getCakeConfig().getCookie();
        HashMap<String, Object> headers = new HashMap<>();
        String[] parseCookie = parseCookie(cookie);
        headers.put("cookie", parseCookie[0]);
        headers.put("User-Agent",
            "jdapp;android;8.5.12;10;867092042012392-5885e9fcd3c1;network/wifi;model/RMX1991;addressid/2082865096;aid/637bd8ae1589f566;oaid/;osVer/29;appBuild/73078;psn/867092042012392-5885e9fcd3c1|66;psq/6;uid/867092042012392-5885e9fcd3c1;adk/;ads/;pap/JA2015_311210|8.5.12|ANDROID 10;osv/10;pv/68.7;jdv/0|kong|t_35460321_|tuiguang|72fc343003794a5d81c8fd5927c51d4e|1587882752;ref/com.jingdong.app.mall.home.JDHomeFragment;partner/oppo;apprpd/Home_Main;Mozilla/5.0 (Linux; Android 10; RMX1991 Build/QKQ1.191201.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.126 MQQBrowser/6.2 TBS/045136 Mobile Safari/537.36");
        HashMap<String, Object> params = new HashMap<>();
        params.put("functionId", "cakebaker_ckCollectScore");
        params.put("body", parseCookie[1]);
        params.put("client", "wh5");
        params.put("clientVersion", "1.0.0");
        String resp = HttpUtil.doPost(url_cakebaker_ckCollectScore, headers, params, String.class);
        CollectCakeResp respObj = JSONObject.parseObject(resp, CollectCakeResp.class);
        String result;
        if (respObj.getCode() == 0 && respObj.getData().getBizCode() == 0) {
            result = String.format("#### 收集金币成功, 数量: [%s] ####", respObj.getData().getResult().getScore());
            log.info(result);
        } else {
            result = String.format("#### 收集金币失败, code: %s ####", respObj.getCode());
            log.error(result);
            cloudFeignClient.pushErrorMsg(openId, resp);
        }
        reqLogService.addLog(PlatformEnum.JD_CAKE.getCode(), openId, result, resp);
        return result;
    }

    /**
     * 我的金币
     *
     * @param openId   用户id
     * @param addToLog 添加到日志
     */
    public String getHomeData(String openId, String cookie, boolean addToLog) {
        String[] parseCookie = parseCookie(cookie);
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("cookie", parseCookie[0]);
        HashMap<String, Object> params = new HashMap<>();
        params.put("functionId", "cakebaker_getHomeData");
        params.put("body", "{}");
        params.put("client", "wh5");
        params.put("clientVersion", "1.0.0");
        String resp = HttpUtil.doPost(url_cakebaker_getHomeData, headers, params, String.class);
        HomeDataResp respObj = JSONObject.parseObject(resp, HomeDataResp.class);
        String result;
        if (respObj.getCode() == 0 && respObj.getData().getBizCode() == 0) {
            RaiseInfoBean raiseInfo = respObj.getData().getResult().getCakeBakerInfo().getRaiseInfo();
            result = String.format("#### 当前等级  : %s ####", raiseInfo.getScoreLevel());
            result += "\n";
            result += String.format("#### 当前金币数: %s ####", raiseInfo.getRemainScore());
            result += "\n";
            result += String.format("#### 升级还差  : %s ####",
                Math.max(raiseInfo.getNextLevelScore() - raiseInfo.getRemainScore(), 0));
            log.info(result);
        } else {
            result = String.format("#### 叠蛋糕获取信息失败, code: %s ####", respObj.getCode());
            log.error(result);
        }
        if (addToLog) {
            reqLogService.addLog(PlatformEnum.JD_CAKE.getCode(), openId, result, resp);
        }
        return result;
    }

    public String countCollectScore(String openId, Date date) {
        List<ReqLog> reqLogs = reqLogService.getByPlatformTypeAndUser(PlatformEnum.JD_CAKE.getCode(), openId, date);
        if (reqLogs.isEmpty()) {
            return "没有数据";
        }
        Pattern pattern = Pattern.compile(".*\\[(\\d+)].*");
        Matcher matcher;
        int count = 0;
        for (ReqLog item : reqLogs) {
            matcher = pattern.matcher(item.getMessage());
            if (matcher.find()) {
                count += Integer.parseInt(matcher.group(1));
            }
        }
        String result = String.format("#### 执行次数: %s ####", reqLogs.size());
        result += "\n";
        result += String.format("#### 总计领取  : %s ####", count);
        return result;
    }
}
