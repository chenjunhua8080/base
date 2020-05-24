package com.cjh.common.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CollectCakeResp {

    /**
     * code : 0
     * data : {"bizCode":0,"bizMsg":"success","result":{"maxTimes":30,"score":"7","taskStatus":1,"times":17,"userScore":"1233"},"success":true}
     * msg : 调用成功
     */

    private int code;
    private DataBean data;
    private String msg;

    @NoArgsConstructor
    @Data
    public static class DataBean {

        /**
         * bizCode : 0
         * bizMsg : success
         * result : {"maxTimes":30,"score":"7","taskStatus":1,"times":17,"userScore":"1233"}
         * success : true
         */

        private int bizCode;
        private String bizMsg;
        private ResultBean result;
        private boolean success;

        @NoArgsConstructor
        @Data
        public static class ResultBean {

            /**
             * maxTimes : 30
             * score : 7
             * taskStatus : 1
             * times : 17
             * userScore : 1233
             */

            private int maxTimes;
            private String score;
            private int taskStatus;
            private int times;
            private String userScore;
        }
    }
}
