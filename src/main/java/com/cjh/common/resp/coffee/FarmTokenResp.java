package com.cjh.common.resp.coffee;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FarmTokenResp {

    /**
     * errorCode
     */
    private Integer errorCode;
    /**
     * errorMessage
     */
    private String errorMessage;
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
         * token
         */
        private String token;
        /**
         * tokenType
         */
        private String tokenType;
        /**
         * hwTokenExpired
         */
        private Integer hwTokenExpired;
    }
}
