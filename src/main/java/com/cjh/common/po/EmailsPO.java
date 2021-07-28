package com.cjh.common.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 邮件表
 * 
 * @author cjh
 * @email chenjunhua8080@qq.com
 * @date 2021-05-29 14:29:43
 */
@Data
@TableName("emails")
public class EmailsPO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 来自邮箱
	 */
	private String fromMail;
	/**
	 * 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 下载时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date orgSendTime;
	/**
	 * 附件文件本地地址
	 */
	private String attrSrc;
}
