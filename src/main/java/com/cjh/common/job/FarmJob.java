package com.cjh.common.job;

import com.cjh.common.api.ApiConfig;
import com.cjh.common.api.FarmApi;
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
 * 农场定时任务
 *
 * @author cjh
 * @date 2020/4/3
 */
@AllArgsConstructor
@EnableScheduling
@Component
@Slf4j
public class FarmJob {

    private FarmApi farmApi;
    private ApiConfig apiConfig;
    private UserDao userDao;
    private BindFarmDao bindFarmDao;

    @Scheduled(cron = "${job.farm.gotThreeMealForFarm}")
    public void gotThreeMealForFarm() {
        List<UserPO> users = userDao.selectList(null);
        log.info("#### 定时任务[水滴 - 三餐] 开始: {} ####", DateUtil.format(new Date()));
        log.info("#### 统计: {} ####", users.size());
        BindFarmPO bindFarmPO;
        for (UserPO user : users) {
            bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_FARM.getCode());
            if (bindFarmPO != null) {
                farmApi.gotThreeMealForFarmV13(user.getOpenId(), bindFarmPO.getCookie());
            }
        }
        log.info("#### 定时任务[水滴 - 三餐] 结束: {} ####", DateUtil.format(new Date()));

    }

    @Scheduled(cron = "${job.farm.signForFarm}")
    public void signForFarm() {
        List<UserPO> users = userDao.selectList(null);
        log.info("#### 定时任务[水滴 - 签到] 开始: {} ####", DateUtil.format(new Date()));
        log.info("#### 统计: {} ####", users.size());
        BindFarmPO bindFarmPO;
        for (UserPO user : users) {
            bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_FARM.getCode());
            if (bindFarmPO != null) {
                farmApi.signForFarmV13(user.getOpenId(), bindFarmPO.getCookie());
            }
        }
        log.info("#### 定时任务[水滴 - 签到] 结束: {} ####", DateUtil.format(new Date()));

    }

    @Scheduled(cron = "${job.farm.waterGoodForFarm}")
    public void waterGoodForFarm() {
        List<UserPO> users = userDao.selectList(null);
        log.info("#### 定时任务[水滴 - 浇水] 开始: {} ####", DateUtil.format(new Date()));
        log.info("#### 统计: {} ####", users.size());
        BindFarmPO bindFarmPO;
        for (UserPO user : users) {
            bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_FARM.getCode());
            if (bindFarmPO != null) {
                farmApi.waterGoodForFarm(user.getOpenId(), bindFarmPO.getCookie());
            }
        }
        log.info("#### 定时任务[水滴 - 浇水] 结束: {} ####", DateUtil.format(new Date()));
    }

    @Scheduled(cron = "${job.farm.firstWaterTaskForFarm}")
    public void firstWaterTaskForFarm() {
        List<UserPO> users = userDao.selectList(null);
        log.info("#### 定时任务[水滴 - 首浇奖励] 开始: {} ####", DateUtil.format(new Date()));
        log.info("#### 统计: {} ####", users.size());
        BindFarmPO bindFarmPO;
        for (UserPO user : users) {
            bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_FARM.getCode());
            if (bindFarmPO != null) {
                farmApi.firstWaterTaskForFarm(user.getOpenId(), bindFarmPO.getCookie());
            }
        }
        log.info("#### 定时任务[水滴 - 首浇奖励] 结束: {} ####", DateUtil.format(new Date()));
    }

}
