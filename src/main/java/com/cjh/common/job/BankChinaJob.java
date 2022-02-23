package com.cjh.common.job;

import com.cjh.common.api.BankChinaApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.dao.UserDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.po.UserPO;
import com.cjh.common.util.XxlJobUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 中银定时任务
 *
 * @author cjh
 * @date 2020/4/3
 */
@Component
@Slf4j
public class BankChinaJob {

    @Autowired
    private BankChinaApi bankApi;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BindFarmDao bindFarmDao;

    @XxlJob("job.bankChina.sign")
    public void sign() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[中银 - 签到] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.BANK_CHINA.getCode());
            if (bindFarm != null) {
                bankApi.sign(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobUtil.showLog("#### 定时任务[中银 - 签到] 结束 ####");
    }

    /**
     * job.bankChina.index 会报错？
     */
    @XxlJob("job.bankChina.reqIndex")
    public void index() {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[中银 - 查询] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.BANK_CHINA.getCode());
            if (bindFarm != null) {
                bankApi.index(user.getOpenId(), bindFarm.getCookie(), true);
            }
        }
        XxlJobUtil.showLog("#### 定时任务[中银 - 查询] 结束 ####");
    }

}
