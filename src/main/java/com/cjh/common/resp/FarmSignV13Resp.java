package com.cjh.common.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FarmSignV13Resp {

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
     * signDay
     */
    private Integer signDay;
    /**
     * sysTime
     */
    private Long sysTime;
    /**
     * message
     */
    private String message;

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
