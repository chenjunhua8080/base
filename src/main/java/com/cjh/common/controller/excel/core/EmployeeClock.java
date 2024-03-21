package com.cjh.common.controller.excel.core;

import java.util.List;
import lombok.Data;

@Data
public class EmployeeClock {

    private Employee employee;
    private List<ClockByDay> clockList;

    @Data
    public static class ClockByDay {

        private DayOfMonth dayOfMonth;
        private Clock clock;
    }

}
