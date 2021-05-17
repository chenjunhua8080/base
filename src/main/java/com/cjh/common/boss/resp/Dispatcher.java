package com.cjh.common.boss.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Dispatcher {

    /**
     * {"code":0,"message":"Success","zpData":{"toUrl":"/web/geek/resume","identity":0,"pcToUrl":"http://www.zhipin.com/web/geek/recommend","version":9.03}}
     */

    private String toUrl;
    private String pcToUrl;
    private Integer identity;
    private Double version;

    /**
     * cookie
     */
    private String wt2;
}
