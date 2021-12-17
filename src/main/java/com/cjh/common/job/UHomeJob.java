package com.cjh.common.job;

import com.cjh.common.api.UHomeApi;
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
 * 有家便利店签到 job
 *
 * @author cjh
 * @date 2021/7/28
 */
@Component
@Slf4j
public class UHomeJob {

    @Autowired
    private UHomeApi uHomeApi;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BindFarmDao bindFarmDao;

    @XxlJob("job.uhome.sign")
    public void sign() {
        XxlJobUtil.showLog("#### 定时任务[有家便利店 - 签到] 开始 ####");
        List<UserPO> users = userDao.selectList(null);
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.UHOME.getCode());
            if (bindFarm != null) {
                uHomeApi.sign(user.getOpenId(), bindFarm.getCookie());
            }
        }
        XxlJobHelper.log("#### 定时任务[有家便利店 - 签到] 结束 ####");
    }

}
