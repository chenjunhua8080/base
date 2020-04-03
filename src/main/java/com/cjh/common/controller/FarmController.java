package com.cjh.common.controller;

import com.cjh.common.api.FarmApi;
import com.cjh.common.entity.ReqLog;
import com.cjh.common.service.ReqLogService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class FarmController {

    private FarmApi farmApi;
    private ReqLogService reqLogService;

    @GetMapping("/signForFarm")
    public String signForFarm() {
        return farmApi.signForFarm();
    }

    @GetMapping("/waterGoodForFarm")
    public String waterGoodForFarm() {
        return farmApi.waterGoodForFarm();
    }

    @GetMapping("/firstWaterTaskForFarm")
    public String firstWaterTaskForFarm() {
        return farmApi.firstWaterTaskForFarm();
    }

    @GetMapping("/gotThreeMealForFarm")
    public String gotThreeMealForFarm() {
        return farmApi.gotThreeMealForFarm();
    }


    @GetMapping("/getTodayFarmLog")
    public List<ReqLog> getTodayFarmLog(String openId) {
        return reqLogService.getTodayReqLog(openId);
    }

}
