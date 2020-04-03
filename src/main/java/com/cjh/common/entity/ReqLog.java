package com.cjh.common.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 请求日志表(ReqLog)实体类
 *
 * @author cjh
 * @since 2020-04-03 13:47:28
 */
@Data
@TableName("req_log")
public class ReqLog implements Serializable {
    private static final long serialVersionUID = -17335203605332008L;
    
    @TableId
    private Integer id;
    private String userId;
    /**
     * 信息
     */
    private String message;
    /**
     * 响应
     */
    private String resp;
    private Date creteTime;

}