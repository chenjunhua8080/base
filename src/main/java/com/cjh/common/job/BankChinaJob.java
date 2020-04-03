package com.cjh.common.job;

import com.cjh.common.api.BankChinaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 中银定时任务
 *
 * @author cjh
 * @date 2020/4/3
 */
@RefreshScope
@Component
@EnableScheduling
public class BankChinaJob {

    @Autowired
    private BankChinaApi bankApi;

    @Scheduled(cron = "${bank.job.sign}")
    public void sign() {
        bankApi.sign();
        bankApi.index();
    }

}
