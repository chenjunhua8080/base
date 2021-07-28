package com.cjh.common.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {"code":0,"data":{"addTime":"2021-07-28 10:06:47","city":"广州","couponCode":"","daySign":1,"giftData":"1","giftName":"","giftNumber":1,"giveType":2,"id":843503,"memberId":"94461a4de25244feacb8c6b32c3a28a6","phone":"13413527259","returnMsg":"","sendCouponSource":1,"signId":1,"status":1,"transNo":"108880002-59088310044230656","updateTime":"2021-07-28
 * 10:06:47"},"message":"操作成功","respTime":0,"sign":""}
 */
@NoArgsConstructor
@Data
public class UHomeResp {

    /**
     * code
     */
    private Long code;
    /**
     * data
     */
    private DataBean data;
    /**
     * message
     */
    private String message;
    /**
     * respTime
     */
    private Long respTime;
    /**
     * sign
     */
    private String sign;

    /**
     * DataBean
     */
    @NoArgsConstructor
    @Data
    public static class DataBean {

        /**
         * addTime
         */
        private String addTime;
        /**
         * city
         */
        private String city;
        /**
         * couponCode
         */
        private String couponCode;
        /**
         * daySign
         */
        private Long daySign;
        /**
         * giftData
         */
        private String giftData;
        /**
         * giftName
         */
        private String giftName;
        /**
         * giftNumber
         */
        private Long giftNumber;
        /**
         * giveType
         */
        private Long giveType;
        /**
         * id
         */
        private Long id;
        /**
         * memberId
         */
        private String memberId;
        /**
         * phone
         */
        private String phone;
        /**
         * returnMsg
         */
        private String returnMsg;
        /**
         * sendCouponSource
         */
        private Long sendCouponSource;
        /**
         * signId
         */
        private Long signId;
        /**
         * status
         */
        private Long status;
        /**
         * transNo
         */
        private String transNo;
        /**
         * updateTime
         */
        private String updateTime;
    }
}
