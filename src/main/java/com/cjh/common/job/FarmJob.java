package com.cjh.common.job;

import com.cjh.common.api.FarmApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 农场定时任务
 *
 * @author cjh
 * @date 2020/4/3
 */
@RefreshScope
@Component
@EnableScheduling
public class FarmJob {

    @Autowired
    private FarmApi farmApi;

    @Value("${farm.enable}")
    private Boolean enable;

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
