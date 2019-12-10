package com.cjh.douban.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjh.douban.dao.FarmLogDao;
import com.cjh.douban.po.FarmLogPO;
import com.cjh.douban.service.FarmLogService;
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
}
