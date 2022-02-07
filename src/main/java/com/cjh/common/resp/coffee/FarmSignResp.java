package com.cjh.common.resp.coffee;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FarmSignResp {

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
         * user
         */
        private UserBean user;
        /**
         * list
         */
        private List<ListBean> list;

        /**
         * UserBean
         */
        @NoArgsConstructor
        @Data
        public static class UserBean {

            /**
             * id
             */
            private Integer id;
            /**
             * openid
             */
            private String openid;
            /**
             * unionid
             */
            private String unionid;
            /**
             * name
             */
            private String name;
            /**
             * avatar
             */
            private String avatar;
            /**
             * sex
             */
            private Integer sex;
            /**
             * mobile
             */
            private String mobile;
            /**
             * credit
             */
            private Integer credit;
            /**
             * yunnanStatus
             */
            private Integer yunnanStatus;
            /**
             * lotteryStatus
             */
            private Integer lotteryStatus;
            /**
             * yunnanActionStatus
             */
            private Integer yunnanActionStatus;
            /**
             * status
             */
            private Integer status;
            /**
             * coefficient
             */
            private String coefficient;
            /**
             * avatarStatus
             */
            private Integer avatarStatus;
            /**
             * messageStatus
             */
            private Integer messageStatus;
            /**
             * rememberToken
             */
            private Object rememberToken;
            /**
             * createdAt
             */
            private String createdAt;
            /**
             * updatedAt
             */
            private String updatedAt;
            /**
             * deletedAt
             */
            private Object deletedAt;
            /**
             * firstWeed
             */
            private Integer firstWeed;
            /**
             * firstInsect
             */
            private Integer firstInsect;
            /**
             * firstTrim
             */
            private Integer firstTrim;
            /**
             * freeTree
             */
            private Integer freeTree;
            /**
             * runStatus
             */
            private Integer runStatus;
            /**
             * qrcode
             */
            private String qrcode;
            /**
             * aidounumber
             */
            private Object aidounumber;
            /**
             * isSow
             */
            private Integer isSow;
            /**
             * authStatus
             */
            private Integer authStatus;
            /**
             * progress
             */
            private Integer progress;
            /**
             * propStatus
             */
            private Integer propStatus;
        }

        /**
         * ListBean
         */
        @NoArgsConstructor
        @Data
        public static class ListBean {

            /**
             * credit
             */
            private Integer credit;
            /**
             * name
             */
            private String name;
            /**
             * sign
             */
            private Boolean sign;
        }
    }
}
