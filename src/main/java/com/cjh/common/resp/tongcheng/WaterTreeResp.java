package com.cjh.common.resp.tongcheng;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {"code":0,"items":{"k1":221},"awardItems":[],"plantInfo":{"canUseTrapWater":0,"trapWater":88,"trapUseTime":1639528200000,"trapExpireTime":1639528200000,"score":140,"dayWaterCount":13,"dayResetTime":1639463063892,"totalWaterCount":14,"latestWaterTime":1639463913605},"nutrient":{"ownNutrient":130,"useNntrient":0,"saveWater":0},"taskList":[{"taskId":"T2401","value":1,"status":2,"updateTime":1639463119495,"extra":{}},{"taskId":"T2402","value":1,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T2403","value":1,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T2404","value":1,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T2001","value":5,"status":2,"updateTime":1639463119495,"extra":{}},{"taskId":"T201","value":1,"status":2,"updateTime":1639463119495,"extra":{}},{"taskId":"T202","value":5,"status":2,"updateTime":1639463119495,"extra":{}},{"taskId":"T203","value":10,"status":2,"updateTime":1639463119495,"extra":{}},{"taskId":"T204","value":13,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T205","value":13,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T206","value":13,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T701","value":10,"status":3,"updateTime":1639463824536,"extra":{}},{"taskId":"T702","value":13,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T1001","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T1201","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T1301","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T1401","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T1501","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T1601","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T1602","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T1603","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T1604","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T1605","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T1701","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T2101","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T2201","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T2301","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T2501","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T301","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T302","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T303","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T304","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T401","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T402","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T403","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T404","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T501","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T502","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T503","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T504","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T505","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T506","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T5401","value":0,"status":1,"updateTime":1638982784890,"extra":{}},{"taskId":"T601","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T801","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T901","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T902","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T903","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T904","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T905","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T906","value":0,"status":1,"updateTime":1639463119495,"extra":{}},{"taskId":"T101","value":1639463119495,"status":1,"updateTime":1639463119495,"extra":{}}]}
 */
@NoArgsConstructor
@Data
public class WaterTreeResp {
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
     * awardItems
     */
    private List<?> awardItems;
    /**
     * plantInfo
     */
    private PlantInfoBean plantInfo;
    /**
     * nutrient
     */
    private NutrientBean nutrient;
    /**
     * taskList
     */
    @Deprecated
    private List<TaskListBean> taskList123;

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
     * PlantInfoBean
     */
    @NoArgsConstructor
    @Data
    public static class PlantInfoBean {

        /**
         * canUseTrapWater
         */
        private Integer canUseTrapWater;
        /**
         * trapWater
         */
        private Integer trapWater;
        /**
         * trapUseTime
         */
        private Long trapUseTime;
        /**
         * trapExpireTime
         */
        private Long trapExpireTime;
        /**
         * score
         */
        private Integer score;
        /**
         * dayWaterCount
         */
        private Integer dayWaterCount;
        /**
         * dayResetTime
         */
        private Long dayResetTime;
        /**
         * totalWaterCount
         */
        private Integer totalWaterCount;
        /**
         * latestWaterTime
         */
        private Long latestWaterTime;
    }

    /**
     * NutrientBean
     */
    @NoArgsConstructor
    @Data
    public static class NutrientBean {

        /**
         * ownNutrient
         */
        private Integer ownNutrient;
        /**
         * useNntrient
         */
        private Integer useNntrient;
        /**
         * saveWater
         */
        private Integer saveWater;
    }

    /**
     * TaskListBean
     */
    @NoArgsConstructor
    @Data
    public static class TaskListBean {

        /**
         * taskId
         */
        private String taskId;
        /**
         * value
         */
        private String value;
        /**
         * status
         */
        private Integer status;
        /**
         * updateTime
         */
        private Long updateTime;
        /**
         * extra
         */
        private ExtraBean extra;

        /**
         * ExtraBean
         */
        @NoArgsConstructor
        @Data
        public static class ExtraBean {

        }
    }
}
