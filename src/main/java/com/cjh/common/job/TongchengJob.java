package com.cjh.common.job;

import cn.hutool.core.date.DateUtil;
import com.cjh.common.api.ApiConfig;
import com.cjh.common.api.TongchengApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.dao.UserDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.po.UserPO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(cron = "${job.tongcheng.sign}")
    public void sign() {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[同程 - 里程 - 签到] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    log.info("#### 用户: {} ####", user.getOpenId());
                    tongchengApi.sign(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            log.info("#### 定时任务[同程 - 里程 - 签到] 结束 ####");
        }
    }

    @Scheduled(cron = "${job.tongcheng.waterSign}")
    public void waterSign() {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[同程 - 水滴 - 签到] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    log.info("#### 用户: {} ####", user.getOpenId());
                    tongchengApi.waterSign(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            log.info("#### 定时任务[同程 - 水滴 - 签到] 结束 ####");
        }
    }

    @Scheduled(cron = "${job.tongcheng.waterTree}")
    public void waterTree() {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[同程 - 水滴 - 浇水] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    log.info("#### 用户: {} ####", user.getOpenId());
                    tongchengApi.waterTree(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            log.info("#### 定时任务[同程 - 水滴 - 浇水] 结束 ####");
        }
    }

    @Scheduled(cron = "${job.tongcheng.getAutoRestoreWater}")
    public void getAutoRestoreWater() {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[同程 - 水滴 - 领取恢复水滴] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    log.info("#### 用户: {} ####", user.getOpenId());
                    tongchengApi.getAutoRestoreWater(user.getOpenId(), bindFarmPO.getCookie());
                }
            }
            log.info("#### 定时任务[同程 - 水滴 - 领取恢复水滴] 结束 ####");
        }
    }

    @Scheduled(cron = "${job.tongcheng.getTaskAward1}")
    public void getTaskAward() {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[同程 - 水滴 - 领取任务水滴] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    log.info("#### 用户: {} ####", user.getOpenId());
                    int thisHour = DateUtil.thisHour(true);
                    tongchengApi.getTaskAward(user.getOpenId(), bindFarmPO.getCookie(), "T10" + thisHour);
                }
            }
            log.info("#### 定时任务[同程 - 水滴 - 领取任务水滴] 结束 ####");
        }
    }

    @Scheduled(cron = "${job.tongcheng.getTaskAward2}")
    public void getTaskAward2() {
        if (apiConfig.getTongchengConfig().getWorking()) {
            List<UserPO> users = userDao.selectList(null);
            log.info("#### 定时任务[同程 - 水滴 - 领取浇水奖励] 开始 ####");
            BindFarmPO bindFarmPO;
            for (UserPO user : users) {
                bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
                if (bindFarmPO != null) {
                    log.info("#### 用户: {} ####", user.getOpenId());
                    tongchengApi.getTaskAward(user.getOpenId(), bindFarmPO.getCookie(), "T201");
                }
            }
            log.info("#### 定时任务[同程 - 水滴 - 领取浇水奖励] 结束 ####");
        }
    }

}
