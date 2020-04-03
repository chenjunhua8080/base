package com.cjh.common.job;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TestJob {

    @Value("${test.job.test}")
    private String cron;

    @Scheduled(cron = "${test.job.test}")
    public void test() {
        log.info("test job: {}, cron: {}", new Date(), cron);
    }

}
