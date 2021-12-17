package com.cjh.common.job;

import com.cjh.common.api.PetsApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.dao.UserDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.po.UserPO;
import com.cjh.common.util.DateUtil;
import com.cjh.common.util.XxlJobUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 京东宠物定时任务
 *
 * @author cjh
 * @date 2020/5/24
 */
@Component
@Slf4j
public class PetsJob {

    @Autowired
    private PetsApi petsApi;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BindFarmDao bindFarmDao;

    @XxlJob("job.pets.getSignReward")
    public void getSignReward() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[京东宠物 - 签到] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                petsApi.getSignReward(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[京东宠物 - 签到] 结束 ####");
    }

    @XxlJob("job.pets.feedPets")
    public void feedPets() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[京东宠物 - 喂食] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getName());
                petsApi.feedPets(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[京东宠物 - 喂食] 结束 ####");
    }

    @XxlJob("job.pets.firstFeedReward")
    public void firstFeedReward() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[京东宠物 - 首喂奖励] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                petsApi.getFirstFeedReward(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[京东宠物 - 首喂奖励] 结束 ####");
    }

    @XxlJob("job.pets.getThreeMeal")
    public void getThreeMeal() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[京东宠物 - 三餐] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                petsApi.getThreeMeal(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[京东宠物 - 三餐] 结束 ####");
    }

    @XxlJob("job.pets.browseExec")
    public void browseExec() throws InterruptedException {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[京东宠物 - 浏览任务] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_PETS.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                petsApi.browseExec(user.getOpenId(), bindFarm.getCookie(), 1);
            }
        }
        XxlJobHelper.log("#### 定时任务[京东宠物 - 浏览任务] 结束 ####");
    }

}
