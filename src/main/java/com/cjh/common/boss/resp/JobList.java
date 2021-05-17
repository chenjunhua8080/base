package com.cjh.common.boss.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class JobList {


    /**
     * encryptJobId
     */
    private String encryptJobId;
    /**
     * description
     */
    private String description;
    /**
     * jobId
     */
    private String jobId;
    /**
     * jobType
     */
    private Integer jobType;

}
