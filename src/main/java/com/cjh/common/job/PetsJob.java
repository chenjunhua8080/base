package com.cjh.common.job;

import com.cjh.common.api.ApiConfig;
import com.cjh.common.api.PetsApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.dao.UserDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.po.UserPO;
import com.cjh.common.util.DateUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 京东宠物定时任务
 *
 * @author cjh
 * @date 2020/5/24
 */
@AllArgsConstructor
@EnableScheduling
@Component
@Slf4j
public class PetsJob {

    private PetsApi petsApi;
    private ApiConfig apiConfig;
    private UserDao userDao;
    private BindFarmDao bindFarmDao;

    //    @Scheduled(cron = "${job.pets.getSignReward}")
    @XxlJob("job.pets.getSignReward")
    public void getSignReward() {
        if (apiConfig.getPetsConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            XxlJobHelper.log("#### 定时任务[京东宠物 - 签到] 开始: {} ####", DateUtil.format(new Date()));
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
                if (bindFarmPO != null) {
                    XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                    petsApi.getSignReward(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            XxlJobHelper.log("#### 定时任务[京东宠物 - 签到] 结束: {} ####", DateUtil.format(new Date()));
        }
    }

    //    @Scheduled(cron = "${job.pets.feedPets}")
    @XxlJob("job.pets.feedPets")
    public void feedPets() {
        if (apiConfig.getPetsConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            XxlJobHelper.log("#### 定时任务[京东宠物 - 喂食] 开始: {} ####", DateUtil.format(new Date()));
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
                if (bindFarmPO != null) {
                    XxlJobHelper.log("#### 用户: {} ####", user.getName());
                    petsApi.feedPets(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            XxlJobHelper.log("#### 定时任务[京东宠物 - 喂食] 结束: {} ####", DateUtil.format(new Date()));
        }
    }

    @XxlJob("job.pets.firstFeedReward")
    public void firstFeedReward() {
        if (apiConfig.getPetsConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            XxlJobHelper.log("#### 定时任务[京东宠物 - 首喂奖励] 开始: {} ####", DateUtil.format(new Date()));
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
                if (bindFarmPO != null) {
                    XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                    petsApi.getFirstFeedReward(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            XxlJobHelper.log("#### 定时任务[京东宠物 - 首喂奖励] 结束: {} ####", DateUtil.format(new Date()));
        }
    }

    //    @Scheduled(cron = "${job.pets.getThreeMeal}")
    @XxlJob("job.pets.getThreeMeal")
    public void getThreeMeal() {
        if (apiConfig.getPetsConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            XxlJobHelper.log("#### 定时任务[京东宠物 - 三餐] 开始: {} ####", DateUtil.format(new Date()));
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
                if (bindFarmPO != null) {
                    XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                    petsApi.getThreeMeal(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            XxlJobHelper.log("#### 定时任务[京东宠物 - 三餐] 结束: {} ####", DateUtil.format(new Date()));
        }
    }

    //    @Scheduled(cron = "${job.pets.browseExec}")
    @XxlJob("job.pets.browseExec")
    public void browseExec() throws InterruptedException {
        if (apiConfig.getPetsConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            XxlJobHelper.log("#### 定时任务[京东宠物 - 浏览任务] 开始: {} ####", DateUtil.format(new Date()));
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
                if (bindFarmPO != null) {
                    XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                    petsApi.browseExec(user.getOpenId(), bindFarmPO.getCookie(), 1);
                }
            }
            XxlJobHelper.log("#### 定时任务[京东宠物 - 浏览任务] 结束: {} ####", DateUtil.format(new Date()));
        }
    }

}
