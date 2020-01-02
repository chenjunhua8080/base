package com.cjh.common.resp;

import lombok.Data;

@Data
public class FarmResp {

    private int amount;
    private int totalEnergy;
    private int sendAmount;
    private int treeEnergy;
    private int waterStatus;
    private boolean finished;
    private int code;
    private TodayGotWaterGoalTaskBean todayGotWaterGoalTask;
    private Object statisticsTimes;
    private int signDay;
    private long sysTime;
    private String message;

    @Data
    public static class TodayGotWaterGoalTaskBean {

        private boolean canPop;
    }
}
