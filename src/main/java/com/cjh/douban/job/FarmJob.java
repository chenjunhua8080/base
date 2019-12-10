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

    @Scheduled(cron = "${farm.job.test}")
    public void test() {
        System.out.println("test job: " + new Date());
    }

    @Scheduled(cron = "${farm.job.gotThreeMealForFarm}")
    public void gotThreeMealForFarm() {
        if (enable) {
            farmService.gotThreeMealForFarm();
        }
    }

    @Scheduled(cron = "${farm.job.signForFarm}")
    public void signForFarm() {
        if (enable) {
            farmService.signForFarm();
        }
    }

    @Scheduled(cron = "${farm.job.waterGoodForFarm}")
    public void waterGoodForFarm() {
        if (enable) {
            farmService.waterGoodForFarm();
        }
    }

    @Scheduled(cron = "${farm.job.firstWaterTaskForFarm}")
    public void firstWaterTaskForFarm() {
        if (enable) {
            farmService.firstWaterTaskForFarm();
        }
    }

}
