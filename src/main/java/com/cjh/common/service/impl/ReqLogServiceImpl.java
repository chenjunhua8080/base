package com.cjh.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjh.common.dao.ReqLogDao;
import com.cjh.common.entity.ReqLog;
import com.cjh.common.service.ReqLogService;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 请求日志表(ReqLog)表服务实现类
 *
 * @author cjh
 * @since 2020-04-03 13:48:13
 */
@Slf4j
@AllArgsConstructor
@Service("reqLogService")
public class ReqLogServiceImpl extends ServiceImpl<ReqLogDao, ReqLog> implements ReqLogService {

    /**
     * 真实删除
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean realDel(Integer id) {
        return baseMapper.realDel(id) > 0;
    }

    @Override
    public void addLog(Integer platformType, String userId, String message, String resp) {
        ReqLog entity = new ReqLog();
        entity.setUserId(userId);
        entity.setMessage(message);
        entity.setResp(resp);
        baseMapper.insert(entity);
    }

    @Override
    public List<ReqLog> getTodayReqLog(String userId) {
        return baseMapper.selectOnDay(userId, new Date());
    }

    @Override
    public List<ReqLog> getByPlatformTypeAndUser(int platformType, String openId, Date date) {
        return baseMapper.getByPlatformTypeAndUser(platformType, openId, date);
    }
}