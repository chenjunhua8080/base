package com.cjh.common.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.resp.FarmResp;
import com.cjh.common.resp.FarmSignV13Resp;
import com.cjh.common.resp.TreeInfo;
import com.cjh.common.resp.TreeInfo.FarmUserProBean;
import com.cjh.common.service.ReqLogService;
import com.cjh.common.util.HttpUtil;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class FarmApi {

    /**
     * @name 果树信息
     * @result {"code":"0","rightUpResouces":{"advertId":"8901471930","name":"右上角icon","appImage":"https://m.360buyimg.com/babel/jfs/t1/177415/27/975/9297/60854349Ed718f3ea/6c2c6f9eb5822d48.png","appLink":"https://pro.m.jd.com/mall/active/2YXNAz7Qo6TcFpEe8xmAAgLoP5uE/index.html","cxyImage":"https://m.360buyimg.com/babel/jfs/t1/177415/27/975/9297/60854349Ed718f3ea/6c2c6f9eb5822d48.png","cxyLink":"https://pro.m.jd.com/mall/active/2YXNAz7Qo6TcFpEe8xmAAgLoP5uE/index.html","type":"","openLink":false},"turntableInit":{"timeState":1},"showExchangeGuidance":false,"iosShieldConfig":null,"waterGiftSurpriseIconLink":null,"mengchongResouce":{"advertId":"9101430311","name":"左下角鸭子icon放萌宠","appImage":"https://m.360buyimg.com/babel/jfs/t1/145092/37/18785/14059/5fd9f2bcEd3d080f7/57784b97834ebcb8.gif","appLink":"openapp.jdmobile://virtual?params=%7B%22category%22:%22jump%22,%22des%22:%22jdreactcommon%22,%22modulename%22:%22JDReactJDBeansReward%22,%22appname%22:%22JDReactJDBeansReward%22,%22param%22:%7B%22page%22:%22JDReactJDBeansReward%22,%22source%22:%22nongchang%22,%22transparentenable%22:true%7D%7D","cxyImage":"https://m.360buyimg.com/babel/jfs/t1/131693/31/14707/19791/5fa26b24Ecca399c2/9af237b61179aebe.png","cxyLink":"https://pro.m.jd.com/mall/active/3Vk9JgtDckmYZGNXPFVBmJgK3D25/index.html","type":"","openLink":false},"clockInGotWater":true,"isOpenOldRemind":false,"guidPopupTask":{"guidPopupTask":"none"},"toFruitEnergy":50,"couponSurpriseIconLink":null,"statisticsTimes":null,"sysTime":1621407712659,"canHongbaoContineUse":false,"toFlowTimes":20,"iosConfigResouces":{"advertId":"2201386920","name":"天天红包","appImage":"https://m.360buyimg.com/babel/jfs/t1/151651/10/2228/274712/5f86c61fE9a0910e1/eb8c94fde6864768.gif","appLink":"https://carry.m.jd.com/babelDiy/Zeus/CvMVbdFGXPiWFFPCc934RiJfMPu/index.html?babelChannel=ttt1","cxyImage":"","cxyLink":"","type":"","openLink":false},"todayGotWaterGoalTask":{"canPop":false},"oldUserState":1,"leftUpResouces":{"advertId":"0501486949","name":"左上角icon","appImage":"https://m.360buyimg.com/babel/jfs/t1/180953/12/4823/17488/60a39cedE7a84dce5/ba0242f34ff25aa2.gif","appLink":"https://pro.m.jd.com/mall/active/3phLRPvjA2BuHqkPSMpwAURbzfW6/index.html","cxyImage":"https://m.360buyimg.com/babel/jfs/t1/180953/12/4823/17488/60a39cedE7a84dce5/ba0242f34ff25aa2.gif","cxyLink":"https://pro.m.jd.com/mall/active/3phLRPvjA2BuHqkPSMpwAURbzfW6/index.html","type":"","openLink":false},"minSupportAPPVersion":"8.5.4","lowFreqStatus":0,"funCollectionHasLimit":false,"message":null,"treeState":1,"rightDownResouces":{"advertId":"1601491737","name":"风车","appImage":"https://m.360buyimg.com/babel/jfs/t1/175738/30/9562/5879/609e4922Eec3f2492/d74a1c64e27afab7.gif","appLink":"https://pro.m.jd.com/mall/active/gkB3YtTdacWMdtiBJWothC6xPLw/index.html","cxyImage":"https://m.360buyimg.com/babel/jfs/t1/175738/30/9562/5879/609e4922Eec3f2492/d74a1c64e27afab7.gif","cxyLink":"https://pro.m.jd.com/mall/active/gkB3YtTdacWMdtiBJWothC6xPLw/index.html","type":"","openLink":false},"iconFirstPurchaseInit":false,"toFlowEnergy":40,"farmUserPro":{"totalEnergy":14610,"treeState":1,"groupSkuId":"10190616-53136309235","createTime":1566261803000,"treeEnergy":3510,"treeTotalEnergy":4000,"shareCode":"6048af3e3a72494d917371864fc72354","winTimes":1,"nickName":"俊华大哥","imageUrl":"http://storage.360buyimg.com/i.imageUpload/6a645f3535623336333438336635623131353534363131333935353139_mid.jpg","couponKey":"vender_BA#396a3ffd9ac840eb8c78d406bb779ca1","type":"usbfengshan","simpleName":"USB风扇","name":"USB电风扇","goodsImage":"https://m.360buyimg.com/babel/jfs/t1/190163/26/2460/128965/6098e9e8Eb26d2623/f7bd3b532a4ca269.jpg","skuId":"10030646612419","lastLoginDate":1621394781000,"newOldState":4,"oldMarkComplete":1,"commonState":31,"prizeLevel":2},"retainPopupLimit":1,"toBeginEnergy":30,"collectPopWindow":{"windowType":1,"type":0,"userLevel":-1},"leftDownResouces":null,"enableSign":false,"loadFriend":{"code":"0","statisticsTimes":null,"sysTime":1621407712653,"message":null,"firstAddUser":false},"hadCompleteXgTask":false,"groupGiftSurpriseIconLink":null,"oldUserIntervalTimes":[7,7,14,14,21],"toFruitTimes":59,"oldUserSendWater":["108","128","108","128","108","128","108","128","108","128"]}
     */
    private final String url_tree_info = "https://api.m.jd.com/client.action?functionId=initForFarm&body=%7B%22PATH%22%3A%221%22%2C%22ptag%22%3A%22138567.7.59%22%2C%22navStart%22%3A%222021-05-19T06%3A49%3A20.103Z%22%2C%22referer%22%3A%22http%3A%2F%2Fwq.jd.com%2Fwxapp%2Fpages%2Findex%2Findex%22%2C%22originUrl%22%3A%22%2Fpages%2Fgarden_new%2Fpages%2Findex%2Findex%3FPATH%3D1%22%2C%22originParams%22%3A%7B%22ptag%22%3A%22138567.7.59%22%7D%2C%22originOpts%22%3A%7B%7D%2C%22imageUrl%22%3A%22%22%2C%22nickName%22%3A%22%22%2C%22version%22%3A12%2C%22channel%22%3A2%7D&appid=wh5&loginType=1&loginWQBiz=ddnc";
    private final String url_tree_info_v13 = "https://api.m.jd.com/client.action?functionId=initForFarm&body=%7B%22babelChannel%22%3A%22121%22%2C%22lng%22%3A%22113.274035%22%2C%22lat%22%3A%2223.09512%22%2C%22sid%22%3A%22a700ae8bbf15f7df5e73ea6843636few%22%2C%22un_area%22%3A%2219_1601_3634_63211%22%2C%22version%22%3A13%2C%22channel%22%3A1%7D&appid=wh5";

    /**
     * @name 签到
     * @result {"amount":20,"code":"0","todayGotWaterGoalTask":{"canPop":false},"statisticsTimes":null,"signDay":1,"sysTime":1575614306624,"message":null}
     */
    private final String url_signForFarm = "https://api.m.jd.com/client.action?functionId=signForFarm&body=%7B%22type%22%3A0%2C%22version%22%3A2%2C%22channel%22%3A2%7D&appid=wh5&loginType=1&loginWQBiz=ddnc";
    /**
     * @name 签到v13
     * @result {"amount":20,"code":"0","todayGotWaterGoalTask":{"canPop":false},"statisticsTimes":null,"signDay":1,"sysTime":1575614306624,"message":null}
     */
    private final String url_signForFarm_v13 = "https://api.m.jd.com/client.action?functionId=clockInForFarm&body=%7B%22type%22%3A1%2C%22version%22%3A13%2C%22channel%22%3A1%7D&appid=wh5";
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
    private final String url_gotThreeMealForFarm_v13 = "https://api.m.jd.com/client.action?functionId=gotThreeMealForFarm&body=%7B%22version%22%3A13%2C%22channel%22%3A1%7D&appid=wh5";
    /**
     * @name 浇水
     * @result {"code":"0","totalEnergy":43,"statisticsTimes":null,"sysTime":1575624467484,"finished":false,"message":null,"waterStatus":0,"sendAmount":10,"treeEnergy":660}
     */
    private final String url_waterGoodForFarm = "https://api.m.jd.com/client.action?functionId=waterGoodForFarm&body=%7B%22type%22%3A%22%22%2C%22version%22%3A13%2C%22channel%22%3A1%7D&appid=wh5";
    /**
     * @name 领首浇
     * @result {"amount":10,"code":"0","todayGotWaterGoalTask":{"canPop":false},"statisticsTimes":null,"sysTime":1575624600601,"message":null}
     */
    private final String url_firstWaterTaskForFarm = "https://api.m.jd.com/client.action?functionId=firstWaterTaskForFarm&body=%7B%22version%22%3A13%2C%22channel%22%3A1%7D&appid=wh5";

    /**
     * @name 回归/新用户水滴
     * @result {"code":"0","farmUserPro":{"totalEnergy":14897,"treeState":1,"groupSkuId":"10190616-53136309235","createTime":1566261803000,"treeEnergy":3910,"treeTotalEnergy":4000,"shareCode":"6048af3e3a72494d917371864fc72354","winTimes":1,"nickName":"俊华大哥","imageUrl":"http://storage.360buyimg.com/i.imageUpload/6a645f3535623336333438336635623131353534363131333935353139_mid.jpg","couponKey":"vender_BA#396a3ffd9ac840eb8c78d406bb779ca1","type":"usbfengshan","simpleName":"USB风扇","name":"USB电风扇","goodsImage":"https://m.360buyimg.com/babel/jfs/t1/190163/26/2460/128965/6098e9e8Eb26d2623/f7bd3b532a4ca269.jpg","skuId":"10030646612419","lastLoginDate":1625038076000,"newOldState":6,"oldMarkComplete":1,"commonState":31,"prizeLevel":2},"todayGotWaterGoalTask":{"getTodayGotWaterGoalTask":{"icon":"https://m.360buyimg.com/babel/jfs/t1/186081/16/10612/181675/60d2d993Eb70d6aab/910ee4b284c3d051.png","goalWater":120,"sendWater":5,"upMainTitle":"继续去逛逛农场","upSecondTitle":"为你送上5g水滴","link":"/pages/garden_new/pages/index/index?PATH=45","vendorid":"1","linkType":"pages","ghidUsername":"gh_45b306365c3d"},"canPop":true},"statisticsTimes":null,"addEnergy":128,"sysTime":1625045940877,"message":null,"type":2}
     */
    private final String url_gotNewUserTaskForFarm = "https://api.m.jd.com/client.action?functionId=gotNewUserTaskForFarm&body=%7B%22version%22%3A13%2C%22channel%22%3A1%7D&appid=wh5";



    private ReqLogService reqLogService;

    /**
     * 果树信息
     */
    public TreeInfo getTreeInfo(String openId, String cookie) {
        String resp = HttpUtil.doGet(url_tree_info, cookie);
        TreeInfo treeInfo = JSONObject.parseObject(resp, TreeInfo.class);
        String result;
        if (treeInfo.getCode() == 0) {
            FarmUserProBean farmUserPro = treeInfo.getFarmUserPro();
            result = String
                .format("#### 获取果树信息成功, %s-%s ####", farmUserPro.getTreeTotalEnergy(), farmUserPro.getTreeEnergy());
            log.info(result);
        } else {
            result = String.format("#### 获取果树信息失败, %s ####", treeInfo.getCode());
            log.error(resp);
        }
//        reqLogService.addLog(PlatformEnum.JD_FARM.getCode(), openId, result, resp);
        return treeInfo;
    }
    /**
     * 果树信息v13
     */
    public TreeInfo getTreeInfoV13(String openId, String cookie) {
        String resp = HttpUtil.doGet(url_tree_info_v13, cookie);
        TreeInfo treeInfo = JSONObject.parseObject(resp, TreeInfo.class);
        String result;
        if (treeInfo.getCode() == 0) {
            FarmUserProBean farmUserPro = treeInfo.getFarmUserPro();
            result = String
                .format("#### 获取果树信息成功, %s-%s ####", farmUserPro.getTreeTotalEnergy(), farmUserPro.getTreeEnergy());
            log.info(result);
        } else {
            result = String.format("#### 获取果树信息失败, %s ####", treeInfo.getCode());
            log.error(resp);
        }
//        reqLogService.addLog(PlatformEnum.JD_FARM.getCode(), openId, result, resp);
        return treeInfo;
    }

    /**
     * 签到
     */
    public String signForFarm(String openId, String cookie) {
        String resp = HttpUtil.doGet(url_signForFarm, cookie);
        FarmResp farmResp = JSONObject.parseObject(resp, FarmResp.class);
        String result;
        if (farmResp.getCode() == 0) {
            result = String.format("#### 签到成功, 获得水滴: %s ####", farmResp.getAmount());
            log.info(result);
        } else {
            result = String.format("#### 签到失败, code: %s ####", farmResp.getCode());
            log.error(result);
        }
        reqLogService.addLog(PlatformEnum.JD_FARM.getCode(), openId, result, resp);
        return result;
    }

    /**
     * 签到v13
     */
    public String signForFarmV13(String openId, String cookie) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("cookie",cookie);
        String resp = HttpUtil.doPost(url_signForFarm_v13, headers,null,String.class);
        FarmSignV13Resp signV13Resp = JSONObject.parseObject(resp, FarmSignV13Resp.class);
        String result;
        if (signV13Resp.getCode() == 0) {
            result = String.format("#### 签到成功, 签到%s天, 获得水滴: %s ####", signV13Resp.getSignDay(), signV13Resp.getAmount());
            log.info(result);
        } else {
            result = String.format("#### 签到失败, code: %s ####", signV13Resp.getCode());
            log.error(result);
        }
        reqLogService.addLog(PlatformEnum.JD_FARM.getCode(), openId, result, resp);
        return result;
    }


    /**
     * 浇水
     */
    public String waterGoodForFarm(String openId, String cookie) {
        String resp = HttpUtil.doGet(url_waterGoodForFarm, cookie);
        FarmResp farmResp = JSONObject.parseObject(resp, FarmResp.class);
        String result;
        if (farmResp.getCode() == 0) {
            TreeInfo treeInfo = getTreeInfoV13(openId, cookie);
            FarmUserProBean farmUserPro = treeInfo.getFarmUserPro();
            result = String.format("#### 浇水成功, 还需%s, 可用水滴: %s ####",
                farmResp.getTotalEnergy(), farmUserPro.getTreeTotalEnergy()-farmUserPro.getTreeEnergy());
            log.info(result);
        } else {
            result = String.format("#### 浇水失败, code: %s ####", farmResp.getCode());
            log.error(result);
        }
        reqLogService.addLog(PlatformEnum.JD_FARM.getCode(), openId, result, resp);
        return result;
    }

    /**
     * 连续浇水
     */
    @SneakyThrows
    public String continuousWater(Integer count, String openId, String cookie) {
        TreeInfo treeInfo = getTreeInfoV13(openId, cookie);
        FarmUserProBean farmUserPro = treeInfo.getFarmUserPro();
        String result = String.format("#### 连续浇水开始, 可用水滴: %s ####%n", farmUserPro.getTotalEnergy());
        int success = 0;
        while (count > 0) {
            Thread.sleep(1000);
            String resp = HttpUtil.doGet(url_waterGoodForFarm, cookie);
            FarmResp farmResp = JSON.parseObject(resp, FarmResp.class);
            if (farmResp.getCode() == 0) {
                success++;
            }
            count--;
        }
        treeInfo = getTreeInfoV13(openId, cookie);
        farmUserPro = treeInfo.getFarmUserPro();
        result += String.format("#### 连续浇水结束, 成功%s次, 还需%s, 可用水滴: %s ####",
            success, farmUserPro.getTreeTotalEnergy() - farmUserPro.getTreeEnergy(), farmUserPro.getTotalEnergy());

        reqLogService.addLog(PlatformEnum.JD_FARM.getCode(), openId, result, null);
        return result;
    }

    /**
     * 领取首浇
     */
    public String firstWaterTaskForFarm(String openId, String cookie) {
        String resp = HttpUtil.doGet(url_firstWaterTaskForFarm, cookie);
        FarmResp farmResp = JSONObject.parseObject(resp, FarmResp.class);
        String result;
        if (farmResp.getCode() == 0) {
            result = String.format("#### 领取首浇, 获得水滴: %s ####", farmResp.getAmount());
            log.info(result);
        } else {
            result = String.format("#### 领取首浇失败, code: %s ####", farmResp.getCode());
            log.error(result);
        }
        reqLogService.addLog(PlatformEnum.JD_FARM.getCode(), openId, result, resp);
        return result;
    }

    /**
     * 定时领取
     */
    public String gotThreeMealForFarm(String openId, String cookie) {
        String resp = HttpUtil.doGet(url_gotThreeMealForFarm, cookie);
        FarmResp farmResp = JSONObject.parseObject(resp, FarmResp.class);
        String result;
        if (farmResp.getCode() == 0) {
            result = String.format("#### 定时领取, 获得水滴: %s ####", farmResp.getAmount());
            log.info(result);
        } else {
            result = String.format("#### 定时领取失败, code: %s ####", farmResp.getCode());
            log.error(result);
        }
        reqLogService.addLog(PlatformEnum.JD_FARM.getCode(), openId, result, resp);
        return result;
    }

    /**
     * 定时领取v13
     */
    public String gotThreeMealForFarmV13(String openId, String cookie) {
        String resp = HttpUtil.doGet(url_gotThreeMealForFarm_v13, cookie);
        FarmResp farmResp = JSONObject.parseObject(resp, FarmResp.class);
        String result;
        if (farmResp.getCode() == 0) {
            result = String.format("#### 定时领取, 获得水滴: %s ####", farmResp.getAmount());
            log.info(result);
        } else {
            result = String.format("#### 定时领取失败, code: %s ####", farmResp.getCode());
            log.error(result);
        }
        reqLogService.addLog(PlatformEnum.JD_FARM.getCode(), openId, result, resp);
        return result;
    }
}
