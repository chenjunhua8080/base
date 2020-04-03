package com.cjh.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjh.common.entity.ReqLog;
import java.util.List;

/**
 * 请求日志表(ReqLog)表服务接口
 *
 * @author cjh
 * @since 2020-04-03 13:43:52
 */
public interface ReqLogService extends IService<ReqLog> {

    /**
     * 真实删除
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean realDel(Integer id);

    /**
     * 添加日志
     */
    void addLog(String userId, String message, String resp);

    /**
     * 查询今天日志
     */
    List<ReqLog> getTodayReqLog(String userId);

}