package com.cjh.common.controller;

import com.cjh.common.api.PetsApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.feign.CloudFeignClient;
import com.cjh.common.po.BindFarmPO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class PetsController {

    private PetsApi petsApi;
    private BindFarmDao bindFarmDao;
    private CloudFeignClient feignClient;

    @GetMapping("/continuousFeed")
    public String continuousFeed(@RequestParam("openId") String openId, @RequestParam("count") Integer count) {
        BindFarmPO bindFarm = bindFarmDao.selectByOpenId(openId, PlatformEnum.JD_PETS.getCode());
        if (bindFarm == null) {
            return "未绑定";
        }
        String result = petsApi.continuousFeed(count, openId, bindFarm.getCookie());
        feignClient.tempPush(openId, result);
        return result;
    }

}
