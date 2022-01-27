package com.cjh.common.resp.coffee;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FanclubResp {

    /**
     * code
     */
    private Integer code;
    /**
     * message
     */
    private String message;
    /**
     * resultData
     */
    private String resultData;
}
