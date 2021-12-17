package com.cjh.common.controller;

import com.cjh.common.api.FarmApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.entity.ReqLog;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.feign.CloudFeignClient;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.service.ReqLogService;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class FarmController {

    private FarmApi farmApi;
    private ReqLogService reqLogService;
    private BindFarmDao bindFarmDao;
    private CloudFeignClient feignClient;

    @GetMapping("/signForFarm")
    public String signForFarm(String openId) {
        BindFarmPO bindFarm = bindFarmDao.selectByOpenId(openId, PlatformEnum.JD_FARM.getCode());
        if (bindFarm == null) {
            return "未绑定";
        }
        return farmApi.signForFarmV13(openId, bindFarm.getCookie());
    }

    @GetMapping("/waterGoodForFarm")
    public String waterGoodForFarm(String openId) {
        BindFarmPO bindFarm = bindFarmDao.selectByOpenId(openId, PlatformEnum.JD_FARM.getCode());
        if (bindFarm == null) {
            return "未绑定";
        }
        return farmApi.waterGoodForFarm(openId, bindFarm.getCookie());
    }

    @GetMapping("/continuousWater")
    public String continuousWater(String openId, Integer count) {
        BindFarmPO bindFarm = bindFarmDao.selectByOpenId(openId, PlatformEnum.JD_FARM.getCode());
        if (bindFarm == null) {
            return "未绑定";
        }
        String result = farmApi.continuousWater(count, openId, bindFarm.getCookie());
        feignClient.tempPush(openId, result);
        return result;
    }

    @GetMapping("/firstWaterTaskForFarm")
    public String firstWaterTaskForFarm(String openId) {
        BindFarmPO bindFarm = bindFarmDao.selectByOpenId(openId, PlatformEnum.JD_FARM.getCode());
        if (bindFarm == null) {
            return "未绑定";
        }
        return farmApi.firstWaterTaskForFarm(openId, bindFarm.getCookie());
    }

    @GetMapping("/gotThreeMealForFarm")
    public String gotThreeMealForFarm(String openId) {
        BindFarmPO bindFarm = bindFarmDao.selectByOpenId(openId, PlatformEnum.JD_FARM.getCode());
        if (bindFarm == null) {
            return "未绑定";
        }
        return farmApi.gotThreeMealForFarmV13(openId, bindFarm.getCookie());
    }


    @GetMapping("/getTodayFarmLog")
    public List<ReqLog> getTodayFarmLog(@RequestParam("openId") String openId, @RequestParam("date") Date date) {
        return reqLogService.getByPlatformTypeAndUser(PlatformEnum.JD_FARM.getCode(), openId, date);
    }

}
