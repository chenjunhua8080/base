package com.cjh.common.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * 
 * @author cjh
 * @email 
 * @date 2019-10-21 18:44:25
 */
@Data
@TableName("user")
public class UserPO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String openId;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Integer age;
	/**
	 * 
	 */
	private String phone;
	/**
	 * 
	 */
	private String password;
	/**
	 *
	 */
	private String avatar;

}
