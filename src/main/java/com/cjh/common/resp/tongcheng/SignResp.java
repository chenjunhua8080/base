package com.cjh.common.resp.tongcheng;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {"rspCode":0,"message":"签到成功","data":{"isShowTenTimesWin":false,"memberGrade":1,"isOpenSignNotify":false,"signDay":1,"isBlackGoldCard":false,"blackGoldCardUrl":"/page/pubBusiness/views/webview_black/webview_black?needwrap=1\u0026src=https%3A%2F%2Fwx.17u.cn%2Fblackwhale%3Fchannelcode%3D8fe0e029f29d4eae96e6df63cc4cd550%26refid%3D766637729","awardRatio":1,"continuedSignDays":1,"totalSignDays":3,"activityUrl":null,"isNewUserFirstWeek":false,"cash":null,"prizes":[{"amount":"4","amountDesc":"里程","type":2,"isNewUserFirstWeek":false}]}}
 */
@NoArgsConstructor
@Data
public class SignResp {

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
         * isShowTenTimesWin
         */
        private Boolean isShowTenTimesWin;
        /**
         * memberGrade
         */
        private Integer memberGrade;
        /**
         * isOpenSignNotify
         */
        private Boolean isOpenSignNotify;
        /**
         * signDay
         */
        private Integer signDay;
        /**
         * isBlackGoldCard
         */
        private Boolean isBlackGoldCard;
        /**
         * blackGoldCardUrl
         */
        private String blackGoldCardUrl;
        /**
         * awardRatio
         */
        private Integer awardRatio;
        /**
         * continuedSignDays
         */
        private Integer continuedSignDays;
        /**
         * totalSignDays
         */
        private Integer totalSignDays;
        /**
         * activityUrl
         */
        private Object activityUrl;
        /**
         * isNewUserFirstWeek
         */
        private Boolean isNewUserFirstWeek;
        /**
         * cash
         */
        private Object cash;
        /**
         * prizes
         */
        private List<PrizesBean> prizes;

        /**
         * PrizesBean
         */
        @NoArgsConstructor
        @Data
        public static class PrizesBean {

            /**
             * amount
             */
            private String amount;
            /**
             * amountDesc
             */
            private String amountDesc;
            /**
             * type
             */
            private Integer type;
            /**
             * isNewUserFirstWeek
             */
            private Boolean isNewUserFirstWeek;
        }
    }
}
