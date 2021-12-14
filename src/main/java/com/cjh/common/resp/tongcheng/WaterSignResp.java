package com.cjh.common.resp.tongcheng;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {"rspCode":0,"message":"领取成功","data":{"waterNum":96}}
 */
@NoArgsConstructor
@Data
public class WaterSignResp {

    /**
     * rspCode
     */
    private Integer rspCode;
    /**
     * message
     */
    private String message;
    /**
     * data
     */
    private DataBean data;

    /**
     * DataBean
     */
    @NoArgsConstructor
    @Data
    public static class DataBean {

        /**
         * waterNum
         */
        private Integer waterNum;
    }
}
