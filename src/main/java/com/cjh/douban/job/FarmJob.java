package com.cjh.douban.job;

import com.cjh.douban.service.FarmService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@EnableScheduling
public class FarmJob {

    @Autowired
    private FarmService farmService;

    @Value("${farm.enable}")
    private Boolean enable;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void test() {
        System.out.println(new Date());
    }

    @Scheduled(cron = "0 5 9,11,17 * * ?")
    public void gotThreeMealForFarm() {
        if (enable) {
            farmService.gotThreeMealForFarm();
        }
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void signForFarm() {
        if (enable) {
            farmService.signForFarm();
        }
    }

    @Scheduled(cron = "0 1 9 * * ?")
    public void waterGoodForFarm() {
        if (enable) {
            farmService.waterGoodForFarm();
        }
    }

    @Scheduled(cron = "0 2 9 * * ?")
    public void firstWaterTaskForFarm() {
        if (enable) {
            farmService.firstWaterTaskForFarm();
        }
    }

}
