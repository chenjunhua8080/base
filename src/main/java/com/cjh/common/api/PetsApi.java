package com.cjh.common.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.resp.DogBrowseResp;
import com.cjh.common.resp.DogBrowseResp.ResultBean;
import com.cjh.common.resp.DogEnergyCollectResp;
import com.cjh.common.resp.DogFeedPetsResp;
import com.cjh.common.resp.DogFeedPetsResp.ResultBean.PetPlaceInfoListBean;
import com.cjh.common.resp.DogSignResp;
import com.cjh.common.resp.DogSingleShopResp;
import com.cjh.common.resp.DogThreeMealResp;
import com.cjh.common.service.ReqLogService;
import com.cjh.common.util.HttpUtil;
import com.cjh.common.util.JsonUtil;
import com.cjh.common.util.XxlJobUtil;
import com.xxl.job.core.context.XxlJobHelper;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PetsApi {

    /**
     * 浏览任务
     */
    private static final String browseUrl = "https://api.m.jd.com/client.action?appid=wh5&loginType=1&functionId=getSingleShopReward&loginWQBiz=pet-town&body={\"index\":INDEX,\"version\":1,\"type\":TYPE}";
    /**
     * 签到
     */
    private final String dog_url_getSignReward = "https://api.m.jd.com/client.action?functionId=getSignReward&appid=wh5&loginType=1&loginWQBiz=pet-town";
    /**
     * 喂食
     */
    private final String dog_url_feedPets = "https://api.m.jd.com/client.action?functionId=feedPets&appid=wh5&loginType=1&loginWQBiz=pet-town&body=%7B%22version%22%3A1%7D";
    /**
     * 收集
     */
    private final String dog_url_energyCollect = "https://api.m.jd.com/client.action?functionId=energyCollect&appid=wh5&loginType=1&loginWQBiz=pet-town&body=%7B%22place%22%3AplaceIndex%7D";
    /**
     * 三餐
     */
    private final String dog_url_getThreeMealReward = "https://api.m.jd.com/client.action?functionId=getThreeMealReward&appid=wh5&loginType=1&loginWQBiz=pet-town";
    /**
     * 首次喂食奖励
     */
    private final String dog_url_getFirstFeedReward = "https://api.m.jd.com/client.action?functionId=getFirstFeedReward&appid=wh5&loginType=1&loginWQBiz=pet-town";

    @Autowired
    private ReqLogService reqLogService;

    /**
     * 宠物签到
     *
     * @param openId 公众号openId
     * @param cookie 用户绑定cookie
     */
    public void getSignReward(String openId, String cookie) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("cookie", cookie);
        String json = HttpUtil.doGet(dog_url_getSignReward, headers);
        DogSignResp resp = JsonUtil.json2java(json, DogSignResp.class);
        String result = "";
        if (resp.isSuccess()) {
            result += String.format("#### 签到成功, 狗粮 +[%s]    \n", resp.getResult().getSignReward());
            result += String.format("     签到天数: [%s]          \n", resp.getResult().getSignDay());
            result += String.format("     当前狗粮: [%s]      ####", resp.getResult().getFoodAmount());
            XxlJobHelper.log(result);
        } else {
            result = String.format("#### 签到失败, %s ####", resp.getErrorMsg());
            XxlJobUtil.showErrorLog(result, openId);
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
        String json = HttpUtil.doGet(dog_url_feedPets, headers);
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
            XxlJobHelper.log(result);
        } else {
            result = String.format("#### 喂食失败, %s ####", resp.getErrorMsg());
            XxlJobUtil.showErrorLog(result);
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
        String url = dog_url_energyCollect.replace("placeIndex", String.valueOf(energy.getPlace()));
        String json = HttpUtil.doGet(url, headers);
        DogEnergyCollectResp resp = JsonUtil.json2java(json, DogEnergyCollectResp.class);
        String result = "";
        if (resp.isSuccess()) {
            result += String.format("#### 收集能量成功, 能量 +[%s] \n", energy.getEnergy());
            result += String.format("     当前进度: [%s]          \n", resp.getResult().getMedalPercent());
            result += String.format("     升级还需: [%s]      ####", resp.getResult().getNeedCollectEnergy());
            XxlJobHelper.log(result);
        } else {
            result = String.format("#### 收集能量失败, %s ####", resp.getErrorMsg());
            XxlJobUtil.showErrorLog(result);
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
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("cookie", cookie);
        String url = dog_url_energyCollect.replace("placeIndex", String.valueOf(energy.getPlace()));
        String json = HttpUtil.doGet(url, headers);
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
        DogFeedPetsResp resp;
        List<PetPlaceInfoListBean> energyList;
        if (count <= 5) {
            int whileCount = count;
            while (whileCount > 0) {
                Thread.sleep(3000);
                String json = HttpUtil.doGet(dog_url_feedPets, headers);
                resp = JsonUtil.json2java(json, DogFeedPetsResp.class);
                if (resp.isSuccess()) {
                    success++;
                    foodAmount = resp.getResult().getFoodAmount();
                    //调用收集
                    energyList = resp.getResult().getPetPlaceInfoList();
                    if (energyList != null) {
                        for (PetPlaceInfoListBean item : energyList) {
                            foodAmount = foodAmount + item.getEnergy();
                            if (item.getEnergy() != 0) {
                                Thread.sleep(2000);
                                needFeed = energyCollect2(cookie, item);
                            }
                        }
                    }
                }
                whileCount--;
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
                    String json = HttpUtil.doPost(dog_url_feedPets, headers);
                    resp = JsonUtil.json2java(json, DogFeedPetsResp.class);
                    if (resp.isSuccess()) {
                        success++;
                        foodAmount = resp.getResult().getFoodAmount();
                        //调用收集
                        energyList = resp.getResult().getPetPlaceInfoList();
                        if (energyList != null) {
                            for (PetPlaceInfoListBean item : energyList) {
                                foodAmount = foodAmount + item.getEnergy();
                                if (item.getEnergy() != 0) {
                                    Thread.sleep(2000);
                                    needFeed = energyCollect2(cookie, item);
                                }
                            }
                        }
                    }
                    itemCount--;
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
        String json = HttpUtil.doGet(dog_url_getThreeMealReward, headers);
        DogThreeMealResp resp = JsonUtil.json2java(json, DogThreeMealResp.class);
        String result = "";
        if (resp.isSuccess()) {
            result += String.format("#### 三餐领取成功, 狗粮 +[%s]   \n", resp.getResult().getThreeMealReward());
            result += String.format("     当前狗粮: [%s]         ####", resp.getResult().getFoodAmount());
            XxlJobHelper.log(result);
        } else {
            result = String.format("#### 三餐领取失败, %s ####", resp.getErrorMsg());
            XxlJobUtil.showErrorLog(result);
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
        String json = HttpUtil.doGet(dog_url_getFirstFeedReward, headers);
        DogSingleShopResp resp = JsonUtil.json2java(json, DogSingleShopResp.class);
        String result = "";
        if (resp.isSuccess()) {
            result += String.format("#### 首次喂食奖励, 狗粮 +[%s]     \n", resp.getResult().getReward());
            result += String.format("     当前狗粮: [%s]           ####", resp.getResult().getFoodAmount());
            XxlJobHelper.log(result);
        } else {
            result = String.format("#### 首次喂食奖励领取失败, %s ####", resp.getErrorMsg());
            XxlJobUtil.showErrorLog(result);
        }
        reqLogService.addLog(PlatformEnum.JD_PETS.getCode(), openId, result, json);
    }

    /**
     * 浏览任务
     */
    public String browseExec(String openId, String cookie, Integer index) throws InterruptedException {
        String result;
        if (index > 8) {
            result = "浏览任务数量已达" + index + "，停止继续浏览";
            XxlJobHelper.log(result);
            return result;
        }
        String url = browseUrl.replace("INDEX", index.toString()).replace("TYPE", "1");
        HttpRequest request = new HttpRequest(url);
        request.header("Cookie", cookie);
        HttpResponse httpResponse = request.execute();
        //XxlJobHelper.log(String.valueOf(request));
        //XxlJobHelper.log(String.valueOf(httpResponse));
        String resp = httpResponse.body();
        DogBrowseResp browseResp = JSONObject.parseObject(resp, DogBrowseResp.class);
        ResultBean respResult = browseResp.getResult();
        if ("0".equals(browseResp.getCode()) && respResult.getStatus() == 1) {
            TimeUnit.SECONDS.sleep(10);
            DogBrowseResp browseGiftResp = browseGift(cookie, index);
            if ("0".equals(browseResp.getCode()) && browseGiftResp.getResult().getStatus() == 1) {
                result = String.format("#### 浏览[%s]成功, 领取奖励: %s ####", index, browseGiftResp.getResult().getReward());
                XxlJobHelper.log(result);
                TimeUnit.SECONDS.sleep(10);
                if (browseGiftResp.getResult().getReward() > 0) {
                    browseExec(openId, cookie, ++index);
                }
            } else {
                result = String.format("#### 浏览[%s]成功, 领取失败, %s ####", index, browseGiftResp.getResult().getStatus());
                XxlJobUtil.showErrorLog(result);
            }
        } else {
            result = String.format("#### 浏览[%s]失败, code: %s ####", browseResp.getCode());
            XxlJobUtil.showErrorLog(result);
        }
        reqLogService.addLog(PlatformEnum.JD_FARM.getCode(), openId, result, resp);
        return result;
    }

    /**
     * 浏览任务奖励
     */
    private DogBrowseResp browseGift(String cookie, Integer index) {
        String url = browseUrl.replace("INDEX", index.toString()).replace("TYPE", "2");
        HttpRequest request = new HttpRequest(url);
        request.header("Cookie", cookie);
        HttpResponse httpResponse = request.execute();
        //XxlJobHelper.log(String.valueOf(request));
        //XxlJobHelper.log(String.valueOf(httpResponse));
        String resp = httpResponse.body();
        return JSONObject.parseObject(resp, DogBrowseResp.class);
    }
}
