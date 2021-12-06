package com.cjh.common.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {
 * "amount": 8,
 * "code": "0",
 * "todayGotWaterGoalTask": {"canPop": false},
 * "statisticsTimes": null,
 * "sysTime": 1638760148160,
 * "message": null
 * }
 */
@NoArgsConstructor
@Data
public class FarmTaskResp {

    /**
     * amount
     */
    private Integer amount;
    /**
     * code
     */
    private Integer code;
    /**
     * todayGotWaterGoalTask
     */
    private TodayGotWaterGoalTaskBean todayGotWaterGoalTask;
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
}
