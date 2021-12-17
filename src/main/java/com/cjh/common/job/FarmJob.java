package com.cjh.common.job;

import com.cjh.common.api.FarmApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.dao.UserDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.po.UserPO;
import com.cjh.common.util.XxlJobUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 农场定时任务
 *
 * @author cjh
 * @date 2020/4/3
 */
@Component
@Slf4j
public class FarmJob {

    @Autowired
    private FarmApi farmApi;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BindFarmDao bindFarmDao;

    @XxlJob("job.farm.gotThreeMealForFarm")
    public void gotThreeMealForFarm() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[京东农场 - 三餐] 开始 ####");
        XxlJobHelper.log("#### 统计: {} ####", users.size());
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_FARM.getCode());
            if (bindFarm != null) {
                farmApi.gotThreeMealForFarmV13(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[京东农场 - 三餐] 结束 ####");

    }

    @XxlJob("job.farm.signForFarm")
    public void signForFarm() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[京东农场 - 签到] 开始 ####");
        XxlJobHelper.log("#### 统计: {} ####", users.size());
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_FARM.getCode());
            if (bindFarm != null) {
                farmApi.signForFarmV13(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[京东农场 - 签到] 结束 ####");

    }

    @XxlJob("job.farm.waterGoodForFarm")
    public void waterGoodForFarm() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[京东农场 - 浇水] 开始 ####");
        XxlJobHelper.log("#### 统计: {} ####", users.size());
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_FARM.getCode());
            if (bindFarm != null) {
                farmApi.waterGoodForFarm(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[京东农场 - 浇水] 结束 ####");
    }

    @XxlJob("job.farm.firstWaterTaskForFarm")
    public void firstWaterTaskForFarm() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[京东农场 - 首浇奖励] 开始 ####");
        XxlJobHelper.log("#### 统计: {} ####", users.size());
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_FARM.getCode());
            if (bindFarm != null) {
                farmApi.firstWaterTaskForFarm(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[京东农场 - 首浇奖励] 结束 ####");
    }

    @XxlJob("job.farm.task")
    public void task() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[京东农场 - 浏览任务] 开始 ####");
        XxlJobHelper.log("#### 统计: {} ####", users.size());
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.JD_FARM.getCode());
            if (bindFarm != null) {
                farmApi.taskList(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[京东农场 - 浏览任务] 结束 ####");
    }

}
