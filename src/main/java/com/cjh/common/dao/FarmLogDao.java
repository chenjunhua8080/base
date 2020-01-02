package com.cjh.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjh.common.po.FarmLogPO;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 农场log
 *
 * @author chenjunhua
 * @email 1109551489@qq.com
 * @date 2019-12-10 16:13:23
 */
@Mapper
public interface FarmLogDao extends BaseMapper<FarmLogPO> {

    /**
     * 查询日志，根据天
     */
    List<FarmLogPO> selectFarmLogOnDay(@Param("openId") String openId, @Param("date") Date date);
}
