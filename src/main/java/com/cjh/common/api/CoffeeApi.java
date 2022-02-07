package com.cjh.common.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.req.coffee.CoffeeCookie;
import com.cjh.common.req.coffee.FanclubPostParam;
import com.cjh.common.resp.coffee.FanclubResp;
import com.cjh.common.resp.coffee.FarmCultureResp;
import com.cjh.common.resp.coffee.FarmCultureResp.DataBean;
import com.cjh.common.resp.coffee.FarmCultureResp.DataBean.LandBean.LevelBean;
import com.cjh.common.resp.coffee.FarmCultureResp.DataBean.LandRewardBean;
import com.cjh.common.resp.coffee.FarmCultureResp.DataBean.UserBean;
import com.cjh.common.resp.coffee.FarmIndexResp;
import com.cjh.common.resp.coffee.FarmSignResp;
import com.cjh.common.resp.coffee.FarmTokenResp;
import com.cjh.common.service.ReqLogService;
import com.cjh.common.util.XxlJobUtil;
import com.google.common.collect.Maps;
import com.xxl.job.core.context.XxlJobHelper;
import java.io.File;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CoffeeApi {

    /**
     * 俱乐部上传图片
     */
    private static final String URL_FANCLUB_UPLOAD = "https://dgwestorestage.nestlechinese.com/fanclubcrm/upload/Poster";

    /**
     * 俱乐部上传图片-查看
     */
    private static final String URL_FANCLUB_UPLOAD_VIEW = "https://dgwestore.nestlechinese.com/fanclubimg/SEC";
    /**
     * 俱乐部发帖
     */
    private static final String URL_FANCLUB_POST = "https://dgwestorestage.nestlechinese.com/fanclubcrm/api/Share/add";
    /**
     * 爱豆庄园获取token
     */
    private static final String URL_FARM_AUTH = "https://coffeefarm.shheywow.com/api/user/auth";
    /**
     * 爱豆庄园获取landId
     */
    private static final String URL_FARM_INDEX = "https://coffeefarm.shheywow.com/api/user/index";
    /**
     * 爱豆庄园获取消息
     */
    private static final String URL_FARM_MESSAGE = "https://coffeefarm.shheywow.com/api/user/land/message/get";
    /**
     * 爱豆庄园签到
     */
    private static final String URL_FARM_SIGN = "https://coffeefarm.shheywow.com/api/user/taskv2/sign";
    /**
     * 爱豆庄园培育
     */
    private static final String URL_FARM_CULTURE = "https://coffeefarm.shheywow.com/api/user/land/culture/v2";

    @Autowired
    private ReqLogService reqLogService;

    /**
     * cookie 里存了 {@link CoffeeCookie}
     */
    private static CoffeeCookie parseCookie(String cookie) {
        return JSON.parseObject(cookie, CoffeeCookie.class);
    }

    /**
     * 俱乐部上传图片
     *
     * @return src
     */
    public String fanclubUpload(String openId, File file) {
        HttpRequest request = HttpRequest.post(URL_FANCLUB_UPLOAD);
        HashMap<String, Object> formMap = Maps.newHashMap();
        formMap.put("User", "test");
        formMap.put("file", file);
        request.form(formMap);
        HttpResponse response = request.execute();
        String respBody = response.body();
        String result;
        if (response.getStatus() == 200) {
            result = String.format("#### 俱乐部-上传图片 成功, %s ####", respBody);
            XxlJobHelper.log(result);
            return result;
        } else {
            result = String.format("#### 俱乐部-上传图片 失败, %s ####", respBody);
            XxlJobUtil.showErrorLog(result);
        }
        reqLogService.addLog(PlatformEnum.COFFEE.getCode(), openId, result, respBody);
        return null;
    }

    /**
     * 俱乐部上传图片-查看
     */
    public File fanclubUploadView(String src) {
        HttpRequest request = HttpRequest.get(URL_FANCLUB_UPLOAD_VIEW.replace("src", src));
        HttpResponse response = request.execute();
        String result;
        if (response.getStatus() == 200) {
            File file = new File(src.substring(src.lastIndexOf("/") + 1));
            long bytes = response.writeBody(file);
            result = String.format("#### 俱乐部-上传图片浏览 成功, %s ####", bytes);
            XxlJobHelper.log(result);
            return file;
        } else {
            result = String.format("#### 俱乐部-上传图片浏览 失败, %s ####", response.body());
            XxlJobUtil.showErrorLog(result);
        }
        return null;
    }

    /**
     * 俱乐部发帖
     */
    public void fanclubPost(String openId, String cookie, String src, String context) {
        CoffeeCookie coffeeCookie = parseCookie(cookie);
        HttpRequest request = HttpRequest.get(URL_FANCLUB_POST);
        FanclubPostParam fanclubPostParam = new FanclubPostParam();
        fanclubPostParam.setUnionid(coffeeCookie.getUnionid());
        fanclubPostParam.setImgList(src);
        fanclubPostParam.setContext(context);
        fanclubPostParam.setTopicId(24);
        fanclubPostParam.setSeriesId(0);
        request.body(JSON.toJSONString(fanclubPostParam));
        HttpResponse response = request.execute();
        String respBody = response.body();
        FanclubResp fanclubResp = JSON.parseObject(respBody, FanclubResp.class);
        String result;
        if (response.getStatus() == 200 && fanclubResp.getCode() == 200) {
            result = String.format("#### 俱乐部-发帖 成功, %s ####", fanclubResp.getMessage());
            XxlJobHelper.log(result);
        } else {
            result = String.format("#### 俱乐部-发帖 失败, %s ####", respBody);
            XxlJobUtil.showErrorLog(result, openId);
        }
        reqLogService.addLog(PlatformEnum.COFFEE.getCode(), openId, result, respBody);
    }


    /**
     * 爱豆庄园-获取token
     */
    public String getToken(String openId, String cookie) {
        HttpRequest request = HttpRequest.post(URL_FARM_AUTH);
        request.body(cookie);
        HttpResponse response = request.execute();
        String respBody = response.body();
        FarmTokenResp resp = JSON.parseObject(respBody, FarmTokenResp.class);
        String result;
        if (resp.getErrorCode() == 0) {
            String token = resp.getData().getTokenType() + " " + resp.getData().getToken();
            result = String.format("#### 爱豆庄园-获取token 成功, %s ####", token);
            XxlJobHelper.log(result);
            return token;
        } else {
            result = String.format("#### 爱豆庄园-获取token 失败, %s ####", resp.getErrorMessage());
            reqLogService.addLog(PlatformEnum.COFFEE.getCode(), openId, result, respBody);
            XxlJobUtil.showErrorLog(result, request, response);
        }
        return null;
    }

    /**
     * 爱豆庄园-获取landId
     */
    public Long getLandId(String openId, String token) {
        HttpRequest request = HttpRequest.post(URL_FARM_INDEX);
        request.auth(token);
        HttpResponse response = request.execute();
        String respBody = response.body();
        FarmIndexResp resp = JSON.parseObject(respBody, FarmIndexResp.class);
        String result;
        if (resp.getErrorCode() == 0) {
            String name = resp.getData().getUser().getName();
            Long landId = resp.getData().getLand().getId();
            result = String.format("#### 爱豆庄园-获取用户信息 成功, %s-%s ####", name, landId);
            XxlJobHelper.log(result);
            return landId;
        } else {
            result = String.format("#### 爱豆庄园-获取token 失败, %s ####", resp.getErrorMessage());
            reqLogService.addLog(PlatformEnum.COFFEE.getCode(), openId, result, respBody);
            XxlJobUtil.showErrorLog(result, request, response);
        }
        return null;
    }

    /**
     * 爱豆庄园-获取消息
     */
    public void getMessage(String openId, String token, Long landId) {
        HttpRequest request = HttpRequest.post(URL_FARM_MESSAGE);
        request.auth(token);
        request.body("{\"landId\":" + landId + "}");
        HttpResponse response = request.execute();
        String respBody = response.body();
        FarmTokenResp resp = JSON.parseObject(respBody, FarmTokenResp.class);
        String result;
        if (resp.getErrorCode() == 0) {
            result = String.format("#### 爱豆庄园-获取消息 成功, %s ####", respBody);
            XxlJobHelper.log(result);
        } else {
            result = String.format("#### 爱豆庄园-获取消息 失败, %s ####", resp.getErrorMessage());
            reqLogService.addLog(PlatformEnum.COFFEE.getCode(), openId, result, respBody);
            XxlJobUtil.showErrorLog(result, request, response);
        }
    }

    /**
     * 爱豆庄园-签到
     */
    public void sign(String openId, String token) {
        HttpRequest request = HttpRequest.post(URL_FARM_SIGN);
        request.auth(token);
        HttpResponse response = request.execute();
        String respBody = response.body();
        FarmSignResp resp = JSON.parseObject(respBody, FarmSignResp.class);
        String result;
        if (resp.getErrorCode() == 0) {
            result = String.format("#### 爱豆庄园-签到 成功, %s ####", resp.getData().getUser().getCredit());
            XxlJobHelper.log(result);
        } else {
            result = String.format("#### 爱豆庄园-签到 失败, %s ####", resp.getErrorMessage());
            XxlJobHelper.log(result);
            XxlJobUtil.showErrorLog(result, openId);
        }
        reqLogService.addLog(PlatformEnum.COFFEE.getCode(), openId, result, respBody);
    }


    /**
     * 爱豆庄园-培育
     */
    public void culture(String openId, String token, Long landId) {
        HttpRequest request = HttpRequest.post(URL_FARM_CULTURE);
        request.auth(token);
        request.body("{\"landId\":" + landId + ",\"collectType\":0}");
        HttpResponse response = request.execute();
        String respBody = response.body();
        FarmCultureResp resp = JSON.parseObject(respBody, FarmCultureResp.class);
        String result;
        if (resp.getErrorCode() == 0) {
            DataBean data = resp.getData();
            LandRewardBean landReward = data.getLandReward();
            LevelBean level = data.getLand().getLevel();
            UserBean user = data.getUser();
            result = String.format("#### 爱豆庄园-培育 成功, %s, 共培育%s次, 剩余%s豆值, 触发奖励%s  ####",
                level.getName(), data.getTimes(), user.getCredit(), landReward.getMessage());
            XxlJobHelper.log(result);
        } else {
            result = String.format("#### 爱豆庄园-培育 失败, %s ####", resp.getErrorMessage());
            XxlJobUtil.showErrorLog(result, openId);
        }
        reqLogService.addLog(PlatformEnum.COFFEE.getCode(), openId, result, respBody);
    }

}
