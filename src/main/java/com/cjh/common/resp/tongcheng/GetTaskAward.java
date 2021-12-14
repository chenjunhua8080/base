package com.cjh.common.resp.tongcheng;


import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GetTaskAward {

    /**
     * errMsg
     */
    private String errMsg;
    /**
     * code
     */
    private Integer code;
    /**
     * newTaskList
     */
    private List<NewTaskListBean> newTaskList;
    /**
     * items
     */
    private ItemsBean items;
    /**
     * awardItems
     */
    private List<String> awardItems;

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
     * NewTaskListBean
     */
    @NoArgsConstructor
    @Data
    public static class NewTaskListBean {

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

            /**
             * aaa
             */
            private String aaa;
        }
    }
}
