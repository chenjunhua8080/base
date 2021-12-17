package com.cjh.common.job;

import com.cjh.common.api.TongchengApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.dao.UserDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.po.UserPO;
import com.cjh.common.redis.RedisService;
import com.cjh.common.util.XxlJobUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 同程 - 定时任务
 *
 * @author cjh
 * @date 2021/12/14
 */
@Component
@Slf4j
public class TongchengJob {

    @Autowired
    private TongchengApi tongchengApi;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BindFarmDao bindFarmDao;
    @Autowired
    private RedisService redisService;

    /**
     * 刷新防止过期？
     */
    @XxlJob("job.tongcheng.getSignInfo")
    public void getSignInfo() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[同程里程 - 获取签到信息] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                tongchengApi.getSignInfo(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[同程里程 - 获取签到信息] 结束 ####");
    }

    @XxlJob("job.tongcheng.sign")
    public void sign() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[同程里程 - 签到] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                tongchengApi.sign(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[同程里程 - 签到] 结束 ####");
    }

    @XxlJob("job.tongcheng.waterSign")
    public void waterSign() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[同程许愿树 - 签到] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                tongchengApi.waterSign(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[同程许愿树 - 签到] 结束 ####");
    }

    @XxlJob("job.tongcheng.waterTree")
    public void waterTree() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[同程许愿树 - 浇水] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                tongchengApi.waterTree(user.getOpenId(), bindFarm.getCookie());
                String waterTreeCount = redisService.get("waterTree:" + user.getOpenId());
                if (waterTreeCount == null) {
                    waterTreeCount = "1";
                    redisService.setToday("waterTree:" + user.getOpenId(), waterTreeCount);
                } else {
                    waterTreeCount = String.valueOf(Integer.parseInt(waterTreeCount) + 1);
                    redisService.updateValue("waterTree:" + user.getOpenId(), waterTreeCount);
                }
            }
        }
        XxlJobHelper.log("#### 定时任务[同程许愿树 - 浇水] 结束 ####");
    }

    @XxlJob("job.tongcheng.getAutoRestoreWater")
    public void getAutoRestoreWater() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[同程许愿树 - 领取恢复水滴] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                tongchengApi.getAutoRestoreWater(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[同程许愿树 - 领取恢复水滴] 结束 ####");
    }

    @XxlJob("job.tongcheng.getTaskAward1")
    public void getTaskAward() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[同程许愿树 - 领取任务水滴] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                String task1Num = redisService.get("getTaskAward1:" + user.getOpenId());
                if (task1Num == null) {
                    task1Num = "1";
                    redisService.setToday("getTaskAward1:" + user.getOpenId(), task1Num);
                } else {
                    task1Num = String.valueOf(Integer.parseInt(task1Num) + 1);
                    redisService.updateValue("getTaskAward1:" + user.getOpenId(), task1Num);
                }
                tongchengApi.getTaskAward(user.getOpenId(), bindFarm.getCookie(), "T10" + task1Num);
            }
        }
        XxlJobHelper.log("#### 定时任务[同程许愿树 - 领取任务水滴] 结束 ####");
    }

    @XxlJob("job.tongcheng.getTaskAward2")
    public void getTaskAward2() throws InterruptedException {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[同程许愿树 - 领取浇水奖励] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                String waterTreeCount = redisService.get("waterTree:" + user.getOpenId());
                XxlJobHelper.log("今日共浇水：" + waterTreeCount);
                tongchengApi.getTaskAward(user.getOpenId(), bindFarm.getCookie(), "T201");
//                    if (waterTreeCount != null) {
//                        for (int i = 0; i < waterTreeCount; i++) {
//                            tongchengApi.getTaskAward(user.getOpenId(), bindFarm.getCookie(), "T20" + i);
//                            TimeUnit.SECONDS.sleep(5);
//                        }
//                    }
            }
        }
        XxlJobHelper.log("#### 定时任务[同程许愿树 - 领取浇水奖励] 结束 ####");
    }

}
