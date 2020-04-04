package com.cjh.common.job;

import lombok.extern.slf4j.Slf4j;
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

    @Scheduled(cron = "${job.test.test}")
    public void test() {
        log.info("[----- test job ----]");
    }

}
