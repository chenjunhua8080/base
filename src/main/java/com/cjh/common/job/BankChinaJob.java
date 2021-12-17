package com.cjh.common.job;

import com.cjh.common.api.ApiConfig;
import com.cjh.common.api.BankChinaApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.dao.UserDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.po.UserPO;
import com.cjh.common.util.DateUtil;
import java.util.Date;
import java.util.List;
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
//@Component
@Slf4j
@Deprecated
public class BankChinaJob {

    private BankChinaApi bankApi;
    private ApiConfig apiConfig;
    private UserDao userDao;
    private BindFarmDao bindFarmDao;

    @Scheduled(cron = "${job.bankChina.sign}")
    public void sign() {
        if (apiConfig.getBankChinaConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[中银 - 签到] 开始 ####");
            log.info("#### 统计: {} ####", users.size());
            BindFarmPO bindFarm;
            for (UserPO user : users) {
                bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.BANK_CHINA.getCode());
                if (bindFarm != null) {
                    bankApi.sign(user.getOpenId(), bindFarm.getCookie());
                }
            }
            log.info("#### 定时任务[中银 - 签到] 结束 ####");
        }
    }

    /**
     * job.bankChina.index 会报错？
     */
    @Scheduled(cron = "${job.bankChina.reqIndex}")
    public void index() {
        if (apiConfig.getBankChinaConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[中银 - 查询] 开始 ####");
            log.info("#### 统计: {} ####", users.size());
            BindFarmPO bindFarm;
            for (UserPO user : users) {
                bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.BANK_CHINA.getCode());
                if (bindFarm != null) {
                    bankApi.index(user.getOpenId(), bindFarm.getCookie(), true);
                }
            }
            log.info("#### 定时任务[中银 - 查询] 结束 ####");
        }
    }

}
