package com.cjh.common.job;

import com.cjh.common.api.ApiConfig;
import com.cjh.common.api.TongchengApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.dao.UserDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.po.UserPO;
import com.cjh.common.redis.RedisService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 同程 - 定时任务
 *
 * @author cjh
 * @date 2021/12/14
 */
@AllArgsConstructor
@EnableScheduling
@Component
@Slf4j
public class TongchengJob {

    private TongchengApi tongchengApi;
    private ApiConfig apiConfig;
    private UserDao userDao;
    private BindFarmDao bindFarmDao;
    private RedisService redisService;

    /**
     * 刷新防止过期？
     */
    @XxlJob("job.tongcheng.getSignInfo")
    public void getSignInfo() {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            XxlJobHelper.log("#### 定时任务[同程里程 - 获取签到信息] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                    tongchengApi.getSignInfo(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            XxlJobHelper.log("#### 定时任务[同程里程 - 获取签到信息] 结束 ####");
        }
    }

    //    @Scheduled(cron = "${job.tongcheng.sign}")
    @XxlJob("job.tongcheng.sign")
    public void sign() {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            XxlJobHelper.log("#### 定时任务[同程里程 - 签到] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                    tongchengApi.sign(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            XxlJobHelper.log("#### 定时任务[同程里程 - 签到] 结束 ####");
        }
    }

    //    @Scheduled(cron = "${job.tongcheng.waterSign}")
    @XxlJob("job.tongcheng.waterSign")
    public void waterSign() {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            XxlJobHelper.log("#### 定时任务[同程许愿树 - 签到] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                    tongchengApi.waterSign(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            XxlJobHelper.log("#### 定时任务[同程许愿树 - 签到] 结束 ####");
        }
    }

    //    @Scheduled(cron = "${job.tongcheng.waterTree}")
    @XxlJob("job.tongcheng.waterTree")
    public void waterTree() {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            XxlJobHelper.log("#### 定时任务[同程许愿树 - 浇水] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                    tongchengApi.waterTree(user.getOpenId(), bindFarmPO.getCookie());
                    Integer waterTreeCount = redisService.get("waterTree:" + user.getOpenId(), Integer.class);
                    if (waterTreeCount == null) {
                        waterTreeCount = 1;
                        redisService.set("waterTree:" + user.getOpenId(), waterTreeCount, 60 * 60 * 23L);
                    } else {
                        waterTreeCount++;
                        redisService.set("waterTree:" + user.getOpenId(), waterTreeCount);
                    }
                }
            }
            XxlJobHelper.log("#### 定时任务[同程许愿树 - 浇水] 结束 ####");
        }
    }

    //    @Scheduled(cron = "${job.tongcheng.getAutoRestoreWater}")
    @XxlJob("job.tongcheng.getAutoRestoreWater")
    public void getAutoRestoreWater() {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            XxlJobHelper.log("#### 定时任务[同程许愿树 - 领取恢复水滴] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                    tongchengApi.getAutoRestoreWater(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            XxlJobHelper.log("#### 定时任务[同程许愿树 - 领取恢复水滴] 结束 ####");
        }
    }

    //    @Scheduled(cron = "${job.tongcheng.getTaskAward1}")
    @XxlJob("job.tongcheng.getTaskAward1")
    public void getTaskAward() {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            XxlJobHelper.log("#### 定时任务[同程许愿树 - 领取任务水滴] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                    Integer task1Num = redisService.get("getTaskAward1:" + user.getOpenId(), Integer.class);
                    if (task1Num == null) {
                        task1Num = 1;
                    } else {
                        task1Num++;
                        redisService.set("getTaskAward1:" + user.getOpenId(), task1Num, 60 * 65L);
                    }
                    tongchengApi.getTaskAward(user.getOpenId(), bindFarmPO.getCookie(), "T10" + task1Num);
                }
            }
            XxlJobHelper.log("#### 定时任务[同程许愿树 - 领取任务水滴] 结束 ####");
        }
    }

    //    @Scheduled(cron = "${job.tongcheng.getTaskAward2}")
    @XxlJob("job.tongcheng.getTaskAward2")
    public void getTaskAward2() throws InterruptedException {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            XxlJobHelper.log("#### 定时任务[同程许愿树 - 领取浇水奖励] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
                    Integer waterTreeCount = redisService.get("waterTree:" + user.getOpenId(), Integer.class);
                    XxlJobHelper.log("今日共浇水：" + waterTreeCount);
                    tongchengApi.getTaskAward(user.getOpenId(), bindFarmPO.getCookie(), "T201");
//                    if (waterTreeCount != null) {
//                        for (int i = 0; i < waterTreeCount; i++) {
//                            tongchengApi.getTaskAward(user.getOpenId(), bindFarmPO.getCookie(), "T20" + i);
//                            TimeUnit.SECONDS.sleep(5);
//                        }
//                    }
                }
            }
            XxlJobHelper.log("#### 定时任务[同程许愿树 - 领取浇水奖励] 结束 ####");
        }
    }

}
