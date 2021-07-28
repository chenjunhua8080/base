package com.cjh.common.resp;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TreeInfo {

    /**
     * code
     */
    private Integer code;
    /**
     * rightUpResouces
     */
    private RightUpResoucesBean rightUpResouces;
    /**
     * turntableInit
     */
    private TurntableInitBean turntableInit;
    /**
     * showExchangeGuidance
     */
    private Boolean showExchangeGuidance;
    /**
     * iosShieldConfig
     */
    private Object iosShieldConfig;
    /**
     * waterGiftSurpriseIconLink
     */
    private Object waterGiftSurpriseIconLink;
    /**
     * mengchongResouce
     */
    private MengchongResouceBean mengchongResouce;
    /**
     * clockInGotWater
     */
    private Boolean clockInGotWater;
    /**
     * isOpenOldRemind
     */
    private Boolean isOpenOldRemind;
    /**
     * guidPopupTask
     */
    private GuidPopupTaskBean guidPopupTask;
    /**
     * toFruitEnergy
     */
    private Long toFruitEnergy;
    /**
     * couponSurpriseIconLink
     */
    private Object couponSurpriseIconLink;
    /**
     * statisticsTimes
     */
    private Object statisticsTimes;
    /**
     * sysTime
     */
    private Long sysTime;
    /**
     * canHongbaoContineUse
     */
    private Boolean canHongbaoContineUse;
    /**
     * toFlowTimes
     */
    private Long toFlowTimes;
    /**
     * iosConfigResouces
     */
    private IosConfigResoucesBean iosConfigResouces;
    /**
     * todayGotWaterGoalTask
     */
    private TodayGotWaterGoalTaskBean todayGotWaterGoalTask;
    /**
     * oldUserState
     */
    private Long oldUserState;
    /**
     * leftUpResouces
     */
    private LeftUpResoucesBean leftUpResouces;
    /**
     * minSupportAPPVersion
     */
    private String minSupportAPPVersion;
    /**
     * lowFreqStatus
     */
    private Long lowFreqStatus;
    /**
     * funCollectionHasLimit
     */
    private Boolean funCollectionHasLimit;
    /**
     * message
     */
    private Object message;
    /**
     * treeState
     */
    private Long treeState;
    /**
     * rightDownResouces
     */
    private RightDownResoucesBean rightDownResouces;
    /**
     * iconFirstPurchaseInit
     */
    private Boolean iconFirstPurchaseInit;
    /**
     * toFlowEnergy
     */
    private Long toFlowEnergy;
    /**
     * farmUserPro
     */
    private FarmUserProBean farmUserPro;
    /**
     * retainPopupLimit
     */
    private Long retainPopupLimit;
    /**
     * toBeginEnergy
     */
    private Long toBeginEnergy;
    /**
     * collectPopWindow
     */
    private CollectPopWindowBean collectPopWindow;
    /**
     * leftDownResouces
     */
    private Object leftDownResouces;
    /**
     * enableSign
     */
    private Boolean enableSign;
    /**
     * loadFriend
     */
    private LoadFriendBean loadFriend;
    /**
     * hadCompleteXgTask
     */
    private Boolean hadCompleteXgTask;
    /**
     * groupGiftSurpriseIconLink
     */
    private Object groupGiftSurpriseIconLink;
    /**
     * oldUserIntervalTimes
     */
    private List<Long> oldUserIntervalTimes;
    /**
     * toFruitTimes
     */
    private Long toFruitTimes;
    /**
     * oldUserSendWater
     */
    private List<String> oldUserSendWater;

    /**
     * RightUpResoucesBean
     */
    @NoArgsConstructor
    @Data
    public static class RightUpResoucesBean {

        /**
         * advertId
         */
        private String advertId;
        /**
         * name
         */
        private String name;
        /**
         * appImage
         */
        private String appImage;
        /**
         * appLink
         */
        private String appLink;
        /**
         * cxyImage
         */
        private String cxyImage;
        /**
         * cxyLink
         */
        private String cxyLink;
        /**
         * type
         */
        private String type;
        /**
         * openLink
         */
        private Boolean openLink;
    }

    /**
     * TurntableInitBean
     */
    @NoArgsConstructor
    @Data
    public static class TurntableInitBean {

        /**
         * timeState
         */
        private Long timeState;
    }

    /**
     * MengchongResouceBean
     */
    @NoArgsConstructor
    @Data
    public static class MengchongResouceBean {

        /**
         * advertId
         */
        private String advertId;
        /**
         * name
         */
        private String name;
        /**
         * appImage
         */
        private String appImage;
        /**
         * appLink
         */
        private String appLink;
        /**
         * cxyImage
         */
        private String cxyImage;
        /**
         * cxyLink
         */
        private String cxyLink;
        /**
         * type
         */
        private String type;
        /**
         * openLink
         */
        private Boolean openLink;
    }

    /**
     * GuidPopupTaskBean
     */
    @NoArgsConstructor
    @Data
    public static class GuidPopupTaskBean {

        /**
         * guidPopupTask
         */
        private String guidPopupTask;
    }

    /**
     * IosConfigResoucesBean
     */
    @NoArgsConstructor
    @Data
    public static class IosConfigResoucesBean {

        /**
         * advertId
         */
        private String advertId;
        /**
         * name
         */
        private String name;
        /**
         * appImage
         */
        private String appImage;
        /**
         * appLink
         */
        private String appLink;
        /**
         * cxyImage
         */
        private String cxyImage;
        /**
         * cxyLink
         */
        private String cxyLink;
        /**
         * type
         */
        private String type;
        /**
         * openLink
         */
        private Boolean openLink;
    }

    /**
     * TodayGotWaterGoalTaskBean
     */
    @NoArgsConstructor
    @Data
    public static class TodayGotWaterGoalTaskBean {

        /**
         * canPop
         */
        private Boolean canPop;
    }

    /**
     * LeftUpResoucesBean
     */
    @NoArgsConstructor
    @Data
    public static class LeftUpResoucesBean {

        /**
         * advertId
         */
        private String advertId;
        /**
         * name
         */
        private String name;
        /**
         * appImage
         */
        private String appImage;
        /**
         * appLink
         */
        private String appLink;
        /**
         * cxyImage
         */
        private String cxyImage;
        /**
         * cxyLink
         */
        private String cxyLink;
        /**
         * type
         */
        private String type;
        /**
         * openLink
         */
        private Boolean openLink;
    }

    /**
     * RightDownResoucesBean
     */
    @NoArgsConstructor
    @Data
    public static class RightDownResoucesBean {

        /**
         * advertId
         */
        private String advertId;
        /**
         * name
         */
        private String name;
        /**
         * appImage
         */
        private String appImage;
        /**
         * appLink
         */
        private String appLink;
        /**
         * cxyImage
         */
        private String cxyImage;
        /**
         * cxyLink
         */
        private String cxyLink;
        /**
         * type
         */
        private String type;
        /**
         * openLink
         */
        private Boolean openLink;
    }

    /**
     * FarmUserProBean
     */
    @NoArgsConstructor
    @Data
    public static class FarmUserProBean {

        /**
         * totalEnergy
         */
        private Long totalEnergy;
        /**
         * treeState
         */
        private Long treeState;
        /**
         * groupSkuId
         */
        private String groupSkuId;
        /**
         * createTime
         */
        private Long createTime;
        /**
         * treeEnergy
         */
        private Long treeEnergy;
        /**
         * treeTotalEnergy
         */
        private Long treeTotalEnergy;
        /**
         * shareCode
         */
        private String shareCode;
        /**
         * winTimes
         */
        private Long winTimes;
        /**
         * nickName
         */
        private String nickName;
        /**
         * imageUrl
         */
        private String imageUrl;
        /**
         * couponKey
         */
        private String couponKey;
        /**
         * type
         */
        private String type;
        /**
         * simpleName
         */
        private String simpleName;
        /**
         * name
         */
        private String name;
        /**
         * goodsImage
         */
        private String goodsImage;
        /**
         * skuId
         */
        private String skuId;
        /**
         * lastLoginDate
         */
        private Long lastLoginDate;
        /**
         * newOldState
         */
        private Long newOldState;
        /**
         * oldMarkComplete
         */
        private Long oldMarkComplete;
        /**
         * commonState
         */
        private Long commonState;
        /**
         * prizeLevel
         */
        private Long prizeLevel;
    }

    /**
     * CollectPopWindowBean
     */
    @NoArgsConstructor
    @Data
    public static class CollectPopWindowBean {

        /**
         * windowType
         */
        private Long windowType;
        /**
         * type
         */
        private Long type;
        /**
         * userLevel
         */
        private Long userLevel;
    }

    /**
     * LoadFriendBean
     */
    @NoArgsConstructor
    @Data
    public static class LoadFriendBean {

        /**
         * code
         */
        private String code;
        /**
         * statisticsTimes
         */
        private Object statisticsTimes;
        /**
         * sysTime
         */
        private Long sysTime;
        /**
         * message
         */
        private Object message;
        /**
         * firstAddUser
         */
        private Boolean firstAddUser;
    }
}
