package generate;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * emails
 * @author 
 */
@Data
public class Emails implements Serializable {
    private Integer id;

    /**
     * 来自邮箱
     */
    private String fromMail;

    private Date createTime;

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

    private static final long serialVersionUID = 1L;
}