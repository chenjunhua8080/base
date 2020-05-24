package com.cjh.common.job;

import com.cjh.common.api.ApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * test job
 *
 * @author cjh
 * @date 2020/4/3
 */
@EnableScheduling
@Component
@Slf4j
public class TestJob {

    @Autowired
    private ApiConfig apiConfig;

    @Scheduled(cron = "${job.test.test}")
    public void test() {
        if (apiConfig.getTestConfig().getWorking()) {
            log.info("[----- test job ----]");
        }
    }

}
