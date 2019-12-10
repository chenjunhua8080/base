package com.cjh.douban.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 农场log
 *
 * @author chenjunhua
 * @email 1109551489@qq.com
 * @date 2019-12-10 16:13:23
 */
@Data
@TableName("farm_log")
public class FarmLogPO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * openId
	 */
	private String openId;
	/**
	 * 信息
	 */
	private String message;
	/**
	 * 响应
	 */
	private String resp;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
