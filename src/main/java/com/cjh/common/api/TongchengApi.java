package com.cjh.common.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.feign.CloudFeignClient;
import com.cjh.common.req.tongcheng.TongchengCookie;
import com.cjh.common.req.tongcheng.TongchengSignParam;
import com.cjh.common.req.tongcheng.TongchengWaterParam;
import com.cjh.common.resp.tongcheng.GetAutoRestoreWaterResp;
import com.cjh.common.resp.tongcheng.GetTaskAward;
import com.cjh.common.resp.tongcheng.SignResp;
import com.cjh.common.resp.tongcheng.SignResp.DataBean;
import com.cjh.common.resp.tongcheng.SignResp.DataBean.PrizesBean;
import com.cjh.common.resp.tongcheng.WaterSignResp;
import com.cjh.common.resp.tongcheng.WaterTreeResp;
import com.cjh.common.service.ReqLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TongchengApi {


    /**
     * @name 签到
     * @result {"rspCode":0,"message":"签到成功","data":{"isShowTenTimesWin":false,"memberGrade":1,"isOpenSignNotify":false,"signDay":1,"isBlackGoldCard":false,"blackGoldCardUrl":"/page/pubBusiness/views/webview_black/webview_black?needwrap=1\u0026src=https%3A%2F%2Fwx.17u.cn%2Fblackwhale%3Fchannelcode%3D8fe0e029f29d4eae96e6df63cc4cd550%26refid%3D766637729","awardRatio":1,"continuedSignDays":1,"totalSignDays":3,"activityUrl":null,"isNewUserFirstWeek":false,"cash":null,"prizes":[{"amount":"4","amountDesc":"里程","type":2,"isNewUserFirstWeek":false}]}}
     */
    private static final String URL_SIGN = "https://wx.17u.cn/wcsign/sign/SaveSignInfoNew";

    /**
     * @name 水滴-签到
     */
    private static final String URL_WATER_SIGN = "https://wx.17u.cn/wcsign/sign/ReceiveWater";

    /**
     * @name 水滴-浇水
     */
    private static final String URL_WATER_WATER_TREE = "https://sgame.moxigame.cn/planttree_tc/game/local/waterTree";

    /**
     * @name 水滴-获取自动恢复水
     */
    private static final String URL_WATER_GET_AUTO_RESTORE_WATER = "https://sgame.moxigame.cn/planttree_tc//game/local/fetchAutoRestoreWater";

    /**
     * @name 水滴-获取任务奖励
     */
    private static final String URL_WATER_GET_TASK_AWARD = "https://sgame.moxigame.cn/planttree_tc//game/local/getTaskAward";


    @Autowired
    private ReqLogService reqLogService;
    @Autowired
    private CloudFeignClient cloudFeignClient;

    /**
     * cookie 里存了 {@link TongchengCookie}
     */
    private static TongchengCookie parseCookie(String cookie) {
        return JSON.parseObject(cookie, TongchengCookie.class);
    }

    /**
     * 签到
     */
    public String sign(String openId, String cookie) {
        TongchengCookie param = parseCookie(cookie);
        HttpRequest request = new HttpRequest(URL_SIGN);
        JSONObject json = (JSONObject) JSON.toJSON(new TongchengSignParam(param.getUnionId(), param.getOpenId()));
        json.put("userIcon",
            "https://file.40017.cn/img140017cnproduct/touch/pushcode/qiandao/2020a/icon_defaultheader.png");
        json.put("nickName", "匿名好友");
        json.put("taskSharerId", "");
        request.body(json.toJSONString());
        HttpResponse response = getResp(request, true, cookie, true);
        String respBody = response.body();
        SignResp resp = JSON.parseObject(respBody, SignResp.class);
        String result;
        if (resp.getRspCode() == 0) {
            DataBean data = resp.getData();
            PrizesBean prizes = data.getPrizes().get(0);
            result = String.format("#### 里程-签到 成功, 签到%s天, 获得里程: %s ####", data.getSignDay(), prizes.getAmount());
            log.info(result);
        } else {
            result = String.format("#### 里程-签到 失败, %s ####", resp.getMessage());
            log.error(result);
            cloudFeignClient.pushErrorMsg(openId, result);
        }
        reqLogService.addLog(PlatformEnum.TONGCHENG.getCode(), openId, result, respBody);
        return result;
    }

    /**
     * 水滴-签到
     */
    public String waterSign(String openId, String cookie) {
        TongchengCookie param = parseCookie(cookie);
        HttpRequest request = new HttpRequest(URL_WATER_SIGN);
        request.body(JSON.toJSONString(new TongchengWaterParam(param.getUnionId(), param.getOpenId())));
        HttpResponse response = getResp(request, true, cookie, true);
        String respBody = response.body();
        WaterSignResp resp = JSON.parseObject(respBody, WaterSignResp.class);
        String result;
        if (resp.getRspCode() == 0) {
            WaterSignResp.DataBean data = resp.getData();
            result = String.format("#### 水滴-签到 成功, 获得水滴: %s ####", data.getWaterNum());
            log.info(result);
        } else {
            result = String.format("#### 水滴-签到 失败, %s ####", resp.getMessage());
            log.error(result);
            cloudFeignClient.pushErrorMsg(openId, result);
        }
        reqLogService.addLog(PlatformEnum.TONGCHENG.getCode(), openId, result, respBody);
        return result;
    }

    /**
     * 水滴-浇水
     */
    public String waterTree(String openId, String cookie) {
        TongchengCookie param = parseCookie(cookie);
        HttpRequest request = new HttpRequest(URL_WATER_WATER_TREE);
        request.cookie(param.getCookie());
        request.body(JSON.toJSONString(new TongchengWaterParam(param.getUnionId(), param.getOpenId())));
        HttpResponse response = getResp(request, false, null, false);
        String respBody = response.body();
        WaterTreeResp resp = JSON.parseObject(respBody, WaterTreeResp.class);
        String result;
        if (resp.getCode() == 0) {
            result = String.format("#### 水滴-浇水 成功, 总次数: %s, 可用: %s ####",
                resp.getPlantInfo().getTotalWaterCount(), resp.getItems().getK1());
            log.info(result);
        } else {
            result = String.format("#### 水滴-浇水 失败, %s ####", resp.getCode());
            log.error(result);
        }
        reqLogService.addLog(PlatformEnum.TONGCHENG.getCode(), openId, result, respBody);
        return result;
    }


    /**
     * 水滴-获取自动恢复水
     */
    public String getAutoRestoreWater(String openId, String cookie) {
        TongchengCookie param = parseCookie(cookie);
        HttpRequest request = new HttpRequest(URL_WATER_GET_AUTO_RESTORE_WATER);
        request.cookie(param.getCookie());
        JSONObject json = (JSONObject) JSON.toJSON(new TongchengWaterParam(param.getGameId(), param.getToken()));
        json.put("waterNum", "10");
        request.body(json.toJSONString());
        HttpResponse response = getResp(request, false, null, false);
        String respBody = response.body();
        GetAutoRestoreWaterResp resp = JSON.parseObject(respBody, GetAutoRestoreWaterResp.class);
        String result;
        if (resp.getCode() == 0) {
            result = String.format("#### 水滴-获取自动恢复水 成功, 获得水滴: %s ####", resp.getAutoRestoreWater().getWaterNum());
            log.info(result);
        } else {
            result = String.format("#### 水滴-获取自动恢复水 失败, %s ####", resp.getErrMsg());
            log.error(result);
        }
        reqLogService.addLog(PlatformEnum.TONGCHENG.getCode(), openId, result, respBody);
        return result;
    }

    /**
     * 水滴-获取任务奖励
     */
    public String getTaskAward(String openId, String cookie, String taskId) {
        TongchengCookie param = parseCookie(cookie);
        HttpRequest request = new HttpRequest(URL_WATER_GET_TASK_AWARD);
        request.cookie(param.getCookie());
        JSONObject json = (JSONObject) JSON.toJSON(new TongchengWaterParam(param.getGameId(), param.getToken()));
        json.put("taskId", taskId);
        request.body(json.toJSONString());
        HttpResponse response = getResp(request, false, null, false);
        String respBody = response.body();
        GetTaskAward resp = JSON.parseObject(respBody, GetTaskAward.class);
        String result;
        if (resp.getCode() == 0) {
            String waterNum = resp.getAwardItems().get(0);
            result = String.format("#### 水滴-获取任务(%s)奖励 成功, 获得水滴: %s ####", taskId, waterNum);
            log.info(result);
        } else {
            result = String.format("#### 水滴-获取任务(%s)奖励 失败, %s ####", taskId, resp.getErrMsg());
            log.error(result);
        }
        reqLogService.addLog(PlatformEnum.TONGCHENG.getCode(), openId, result, respBody);
        return result;
    }

    /**
     * 公共head
     * <p>
     * Host: wx.17u.cn
     * Connection: keep-alive
     * TCReferer: page%2FAC%2Fsign%2Fmsindex%2Fmsindex
     * TCSecTk: ZfOeS2YX9IStsHx-3-C4uwGbHNV1okjW5XYrqEPEf0sEn2BQ3zuvDQkFIeLZDYqLdoAvLsi6pmc_DMiPLbcnRrU56vvZ_b8JL9VyY9rjxkowdszFN6wTw4jePfJ6bufs9j6Lv0MI6uUd3fBiM500JaCzGemYO0B1Wl4ZgjuE6eNA4FgyoBmLm4wcZiMZHszTIz8ti7oEwDtWCMVlYY5bMQ**4641
     * apmat: o498X0Zcbr5AvQoatOpUe3m9Gyh8
     * content-type: application/json
     * sectoken: ZfOeS2YX9IStsHx-3-C4uwGbHNV1okjW5XYrqEPEf0sEn2BQ3zuvDQkFIeLZDYqLdoAvLsi6pmc_DMiPLbcnRrU56vvZ_b8JL9VyY9rjxkowdszFN6wTw4jePfJ6bufs9j6Lv0MI6uUd3fBiM500JaCzGemYO0B1Wl4ZgjuE6eNA4FgyoBmLm4wcZiMZHszTIz8ti7oEwDtWCMVlYY5bMQ**4641
     */
    private void addBaseHeads(HttpRequest request, String cookie) {
        TongchengCookie param = parseCookie(cookie);
        request.header("Host", param.getHost());
        request.header("Connection", "keep-alive");
        request.header("TCReferer", param.getTCReferer());
        request.header("TCSecTk", param.getTCSecTk());
        request.header("apmat", param.getApmat());
        request.header("content-type", "application/json");
        request.header("sectoken", param.getSectoken());
    }

    /**
     * execute http
     */
    private HttpResponse getResp(HttpRequest request, boolean addBaseHeads, String cookie, boolean showLog) {
        if (addBaseHeads) {
            addBaseHeads(request, cookie);
        }
        HttpResponse response = request.execute();
        if (showLog) {
            log.info(String.valueOf(request));
            log.info(String.valueOf(response));
        }
        return response;
    }

}
