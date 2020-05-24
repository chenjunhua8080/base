package com.cjh.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjh.common.entity.ReqLog;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 请求日志表(ReqLog)表数据库访问层
 *
 * @author cjh
 * @since 2020-04-03 13:43:52
 */
@Mapper 
public interface ReqLogDao extends BaseMapper<ReqLog> {

    /**
     * 真实删除
     *
     * @param id 主键
     * @return 影响行数
     */
    int realDel(Integer id);

    /**
     * 查询日志，根据天
     */
    List<ReqLog> selectOnDay(@Param("userId") String userId, @Param("date") Date date);

    List<ReqLog> getByPlatformTypeAndUser(
        @Param("platformType") int platformType, @Param("openId") String openId, @Param("date") Date date);
}