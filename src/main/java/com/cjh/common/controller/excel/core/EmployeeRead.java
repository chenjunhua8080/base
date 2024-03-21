package com.cjh.common.controller.excel.core;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class EmployeeRead {

    private String id;
    private String name;
    private String department;
    private String isSingleDay;

    private Date joinTime;
    private Date leaveTime;
    private List<Date> resignation;
}
