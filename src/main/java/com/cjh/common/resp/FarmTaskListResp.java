package com.cjh.common.resp;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FarmTaskListResp {

    /**
     * allTaskFinished
     */
    private Boolean allTaskFinished;
    /**
     * code
     */
    private Integer code;
    /**
     * signInit
     */
    private SignInitBean signInit;
    /**
     * gotBrowseTaskAdInit
     */
    private GotBrowseTaskAdInitBean gotBrowseTaskAdInit;
    /**
     * message
     */
    private Object message;
    /**
     * taskOrder
     */
    private List<String> taskOrder;
    /**
     * gotThreeMealInit
     */
    private GotThreeMealInitBean gotThreeMealInit;
    /**
     * firstWaterInit
     */
    private FirstWaterInitBean firstWaterInit;
    /**
     * totalWaterTaskInit
     */
    private TotalWaterTaskInitBean totalWaterTaskInit;
    /**
     * statisticsTimes
     */
    private Object statisticsTimes;
    /**
     * sysTime
     */
    private Long sysTime;
    /**
     * orderInit
     */
    private OrderInitBean orderInit;
    /**
     * waterRainInit
     */
    private WaterRainInitBean waterRainInit;
    /**
     * waterFriendTaskInit
     */
    private WaterFriendTaskInitBean waterFriendTaskInit;

    /**
     * SignInitBean
     */
    @NoArgsConstructor
    @Data
    public static class SignInitBean {

        /**
         * signEnergyEachAmount
         */
        private String signEnergyEachAmount;
        /**
         * f
         */
        private Boolean f;
        /**
         * totalSigned
         */
        private Integer totalSigned;
        /**
         * signEnergyAmounts
         */
        private List<String> signEnergyAmounts;
        /**
         * todaySigned
         */
        private Boolean todaySigned;
    }

    /**
     * GotBrowseTaskAdInitBean
     */
    @NoArgsConstructor
    @Data
    public static class GotBrowseTaskAdInitBean {

        /**
         * f
         */
        private Boolean f;
        /**
         * userBrowseTaskAds
         */
        private List<UserBrowseTaskAdsBean> userBrowseTaskAds;

        /**
         * UserBrowseTaskAdsBean
         */
        @NoArgsConstructor
        @Data
        public static class UserBrowseTaskAdsBean {

            /**
             * advertId
             */
            private String advertId;
            /**
             * mainTitle
             */
            private String mainTitle;
            /**
             * subTitle
             */
            private String subTitle;
            /**
             * wechatPic
             */
            private String wechatPic;
            /**
             * link
             */
            private String link;
            /**
             * picurl
             */
            private String picurl;
            /**
             * wechatLink
             */
            private String wechatLink;
            /**
             * wechatMain
             */
            private String wechatMain;
            /**
             * wechatSub
             */
            private String wechatSub;
            /**
             * type
             */
            private String type;
            /**
             * reward
             */
            private Integer reward;
            /**
             * limit
             */
            private Integer limit;
            /**
             * time
             */
            private Integer time;
            /**
             * hadGotTimes
             */
            private Integer hadGotTimes;
            /**
             * hadFinishedTimes
             */
            private Integer hadFinishedTimes;
            /**
             * appLinkType
             */
            private String appLinkType;
            /**
             * appLinkId
             */
            private String appLinkId;
        }
    }

    /**
     * GotThreeMealInitBean
     */
    @NoArgsConstructor
    @Data
    public static class GotThreeMealInitBean {

        /**
         * threeMealAmount
         */
        private String threeMealAmount;
        /**
         * pos
         */
        private Integer pos;
        /**
         * hadGotShareAmount
         */
        private Boolean hadGotShareAmount;
        /**
         * f
         */
        private Boolean f;
        /**
         * threeMealTimes
         */
        private List<String> threeMealTimes;
        /**
         * hadGotAmount
         */
        private Boolean hadGotAmount;
    }

    /**
     * FirstWaterInitBean
     */
    @NoArgsConstructor
    @Data
    public static class FirstWaterInitBean {

        /**
         * firstWaterFinished
         */
        private Boolean firstWaterFinished;
        /**
         * f
         */
        private Boolean f;
        /**
         * totalWaterTimes
         */
        private Integer totalWaterTimes;
        /**
         * firstWaterEnergy
         */
        private Integer firstWaterEnergy;
    }

    /**
     * TotalWaterTaskInitBean
     */
    @NoArgsConstructor
    @Data
    public static class TotalWaterTaskInitBean {

        /**
         * totalWaterTaskLimit
         */
        private Integer totalWaterTaskLimit;
        /**
         * totalWaterTaskEnergy
         */
        private Integer totalWaterTaskEnergy;
        /**
         * f
         */
        private Boolean f;
        /**
         * totalWaterTaskFinished
         */
        private Boolean totalWaterTaskFinished;
        /**
         * totalWaterTaskTimes
         */
        private Integer totalWaterTaskTimes;
    }

    /**
     * OrderInitBean
     */
    @NoArgsConstructor
    @Data
    public static class OrderInitBean {

        /**
         * f
         */
        private Boolean f;
        /**
         * config
         */
        private ConfigBean config;

        /**
         * ConfigBean
         */
        @NoArgsConstructor
        @Data
        public static class ConfigBean {

            /**
             * icon
             */
            private String icon;
            /**
             * jumpLink
             */
            private String jumpLink;
            /**
             * mainTitle
             */
            private String mainTitle;
            /**
             * subTitle
             */
            private String subTitle;
            /**
             * sendWaterX
             */
            private Integer sendWaterX;
            /**
             * limitN
             */
            private Integer limitN;
            /**
             * refundMaxTimes
             */
            private Integer refundMaxTimes;
            /**
             * finishedGotTimes
             */
            private Integer finishedGotTimes;
            /**
             * finishedTimes
             */
            private Integer finishedTimes;
            /**
             * hadFinished
             */
            private Boolean hadFinished;
        }
    }

    /**
     * WaterRainInitBean
     */
    @NoArgsConstructor
    @Data
    public static class WaterRainInitBean {

        /**
         * lastTime
         */
        private Integer lastTime;
        /**
         * f
         */
        private Boolean f;
        /**
         * winTimes
         */
        private Integer winTimes;
        /**
         * config
         */
        private ConfigBean config;

        /**
         * ConfigBean
         */
        @NoArgsConstructor
        @Data
        public static class ConfigBean {

            /**
             * maxLimit
             */
            private Integer maxLimit;
            /**
             * peraward
             */
            private Double peraward;
            /**
             * awardmax
             */
            private Integer awardmax;
            /**
             * intervalTime
             */
            private Integer intervalTime;
            /**
             * lotteryProb
             */
            private Integer lotteryProb;
            /**
             * imgArea
             */
            private String imgArea;
            /**
             * countDown
             */
            private Integer countDown;
            /**
             * countOfBomb
             */
            private Integer countOfBomb;
            /**
             * minAwardWater
             */
            private Integer minAwardWater;
            /**
             * maxAwardWater
             */
            private Integer maxAwardWater;
            /**
             * bottomImg
             */
            private String bottomImg;
            /**
             * keys
             */
            private List<?> keys;
            /**
             * actId
             */
            private Integer actId;
            /**
             * btnText
             */
            private String btnText;
        }
    }

    /**
     * WaterFriendTaskInitBean
     */
    @NoArgsConstructor
    @Data
    public static class WaterFriendTaskInitBean {

        /**
         * waterFriendMax
         */
        private Integer waterFriendMax;
        /**
         * waterFriendSendWater
         */
        private Integer waterFriendSendWater;
        /**
         * waterFriendCountKey
         */
        private Integer waterFriendCountKey;
        /**
         * f
         */
        private Boolean f;
        /**
         * waterFriendGotAward
         */
        private Boolean waterFriendGotAward;
    }
}
