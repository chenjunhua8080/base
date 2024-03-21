package com.cjh.common.controller.excel.core;

import lombok.Data;

@Data
public class DayOfMonth {

    private Integer month;
    private Integer day;
    private String week;

    private boolean isSingleDay;
    private boolean isWeekend;

    private Holiday holiday;

}
