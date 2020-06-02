package com.cjh.common.job;

import com.cjh.common.api.ApiConfig;
import com.cjh.common.api.JDApi;
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
 * 京东 - 叠蛋糕定时任务
 *
 * @author cjh
 * @date 2020/5/24
 */
@AllArgsConstructor
@EnableScheduling
@Component
@Slf4j
public class JDJob {

    private JDApi jdApi;
    private ApiConfig apiConfig;
    private UserDao userDao;
    private BindFarmDao bindFarmDao;

    @Scheduled(cron = "${job.cake.collectScore}")
    public void collectScore() {
        if (apiConfig.getCakeConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
//            log.info("#### 定时任务[京东 - 叠蛋糕 - 收集金币] 开始: {} ####", DateUtil.format(new Date()));
//            log.info("#### 统计: {} ####", users.size());
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_CAKE.getCode());
                if (bindFarmPO != null) {
                    try {
                        jdApi.collectScore(user.getOpenId(), bindFarmPO.getCookie());
                    } catch (Exception e) {
                        log.info("#### 定时任务[京东 - 叠蛋糕 - 收集金币] 异常:  ####", e);
                    }
                }
            }
//            log.info("#### 定时任务[京东 - 叠蛋糕 - 收集金币] 结束: {} ####", DateUtil.format(new Date()));
        }
    }

    @Scheduled(cron = "${job.cake.getHomeData}")
    public void getHomeData() {
        if (apiConfig.getCakeConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[京东 - 叠蛋糕 - 查询升级] 开始: {} ####", DateUtil.format(new Date()));
            log.info("#### 统计: {} ####", users.size());
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_CAKE.getCode());
                if (bindFarmPO != null) {
                    jdApi.getHomeData(user.getOpenId(), bindFarmPO.getCookie(), true);
                }
            }
            log.info("#### 定时任务[京东 - 叠蛋糕 - 查询升级] 结束: {} ####", DateUtil.format(new Date()));
        }
    }

    //################################# 京东宠物 ####################################

    @Scheduled(cron = "${job.pets.getSignReward}")
    public void getSignReward() {
        if (apiConfig.getPetsConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[京东 - 宠物 - 签到] 开始: {} ####", DateUtil.format(new Date()));
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
                if (bindFarmPO != null) {
                    log.info("#### 用户: {} ####", user.getOpenId());
                    jdApi.getSignReward(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            log.info("#### 定时任务[京东 - 宠物 - 签到] 结束: {} ####", DateUtil.format(new Date()));
        }
    }

    @Scheduled(cron = "${job.pets.feedPets}")
    public void feedPets() {
        if (apiConfig.getPetsConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[京东 - 宠物 - 喂食] 开始: {} ####", DateUtil.format(new Date()));
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
                if (bindFarmPO != null) {
                    log.info("#### 用户: {} ####", user.getOpenId());
                    jdApi.feedPets(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            log.info("#### 定时任务[京东 - 宠物 - 喂食] 结束: {} ####", DateUtil.format(new Date()));
        }
    }

    @Scheduled(cron = "${job.pets.getSingleShop}")
    public void getSingleShop() {
        if (apiConfig.getPetsConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[京东 - 宠物 - 浏览商店] 开始: {} ####", DateUtil.format(new Date()));
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
                if (bindFarmPO != null) {
                    log.info("#### 用户: {} ####", user.getOpenId());
                    jdApi.getSingleShop(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            log.info("#### 定时任务[京东 - 宠物 - 浏览商店] 结束: {} ####", DateUtil.format(new Date()));
        }
    }


    @Scheduled(cron = "${job.pets.getThreeMeal}")
    public void getThreeMeal() {
        if (apiConfig.getPetsConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[京东 - 宠物 - 三餐领取] 开始: {} ####", DateUtil.format(new Date()));
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
                if (bindFarmPO != null) {
                    log.info("#### 用户: {} ####", user.getOpenId());
                    jdApi.getThreeMeal(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            log.info("#### 定时任务[京东 - 宠物 - 三餐领取] 结束: {} ####", DateUtil.format(new Date()));
        }
    }

}
