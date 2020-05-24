package com.cjh.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjh.common.po.BindFarmPO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 绑定农场
 *
 * @author chenjunhua
 * @email 1109551489@qq.com
 * @date 2019-12-10 18:00:04
 */
@Mapper
public interface BindFarmDao extends BaseMapper<BindFarmPO> {

    /**
     * 根据userID查询
     */
    BindFarmPO selectByUserId(@Param("userId") Integer userId);

    /**
     * 新 根据userID查询
     */
    BindFarmPO getBindUser(@Param("userId") Integer userId, @Param("platformType") Integer platformType);

    /**
     * 查询列表
     */
    List<BindFarmPO> selectByPlatformType(@Param("platformType") Integer platformType);

    /**
     * 根据openId查询
     */
    BindFarmPO selectByOpenId(@Param("openId") String openId, @Param("platformType") Integer platformType);
}
