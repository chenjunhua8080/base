package com.cjh.common.job;

import com.cjh.common.api.ApiConfig;
import com.cjh.common.api.UHomeApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.dao.UserDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.po.UserPO;
import com.cjh.common.util.DateUtil;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 有家便利店签到 job
 *
 * @author cjh
 * @date 2021/7/28
 */
@EnableScheduling
@Component
@Slf4j
public class UHomeJob {

    @Autowired
    private ApiConfig apiConfig;
    @Autowired
    private UHomeApi uHomeApi;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BindFarmDao bindFarmDao;

    @Scheduled(cron = "${job.uhome.sign}")
    public void sign() {
        if (!apiConfig.getUHomeConfig().getWorking()) {
            return;
        }
        log.info("#### 定时任务[有家便利店 - 签到] 开始: {} ####", DateUtil.format(new Date()));
        List<UserPO> users = userDao.selectList(null);
        BindFarmPO bindFarmPO;
        for (UserPO user : users) {
            bindFarmPO = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.UHOME.getCode());
            if (bindFarmPO != null) {
                uHomeApi.sign(user.getOpenId(), bindFarmPO.getCookie());
            }
        }
        log.info("#### 定时任务[有家便利店 - 签到] 结束: {} ####", DateUtil.format(new Date()));
    }
}
