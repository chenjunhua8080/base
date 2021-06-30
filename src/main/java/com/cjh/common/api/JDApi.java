package com.cjh.common.api;

import com.alibaba.fastjson.JSONObject;
import com.cjh.common.entity.ReqLog;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.feign.CloudFeignClient;
import com.cjh.common.resp.CollectCakeResp;
import com.cjh.common.resp.DogEnergyCollectResp;
import com.cjh.common.resp.DogFeedPetsResp;
import com.cjh.common.resp.DogFeedPetsResp.ResultBean.PetPlaceInfoListBean;
import com.cjh.common.resp.DogSignResp;
import com.cjh.common.resp.DogSingleShopResp;
import com.cjh.common.resp.DogThreeMealResp;
import com.cjh.common.resp.HomeDataResp;
import com.cjh.common.resp.HomeDataResp.DataBean.ResultBean.CakeBakerInfoBean.RaiseInfoBean;
import com.cjh.common.service.ReqLogService;
import com.cjh.common.util.HttpUtil;
import com.cjh.common.util.JsonUtil;
import com.google.common.collect.Maps;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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
    /**
     * 签到
     */
    private final String dog_url_getSignReward = "https://api.m.jd.com/client.action?functionId=getSignReward&clientVersion=10.0.5&build=88681&client=android&d_brand=realme&d_model=RMX1991&osVersion=10&screen=2264*1080&partner=oppo&oaid=&openudid=637bd8ae1589f566&eid=eidA2c4481233es2cDODdE0oQDGZAsqjipfs6nAzhOSbOtWyvbmq6irkk3V DBzs8bnZ4XBot0EvrytRfYqmEiF9aQYIV4R2JEYZIQ mxPHZbZWdOR4x&sdkVersion=29&lang=zh_CN&uuid=637bd8ae1589f566&aid=637bd8ae1589f566&area=19_1601_3634_63211&networkType=wifi&wifiBssid=13fa56423c251d614f0d3f559c636a56&uts=0f31TVRjBSsQX3eoNIdoeWWO3nNynz9FG4raKjdBGRk9iWKxxkbW1yvdxBgz8+pnwKnRsvkn1nDkql3s3KUZa0/N06BQVsq7XGfGX9TrJfHMd1xjDyEgOrqHc/vE70ebzVcUYCmNyv4eCSOHweUR7AI2MevtZ4S13/XS7RCWTKlhBnUniInFXnNni4zHbmoRbzHGST/sRMvEVaU5uY7nWA==&uemps=0-0&st=1625048498832&sign=c5cee9b6f199d3e7124228e4ed04e992&sv=112";
    /**
     * 喂食
     */
    private final String dog_url_feedPets = "https://api.m.jd.com/client.action?functionId=feedPets&clientVersion=10.0.5&build=88681&client=android&d_brand=realme&d_model=RMX1991&osVersion=10&screen=2264*1080&partner=oppo&oaid=&openudid=637bd8ae1589f566&eid=eidA2c4481233es2cDODdE0oQDGZAsqjipfs6nAzhOSbOtWyvbmq6irkk3V DBzs8bnZ4XBot0EvrytRfYqmEiF9aQYIV4R2JEYZIQ mxPHZbZWdOR4x&sdkVersion=29&lang=zh_CN&uuid=637bd8ae1589f566&aid=637bd8ae1589f566&area=19_1601_3634_63211&networkType=wifi&wifiBssid=13fa56423c251d614f0d3f559c636a56&uts=0f31TVRjBSsQX3eoNIdoeWWO3nNynz9FG4raKjdBGRk9iWKxxkbW1yvdxBgz8+pnwKnRsvkn1nDkql3s3KUZa0/N06BQVsq7XGfGX9TrJfHMd1xjDyEgOrqHc/vE70ebzVcUYCmNyv4eCSOHweUR7AI2MevtZ4S13/XS7RCWTKlhBnUniInFXnNni4zHbmoRbzHGST/sRMvEVaU5uY7nWA==&uemps=0-0&st=1625047124477&sign=47e92c0427581bf1bf1034e8a4526a98&sv=101";
    /**
     * 收集
     */
    private final String dog_url_energyCollect = "https://api.m.jd.com/client.action?functionId=energyCollect&clientVersion=10.0.5&build=88681&client=android&d_brand=realme&d_model=RMX1991&osVersion=10&screen=2264*1080&partner=oppo&oaid=&openudid=637bd8ae1589f566&eid=eidA2c4481233es2cDODdE0oQDGZAsqjipfs6nAzhOSbOtWyvbmq6irkk3V DBzs8bnZ4XBot0EvrytRfYqmEiF9aQYIV4R2JEYZIQ mxPHZbZWdOR4x&sdkVersion=29&lang=zh_CN&uuid=637bd8ae1589f566&aid=637bd8ae1589f566&area=19_1601_3634_63211&networkType=wifi&wifiBssid=13fa56423c251d614f0d3f559c636a56&uts=0f31TVRjBSsQX3eoNIdoeWWO3nNynz9FG4raKjdBGRk9iWKxxkbW1yvdxBgz8+pnwKnRsvkn1nDkql3s3KUZa0/N06BQVsq7XGfGX9TrJfHMd1xjDyEgOrqHc/vE70ebzVcUYCmNyv4eCSOHweUR7AI2MevtZ4S13/XS7RCWTKlhBnUniInFXnNni4zHbmoRbzHGST/sRMvEVaU5uY7nWA==&uemps=0-0&st=1625047994928&sign=54dcc29c1e31c18b884235275990e6eb&sv=112";
    /**
     * 三餐
     */
    private final String dog_url_getThreeMealReward = "https://api.m.jd.com/client.action?functionId=getThreeMealReward&clientVersion=10.0.5&build=88681&client=android&d_brand=realme&d_model=RMX1991&osVersion=10&screen=2264*1080&partner=oppo&oaid=&openudid=637bd8ae1589f566&eid=eidA2c4481233es2cDODdE0oQDGZAsqjipfs6nAzhOSbOtWyvbmq6irkk3V DBzs8bnZ4XBot0EvrytRfYqmEiF9aQYIV4R2JEYZIQ mxPHZbZWdOR4x&sdkVersion=29&lang=zh_CN&uuid=637bd8ae1589f566&aid=637bd8ae1589f566&area=19_1601_3634_63211&networkType=wifi&wifiBssid=13fa56423c251d614f0d3f559c636a56&uts=0f31TVRjBSsQX3eoNIdoeWWO3nNynz9FG4raKjdBGRk9iWKxxkbW1yvdxBgz8+pnwKnRsvkn1nDkql3s3KUZa0/N06BQVsq7XGfGX9TrJfHMd1xjDyEgOrqHc/vE70ebzVcUYCmNyv4eCSOHweUR7AI2MevtZ4S13/XS7RCWTKlhBnUniInFXnNni4zHbmoRbzHGST/sRMvEVaU5uY7nWA==&uemps=0-0&st=1625048354119&sign=717f2596b6e8d41080e92e3e2abe8f83&sv=122";
    /**
     * 首次喂食奖励
     */
    private final String dog_url_getFirstFeedReward = "https://api.m.jd.com/client.action?functionId=getFirstFeedReward&clientVersion=10.0.5&build=88681&client=android&d_brand=realme&d_model=RMX1991&osVersion=10&screen=2264*1080&partner=oppo&oaid=&openudid=637bd8ae1589f566&eid=eidA2c4481233es2cDODdE0oQDGZAsqjipfs6nAzhOSbOtWyvbmq6irkk3V DBzs8bnZ4XBot0EvrytRfYqmEiF9aQYIV4R2JEYZIQ mxPHZbZWdOR4x&sdkVersion=29&lang=zh_CN&uuid=637bd8ae1589f566&aid=637bd8ae1589f566&area=19_1601_3634_63211&networkType=wifi&wifiBssid=13fa56423c251d614f0d3f559c636a56&uts=0f31TVRjBSsQX3eoNIdoeWWO3nNynz9FG4raKjdBGRk9iWKxxkbW1yvdxBgz8+pnwKnRsvkn1nDkql3s3KUZa0/N06BQVsq7XGfGX9TrJfHMd1xjDyEgOrqHc/vE70ebzVcUYCmNyv4eCSOHweUR7AI2MevtZ4S13/XS7RCWTKlhBnUniInFXnNni4zHbmoRbzHGST/sRMvEVaU5uY7nWA==&uemps=0-0&st=1625048633098&sign=dbce3f22e1c1083936f92bbbc8201dc8&sv=112";


    private ReqLogService reqLogService;
    private CloudFeignClient cloudFeignClient;

    //################################### 宠物 ###########################################

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

        int i = 13 / 5 + 1;
        int j = 13 % 5;
        System.out.println(i);
        System.out.println(j);

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

    /**
     * 收集金币
     *
     * @param cookie 拼接cookie
     */
    public void collectScore(String openId, String cookie) {
//        String cookie = apiConfig.getCakeConfig().getCookie();
        HashMap<String, Object> headers = new HashMap<>();
        String[] parseCookie = parseCookie(cookie);
        headers.put("cookie", parseCookie[0]);
        headers.put("xxx-User-Agent",
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
    }

    /**
     * 宠物签到
     *
     * @param openId 公众号openId
     * @param cookie 用户绑定cookie
     */
    public void getSignReward(String openId, String cookie) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("cookie", cookie);
        headers.put("xxx-User-Agent",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        HashMap<String, Object> params = new HashMap<>();
        params.put("body","{}");
        String json = HttpUtil.doPost(dog_url_getSignReward, headers, params, String.class);
        DogSignResp resp = JsonUtil.json2java(json, DogSignResp.class);
        String result = "";
        if (resp.isSuccess()) {
            result += String.format("#### 签到成功, 狗粮 +[%s]    \n", resp.getResult().getSignReward());
            result += String.format("     签到天数: [%s]          \n", resp.getResult().getSignDay());
            result += String.format("     当前狗粮: [%s]      ####", resp.getResult().getFoodAmount());
            log.info(result);
        } else {
            result = String.format("#### 签到失败, %s ####", resp.getErrorMsg());
            log.error(result);
            cloudFeignClient.pushErrorMsg(openId, result);
        }
        reqLogService.addLog(PlatformEnum.JD_PETS.getCode(), openId, result, json);
    }

    /**
     * 喂食宠物
     *
     * @param openId 公众号openId
     * @param cookie 用户绑定cookie
     */
    public void feedPets(String openId, String cookie) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("cookie", cookie);
        headers.put("xxx-User-Agent",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        Map<String, Object> params = Maps.newHashMap();
        params.put("body","{\"version\":1}");
        String json = HttpUtil.doPost(dog_url_feedPets, headers, params, String.class);
        DogFeedPetsResp resp = JsonUtil.json2java(json, DogFeedPetsResp.class);
        String result = "";
        String[] energyResult = {""};
        List<PetPlaceInfoListBean> energyList = null;
        if (resp.isSuccess()) {
            result += String.format("#### 喂食成功, 狗粮 -[%s]    \n", resp.getResult().getFeedAmount());
            energyList = resp.getResult().getPetPlaceInfoList();
            energyList.removeIf(item -> {
                if (item.getEnergy() != 0) {
                    energyResult[0] += String.format("     能量 +[%s], 卡槽: [%s]  \n", item.getEnergy(), item.getPlace());
                    return false;
                } else {
                    return true;
                }
            });
            result += energyResult[0];
            result += String.format("     当前狗粮: [%s]      ####", resp.getResult().getFoodAmount());
            log.info(result);
        } else {
            result = String.format("#### 喂食失败, %s ####", resp.getErrorMsg());
            log.error(result);
            cloudFeignClient.pushErrorMsg(openId, result);
        }
        reqLogService.addLog(PlatformEnum.JD_PETS.getCode(), openId, result, json);

        //调用收集
        if (energyList != null) {
            energyList.forEach(item -> energyCollect(openId, cookie, item));
        }
    }

    /**
     * 收集能量
     *
     * @param openId 公众号openId
     * @param cookie 用户绑定cookie
     * @param energy 能量
     */
    public void energyCollect(String openId, String cookie, PetPlaceInfoListBean energy) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("cookie", cookie);
        headers.put("xxx-User-Agent",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        HashMap<String, Object> params = new HashMap<>();
        params.put("body","{\"place\":1}");
        String json = HttpUtil.doPost(dog_url_energyCollect, headers, params, String.class);
        DogEnergyCollectResp resp = JsonUtil.json2java(json, DogEnergyCollectResp.class);
        String result = "";
        if (resp.isSuccess()) {
            result += String.format("#### 收集能量成功, 能量 +[%s] \n", energy.getEnergy());
            result += String.format("     当前进度: [%s]          \n", resp.getResult().getMedalPercent());
            result += String.format("     升级还需: [%s]      ####", resp.getResult().getNeedCollectEnergy());
            log.info(result);
        } else {
            result = String.format("#### 收集能量失败, %s ####", resp.getErrorMsg());
            log.error(result);
            cloudFeignClient.pushErrorMsg(openId, result);
        }
        reqLogService.addLog(PlatformEnum.JD_PETS.getCode(), openId, result, json);
    }

    /**
     * 收集能量
     *
     * @param cookie 用户绑定cookie
     * @param energy 能量
     * @return NeedCollectEnergy
     */
    public int energyCollect2(String cookie, PetPlaceInfoListBean energy) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("body","{\"place\":1}");
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("cookie", cookie);
        headers.put("xxx-User-Agent",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        String json = HttpUtil.doPost(dog_url_energyCollect, headers, params, String.class);
        DogEnergyCollectResp resp = JsonUtil.json2java(json, DogEnergyCollectResp.class);
        if (resp.isSuccess()) {
            return resp.getResult().getNeedCollectEnergy();
        }
        return -1;
    }

    /**
     * 连续喂食
     */
    @SneakyThrows
    public String continuousFeed(Integer count, String openId, String cookie) {
        int success = 0;
        int needFeed = 0;
        int foodAmount = 0;
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("cookie", cookie);
        headers.put("xxx-User-Agent",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        DogFeedPetsResp resp = null;
        List<PetPlaceInfoListBean> energyList;
        Map<String, Object> params = Maps.newHashMap();
        params.put("body","{\"version\":1}");
        if (count <= 5) {
            int whileCount = count;
            while (whileCount > 0) {
                Thread.sleep(3000);
                String json = HttpUtil.doPost(dog_url_feedPets, headers, params, String.class);
                resp = JsonUtil.json2java(json, DogFeedPetsResp.class);
                if (resp.isSuccess()) {
                    success++;
                }
                whileCount--;
            }
            foodAmount = resp.getResult().getFoodAmount();
            energyList = resp.getResult().getPetPlaceInfoList();
            //调用收集
            if (energyList != null) {
                for (PetPlaceInfoListBean item : energyList) {
                    if (item.getEnergy() != 0) {
                        Thread.sleep(1000);
                        needFeed = energyCollect2(cookie, item);
                    }
                }
            }
        } else {
            int whileCount = count / 5 + 1;
            int whileLastCount = count % 5;
            while (whileCount > 0) {
                int itemCount;
                if (whileCount == 1) {
                    itemCount = whileLastCount;
                } else {
                    itemCount = 5;
                }
                while (itemCount > 0) {
                    Thread.sleep(3000);
                    String json = HttpUtil.doPost(dog_url_feedPets, headers, params, String.class);
                    resp = JsonUtil.json2java(json, DogFeedPetsResp.class);
                    if (resp.isSuccess()) {
                        success++;
                    }
                    itemCount--;
                }
                energyList = resp.getResult().getPetPlaceInfoList();
                //调用收集
                if (energyList != null) {
                    for (PetPlaceInfoListBean item : energyList) {
                        if (item.getEnergy() != 0) {
                            Thread.sleep(1000);
                            needFeed = energyCollect2(cookie, item);
                        }
                    }
                }
                whileCount--;
            }
        }
        String msg = String.format("连续喂食%s次，成功%s次，还需%s，可用%s", count, success, needFeed, foodAmount);
        reqLogService.addLog(PlatformEnum.JD_PETS.getCode(), openId, msg, null);
        return msg;
    }

    /**
     * 三餐
     *
     * @param openId 公众号openId
     * @param cookie 用户绑定cookie
     */
    public void getThreeMeal(String openId, String cookie) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("cookie", cookie);
        headers.put("xxx-User-Agent",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        HashMap<String, Object> params = new HashMap<>();
        params.put("body","{}");
        String json = HttpUtil.doPost(dog_url_getThreeMealReward, headers, params, String.class);
        DogThreeMealResp resp = JsonUtil.json2java(json, DogThreeMealResp.class);
        String result = "";
        if (resp.isSuccess()) {
            result += String.format("#### 三餐领取成功, 狗粮 +[%s]   \n", resp.getResult().getThreeMealReward());
            result += String.format("     当前狗粮: [%s]         ####", resp.getResult().getFoodAmount());
            log.info(result);
        } else {
            result = String.format("#### 三餐领取失败, %s ####", resp.getErrorMsg());
            log.error(result);
            cloudFeignClient.pushErrorMsg(openId, result);
        }
        reqLogService.addLog(PlatformEnum.JD_PETS.getCode(), openId, result, json);
    }

    /**
     * 首次喂食奖励
     *
     * @param openId 公众号openId
     * @param cookie 用户绑定cookie
     */
    public void getFirstFeedReward(String openId, String cookie) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("cookie", cookie);
        headers.put("xxx-User-Agent",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        HashMap<String, Object> params = new HashMap<>();
        params.put("body","{}");
        String json = HttpUtil.doPost(dog_url_getFirstFeedReward, headers, params, String.class);
        DogSingleShopResp resp = JsonUtil.json2java(json, DogSingleShopResp.class);
        String result = "";
        if (resp.isSuccess()) {
            result += String.format("#### 首次喂食奖励, 狗粮 +[%s]     \n", resp.getResult().getReward());
            result += String.format("     当前狗粮: [%s]           ####", resp.getResult().getFoodAmount());
            log.info(result);
        } else {
            result = String.format("#### 首次喂食奖励领取失败, %s ####", resp.getErrorMsg());
            log.error(result);
            cloudFeignClient.pushErrorMsg(openId, result);
        }
        reqLogService.addLog(PlatformEnum.JD_PETS.getCode(), openId, result, json);
    }
}
