package com.cjh.douban.controller;

import com.cjh.douban.service.FarmLogService;
import com.cjh.douban.service.FarmService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class FarmController {

    private FarmService farmService;
    private FarmLogService farmLogService;

    @GetMapping("/signForFarm")
    public String signForFarm() {
        return farmService.signForFarm();
    }

    @GetMapping("/waterGoodForFarm")
    public String waterGoodForFarm() {
        return farmService.waterGoodForFarm();
    }

    @GetMapping("/firstWaterTaskForFarm")
    public String firstWaterTaskForFarm() {
        return farmService.firstWaterTaskForFarm();
    }

    @GetMapping("/gotThreeMealForFarm")
    public String gotThreeMealForFarm() {
        return farmService.gotThreeMealForFarm();
    }


    @GetMapping("/getTodayFarmLog")
    public String getTodayFarmLog(String openId) {
        return farmLogService.getTodayFarmLog(openId);
    }

}
