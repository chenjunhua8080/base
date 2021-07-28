package com.cjh.common.boss.resp;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RecJobList {


    /**
     * unpaidJobList
     */
    private List<UnpaidJobListBean> unpaidJobList;
    /**
     * onlineJobList
     */
    private List<OnlineJobListBean> onlineJobList;

    /**
     * UnpaidJobListBean
     */
    @NoArgsConstructor
    @Data
    public static class UnpaidJobListBean {

        /**
         * encryptId
         */
        private String encryptId;
        /**
         * jobName
         */
        private String jobName;
        /**
         * brandName
         */
        private Object brandName;
        /**
         * proxyJob
         */
        private Integer proxyJob;
        /**
         * locationName
         */
        private String locationName;
        /**
         * salaryDesc
         */
        private String salaryDesc;
    }

    /**
     * OnlineJobListBean
     */
    @NoArgsConstructor
    @Data
    public static class OnlineJobListBean {

        /**
         * encryptId
         */
        private String encryptId;
        /**
         * jobName
         */
        private String jobName;
        /**
         * brandName
         */
        private Object brandName;
        /**
         * proxyJob
         */
        private Integer proxyJob;
        /**
         * locationName
         */
        private String locationName;
        /**
         * salaryDesc
         */
        private String salaryDesc;
    }

}
