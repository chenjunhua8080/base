package com.cjh.common.job;

import com.cjh.common.api.BankChinaApi;
import com.cjh.common.util.DateUtil;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 中银定时任务
 *
 * @author cjh
 * @date 2020/4/3
 */
@AllArgsConstructor
@EnableScheduling
@Component
@Slf4j
public class BankChinaJob {

    private BankChinaApi bankApi;

    @Scheduled(cron = "${job.bankChina.sign}")
    public void sign() {
        log.info("#### 定时任务[中银 - 签到] 开始: {} ####", DateUtil.format(new Date()));
        bankApi.sign();
        bankApi.index();
        log.info("#### 定时任务[中银 - 签到] 结束: {} ####", DateUtil.format(new Date()));
    }

}
