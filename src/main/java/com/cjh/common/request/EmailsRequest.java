package com.cjh.common.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * 邮件表
 *
 * @author cjh
 * @email chenjunhua8080@qq.com
 * @date 2021-05-29 14:29:43
 */
@Data
public class EmailsRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    private List<Integer> ids;
    private Integer pageSize = 50;
    private Integer pageNum = 1;
    /**
     * 来自邮箱
     */
    private String fromMail;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
    /**
     * 下载时间
     */
    private Date downloadTime;
    /**
     * 原邮件标题
     */
    private String orgTitle;
    /**
     * 原邮件内容
     */
    private String orgContent;
    /**
     * 原邮件发送人
     */
    private String orgFrom;
    /**
     * 原邮件收件人
     */
    private String orgTo;
    /**
     * 原邮件发送时间
     */
    private Date orgSendTime;

}
