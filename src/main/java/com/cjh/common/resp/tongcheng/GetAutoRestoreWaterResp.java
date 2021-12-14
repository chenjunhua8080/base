package com.cjh.common.resp.tongcheng;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {"code":0,"items":{"k1":216},"AutoRestoreWater":{"waterNum":20,"time":1639463550191,"nextTime":1639481550191}}
 */
@NoArgsConstructor
@Data
public class GetAutoRestoreWaterResp {

    /**
     * errMsg
     */
    private String errMsg;
    /**
     * code
     */
    private Integer code;
    /**
     * items
     */
    private ItemsBean items;
    /**
     * autoRestoreWater
     */
    private AutoRestoreWaterBean autoRestoreWater;

    /**
     * ItemsBean
     */
    @NoArgsConstructor
    @Data
    public static class ItemsBean {

        /**
         * k1
         */
        private Integer k1;
    }

    /**
     * AutoRestoreWaterBean
     */
    @NoArgsConstructor
    @Data
    public static class AutoRestoreWaterBean {

        /**
         * waterNum
         */
        private Integer waterNum;
        /**
         * time
         */
        private Long time;
        /**
         * nextTime
         */
        private Long nextTime;
    }
}
