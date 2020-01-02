package com.cjh.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjh.base.dao.FarmLogDao;
import com.cjh.base.po.FarmLogPO;
import com.cjh.base.service.FarmLogService;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 农场log
 *
 * @author chenjunhua
 * @email 1109551489@qq.com
 * @date 2019-12-10 16:13:23
 */
@Slf4j
@AllArgsConstructor
@Service
public class FarmLogServiceImpl extends ServiceImpl<FarmLogDao, FarmLogPO> implements FarmLogService {

    @Override
    public void addLog(String openId, String message, String resp) {
        FarmLogPO entity = new FarmLogPO();
        entity.setOpenId(openId);
        entity.setMessage(message);
        entity.setResp(resp);
        baseMapper.insert(entity);
    }

    @Override
    public List<FarmLogPO> getTodayFarmLog(String openId) {
        return baseMapper.selectFarmLogOnDay(openId, new Date());
    }
}
