package com.cjh.base.job;

import com.cjh.base.api.FarmApi;
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
    private FarmApi farmApi;

    @Value("${farm.enable}")
    private Boolean enable;

    @Scheduled(cron = "${farm.job.test}")
    public void test() {
        System.out.println("test job: " + new Date());
    }

    @Scheduled(cron = "${farm.job.gotThreeMealForFarm}")
    public void gotThreeMealForFarm() {
        if (enable) {
            farmApi.gotThreeMealForFarm();
        }
    }

    @Scheduled(cron = "${farm.job.signForFarm}")
    public void signForFarm() {
        if (enable) {
            farmApi.signForFarm();
        }
    }

    @Scheduled(cron = "${farm.job.waterGoodForFarm}")
    public void waterGoodForFarm() {
        if (enable) {
            farmApi.waterGoodForFarm();
        }
    }

    @Scheduled(cron = "${farm.job.firstWaterTaskForFarm}")
    public void firstWaterTaskForFarm() {
        if (enable) {
            farmApi.firstWaterTaskForFarm();
        }
    }

}
