package com.cjh.common.boss.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ScanResp {

    /**
     * {"msg":"timeout","scaned":false}//超时
     * <p>
     * {"scaned":true,"allweb":true,"newScaned":true}//扫码
     * <p>
     * {"scaned":true,"newScaned":true,"login":true}//登录
     */

    private String msg;
    private boolean scaned;
    private boolean allweb;
    private boolean newScaned;
    private boolean login;
}
