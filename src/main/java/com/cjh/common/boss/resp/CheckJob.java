package com.cjh.common.boss.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CheckJob {

    /**
     * checkJobOpenResult : {"needOpen":false,"encJid":"6b5bf76db6a660201nF73969FFtX","jobName":null,"jobCity":null,"jobSalary":null}
     */

    private CheckJobOpenResultBean checkJobOpenResult;

    @NoArgsConstructor
    @Data
    public static class CheckJobOpenResultBean {

        /**
         * needOpen : false
         * encJid : 6b5bf76db6a660201nF73969FFtX
         * jobName : null
         * jobCity : null
         * jobSalary : null
         */

        private Boolean needOpen;
        private String encJid;
        private Object jobName;
        private Object jobCity;
        private Object jobSalary;

    }
}
