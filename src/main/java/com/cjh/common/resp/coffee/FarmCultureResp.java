package com.cjh.common.resp.coffee;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FarmCultureResp {

    /**
     * errorCode
     */
    private Long errorCode;
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
         * animal
         */
        private Boolean animal;
        /**
         * land
         */
        private LandBean land;
        /**
         * user
         */
        private UserBean user;
        /**
         * landReward
         */
        private LandRewardBean landReward;
        /**
         * progress
         */
        private Long progress;
        /**
         * message
         */
        private MessageBean message;
        /**
         * treeShow
         */
        private Boolean treeShow;
        /**
         * updateSetting
         */
        private Object updateSetting;
        /**
         * times
         */
        private Long times;
        /**
         * userUseProps
         */
        private List<?> userUseProps;

        /**
         * LandBean
         */
        @NoArgsConstructor
        @Data
        public static class LandBean {

            /**
             * id
             */
            private Long id;
            /**
             * userId
             */
            private Long userId;
            /**
             * zoneId
             */
            private Long zoneId;
            /**
             * manorId
             */
            private Long manorId;
            /**
             * seedId
             */
            private Long seedId;
            /**
             * levelId
             */
            private Long levelId;
            /**
             * isDone
             */
            private Long isDone;
            /**
             * seedTime
             */
            private String seedTime;
            /**
             * progress
             */
            private Long progress;
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
             * level
             */
            private LevelBean level;

            /**
             * LevelBean
             */
            @NoArgsConstructor
            @Data
            public static class LevelBean {

                /**
                 * id
                 */
                private Long id;
                /**
                 * name
                 */
                private String name;
                /**
                 * level
                 */
                private Long level;
                /**
                 * progress
                 */
                private String progress;
                /**
                 * createdAt
                 */
                private Object createdAt;
                /**
                 * updatedAt
                 */
                private Object updatedAt;
                /**
                 * image
                 */
                private Object image;
                /**
                 * desOne
                 */
                private Object desOne;
                /**
                 * desTwo
                 */
                private Object desTwo;
                /**
                 * btnTitle
                 */
                private Object btnTitle;
            }
        }

        /**
         * UserBean
         */
        @NoArgsConstructor
        @Data
        public static class UserBean {

            /**
             * id
             */
            private Long id;
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
            private Long sex;
            /**
             * mobile
             */
            private String mobile;
            /**
             * credit
             */
            private Long credit;
            /**
             * yunnanStatus
             */
            private Long yunnanStatus;
            /**
             * lotteryStatus
             */
            private Long lotteryStatus;
            /**
             * yunnanActionStatus
             */
            private Long yunnanActionStatus;
            /**
             * status
             */
            private Long status;
            /**
             * coefficient
             */
            private String coefficient;
            /**
             * avatarStatus
             */
            private Long avatarStatus;
            /**
             * messageStatus
             */
            private Long messageStatus;
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
            private Long firstWeed;
            /**
             * firstInsect
             */
            private Long firstInsect;
            /**
             * firstTrim
             */
            private Long firstTrim;
            /**
             * freeTree
             */
            private Long freeTree;
            /**
             * runStatus
             */
            private Long runStatus;
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
            private Long isSow;
            /**
             * authStatus
             */
            private Long authStatus;
            /**
             * progress
             */
            private Long progress;
            /**
             * propStatus
             */
            private Long propStatus;
        }

        /**
         * LandRewardBean
         */
        @NoArgsConstructor
        @Data
        public static class LandRewardBean {

            /**
             * credit
             */
            private Long credit;
            /**
             * message
             */
            private String message;
            /**
             * next
             */
            private String next;
        }

        /**
         * MessageBean
         */
        @NoArgsConstructor
        @Data
        public static class MessageBean {

            /**
             * id
             */
            private Long id;
        }
    }
}
