package com.cjh.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cjh.common.po.EmailsPO;
import com.cjh.common.request.EmailsRequest;
import java.io.File;

/**
 * 邮件表
 *
 * @author cjh
 * @email chenjunhua8080@qq.com
 * @date 2021-05-29 14:29:43
 */
public interface EmailsService extends IService<EmailsPO> {

    /**
     * 分页查询邮件表
     */
    IPage<EmailsPO> listByPage(EmailsRequest emailsRequest);

    Integer getNotDownCount(EmailsRequest emails);

    File zip(EmailsRequest emails);

    File down(EmailsRequest emails);

    File downloadByNever(EmailsRequest emails);

}

