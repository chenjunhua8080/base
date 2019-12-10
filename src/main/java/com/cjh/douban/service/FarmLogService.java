package com.cjh.douban.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjh.douban.po.FarmLogPO;

/**
 * 农场log
 *
 * @author chenjunhua
 * @email 1109551489@qq.com
 * @date 2019-12-10 16:13:23
 */
public interface FarmLogService extends IService<FarmLogPO> {

    void addLog(String openId, String message, String resp);

}

