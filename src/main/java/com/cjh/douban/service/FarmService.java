package com.cjh.douban.service;

import com.alibaba.fastjson.JSONObject;
import com.cjh.douban.resp.FarmResp;
import com.cjh.douban.util.HttpUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@Slf4j
public class FarmService {

    @Autowired
    private FarmLogService farmLogService;

    @Value("${farm.cookie}")
    private String cookie;

    /**
     * @name 签到
     * @result {"amount":20,"code":"0","todayGotWaterGoalTask":{"canPop":false},"statisticsTimes":null,"signDay":1,"sysTime":1575614306624,"message":null}
     */
    private final String url_signForFarm = "https://api.m.jd.com/client.action?functionId=signForFarm&body=%7B%22type%22%3A0%2C%22version%22%3A2%2C%22channel%22%3A2%7D&appid=wh5&loginType=1&loginWQBiz=ddnc";
    /**
     * @name 任务列表
     * @result {"gotThreeMealInit":{"threeMealAmount":"5-15","pos":-1,"f":true,"threeMealTimes":["6-9","11-14","17-21"]},"code":"0","firstWaterInit":{"firstWaterFinished":false,"f":false,"totalWaterTimes":0,"firstWaterEnergy":10},"signInit":{"signEnergyEachAmount":"20","f":false,"signEnergyShared":false,"totalSigned":0,"signEnergyAmounts":["20","20","20","20","20"],"todaySigned":false},"totalWaterTaskInit":{"totalWaterTaskLimit":10,"totalWaterTaskEnergy":15,"f":false,"totalWaterTaskFinished":false,"totalWaterTaskTimes":0},"statisticsTimes":null,"inviteToFarmInit":{"sendEnergyFinishedTimes":0,"inviteToFarmTimes":15,"f":false,"totalEnergy":0,"curInviteToFarmTimes":0,"inviteToFarmFinished":false,"inviteToFarmEnergy":60},"gotBrowseTaskAdInit":{"f":false,"userBrowseTaskAds":[{"advertId":"0066278604","mainTitle":"浏览推荐商品","subTitle":"奖励10g水滴，每天1次","wechatPic":"https://m.360buyimg.com/babel/jfs/t1/64560/13/9630/4130/5d722fdfE1c21e0e5/38da6883faa13d44.png","link":"https://pro.m.jd.com/mall/active/4MDaRCgfSMNWGLcLtFDdaoZqUST/index.html","picurl":"https://m.360buyimg.com/babel/jfs/t1/46086/40/15399/4130/5dc3b4aeEd350e570/40bf70e12f3582ef.png","wechatLink":"https://pro.m.jd.com/mall/active/4MDaRCgfSMNWGLcLtFDdaoZqUST/index.html","wechatMain":"浏览推荐商品","wechatSub":"奖励10g水滴，每天1次","reward":10,"limit":1,"hadGotTimes":0,"hadFinishedTimes":0},{"advertId":"0066278605","mainTitle":"浏览生鲜暖冬好物","subTitle":"奖励10g水滴，每天1次","wechatPic":"https://m.360buyimg.com/babel/jfs/t1/86316/40/4609/19860/5de7904cE6d3071da/9dd7cdd9deaa7996.png","link":"https://prodev.m.jd.com/mall/active/jNQkK3c9EJjxkRPbt5hHxGYLKMz/index.html","picurl":"https://m.360buyimg.com/babel/jfs/t1/101895/25/4565/5943/5de76a41E05c9648e/a80b66686bf446ef.png","wechatLink":"https://wq.jd.com/webportal/event/27436","wechatMain":"浏览限时补贴好货","wechatSub":"奖励10g水滴，每天1次","reward":10,"limit":1,"hadGotTimes":0,"hadFinishedTimes":0}]},"sysTime":1575613656230,"message":null,"waterRainInit":{"lastTime":0,"f":false,"winTimes":0,"config":{"maxLimit":2,"intervalTime":3,"lotteryProb":100,"imgArea":"https://m.360buyimg.com/babel/jfs/t1/95018/25/1064/277315/5db93bf1Ecbbcedc1/85782b18bb05eab2.png","countDown":10,"countOfBomb":0,"minAwardWater":3,"maxAwardWater":12,"bottomImg":"https://m.360buyimg.com/babel/jfs/t1/79941/15/14063/356572/5db93bf7E838db707/8454fe6098c0fca3.png","logo":"","vendorid":"","vendorName":"","key":"","actId":11288,"btnText":"回我的农场","btnLink":""}},"taskOrder":["signInit","firstWaterInit","inviteToFarmInit","waterRainInit","totalWaterTaskInit","gotBrowseTaskAdInit","gotThreeMealInit"]}
     */
    private final String url_taskInitForFarm = "https://api.m.jd.com/client.action?functionId=taskInitForFarm&body=%7B%22version%22%3A2%2C%22channel%22%3A2%7D&appid=wh5&loginType=1&loginWQBiz=ddnc";
    /**
     * @name 定时领
     * @result {"amount":7,"code":"0","todayGotWaterGoalTask":{"canPop":false},"statisticsTimes":null,"sysTime":1575624133156,"message":null}
     */
    private final String url_gotThreeMealForFarm = "https://api.m.jd.com/client.action?functionId=gotThreeMealForFarm&body=%7B%22type%22%3A0%2C%22version%22%3A2%2C%22channel%22%3A2%7D&appid=wh5&loginType=1&loginWQBiz=ddnc";
    /**
     * @name 浇水
     * @result {"code":"0","totalEnergy":43,"statisticsTimes":null,"sysTime":1575624467484,"finished":false,"message":null,"waterStatus":0,"sendAmount":10,"treeEnergy":660}
     */
    private final String url_waterGoodForFarm = "https://api.m.jd.com/client.action?functionId=waterGoodForFarm&body=%7B%22version%22%3A2%2C%22channel%22%3A1%7D&appid=wh5&loginType=1&loginWQBiz=ddnc";
    /**
     * @name 领首浇
     * @result {"amount":10,"code":"0","todayGotWaterGoalTask":{"canPop":false},"statisticsTimes":null,"sysTime":1575624600601,"message":null}
     */
    private final String url_firstWaterTaskForFarm = "https://api.m.jd.com/client.action?functionId=firstWaterTaskForFarm&body=%7B%22version%22%3A2%2C%22channel%22%3A1%7D&appid=wh5&loginType=1&loginWQBiz=ddnc";

    /**
     * 签到
     */
    public String signForFarm() {
        String resp = HttpUtil.doGet(url_signForFarm, cookie);
        FarmResp farmResp = JSONObject.parseObject(resp, FarmResp.class);
        String result;
        if (farmResp.getCode() == 0) {
            result = String.format("#### 签到成功, 获得水滴: %s ####", farmResp.getAmount());
            log.info(result);
        } else {
            result = String.format("#### 签到失败: %s ####", farmResp.getMessage());
            log.error(result);
        }
        farmLogService.addLog(getOpenId(cookie), result, resp);
        return result;
    }

    /**
     * 浇水
     */
    public String waterGoodForFarm() {
        String resp = HttpUtil.doGet(url_waterGoodForFarm, cookie);
        FarmResp farmResp = JSONObject.parseObject(resp, FarmResp.class);
        String result;
        if (farmResp.getCode() == 0) {
            result = String.format("#### 浇水成功, 可用水滴: %s ####", farmResp.getTotalEnergy());
            log.info(result);
        } else {
            result = String.format("#### 浇水失败: %s ####", farmResp.getMessage());
            log.error(result);
        }
        farmLogService.addLog(getOpenId(cookie), result, resp);
        return result;
    }

    /**
     * 领取首浇
     */
    public String firstWaterTaskForFarm() {
        String resp = HttpUtil.doGet(url_firstWaterTaskForFarm, cookie);
        FarmResp farmResp = JSONObject.parseObject(resp, FarmResp.class);
        String result;
        if (farmResp.getCode() == 0) {
            result = String.format("#### 领取首浇, 获得水滴: %s ####", farmResp.getAmount());
            log.info(result);
        } else {
            result = String.format("#### 领取首浇失败: %s ####", farmResp.getMessage());
            log.error(result);
        }
        farmLogService.addLog(getOpenId(cookie), result, resp);
        return result;
    }

    /**
     * 定时领取
     */
    public String gotThreeMealForFarm() {
        String resp = HttpUtil.doGet(url_gotThreeMealForFarm, cookie);
        FarmResp farmResp = JSONObject.parseObject(resp, FarmResp.class);
        String result;
        if (farmResp.getCode() == 0) {
            result = String.format("#### 定时领取, 获得水滴: %s ####", farmResp.getAmount());
            log.info(result);
        } else {
            result = String.format("#### 定时领取失败: %s ####", farmResp.getMessage());
            log.error(result);
        }
        farmLogService.addLog(getOpenId(cookie), result, resp);
        return result;
    }

    /**
     * cookie匹配openId
     */
    private String getOpenId(String cookie) {
        Pattern pattern = Pattern.compile("wxapp_openid=(.*?);");
        Matcher matcher = pattern.matcher(cookie);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            log.error("cookie中为匹配到wxapp_openid...");
        }
        return null;
    }
}
