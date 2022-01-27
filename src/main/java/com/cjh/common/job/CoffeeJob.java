package com.cjh.common.job;

import com.cjh.common.api.CoffeeApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.dao.UserDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.po.UserPO;
import com.cjh.common.util.XxlJobUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 雀巢咖啡 job
 *
 * @author cjh
 * @date 2022/1/27
 */
@Component
@Slf4j
public class CoffeeJob {

    @Autowired
    private CoffeeApi coffeeApi;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BindFarmDao bindFarmDao;
//
//    @XxlJob("job.coffee.funclub.post")
//    public void post() {
//        List<UserPO> users = userDao.selectList(null);
//        XxlJobUtil.showLog("#### 定时任务[雀巢咖啡 - 爱豆庄园 - 签到] 开始 ####");
//        BindFarmPO bindFarm;
//        for (UserPO user : users) {
//            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.TONGCHENG.getCode());
//            if (bindFarm != null) {
//                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());
//                coffeeApi.sign(user.getOpenId(), bindFarm.getCookie());
//            }
//        }
//        XxlJobHelper.log("#### 定时任务[雀巢咖啡 - 爱豆庄园 - 签到] 结束 ####");
//    }

    @XxlJob("job.coffee.farm")
    public void sign() throws InterruptedException {
        List<UserPO> users = userDao.selectList(null);
        XxlJobUtil.showLog("#### 定时任务[雀巢咖啡 - 爱豆庄园] 开始 ####");
        BindFarmPO bindFarm;
        for (UserPO user : users) {
            bindFarm = bindFarmDao.selectByOpenId(user.getOpenId(), PlatformEnum.COFFEE.getCode());
            if (bindFarm != null) {
                XxlJobHelper.log("#### 用户: {} ####", user.getOpenId());

                String token = coffeeApi.getToken(user.getOpenId(), bindFarm.getCookie());
                if (token == null) {
                    continue;
                }
                TimeUnit.SECONDS.sleep(2);

                Long landId = coffeeApi.getLandId(user.getOpenId(), token);
                if (landId == null) {
                    continue;
                }

                TimeUnit.SECONDS.sleep(2);
                coffeeApi.culture(user.getOpenId(), token, landId);

                TimeUnit.SECONDS.sleep(2);
                coffeeApi.sign(user.getOpenId(), token);
            }
        }
        XxlJobHelper.log("#### 定时任务[雀巢咖啡 - 爱豆庄园] 结束 ####");
    }

}
