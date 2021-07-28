package com.cjh.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjh.common.po.EmailsPO;
import com.cjh.common.request.EmailsRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 邮件表
 * 
 * @author cjh
 * @email chenjunhua8080@qq.com
 * @date 2021-05-29 14:29:43
 */
@Mapper
public interface EmailsDao extends BaseMapper<EmailsPO> {

    /**
     * 分页查询邮件表
     */
    IPage<EmailsPO> listByPage(Page page, @Param("params") EmailsRequest emailsRequest);
	
}
