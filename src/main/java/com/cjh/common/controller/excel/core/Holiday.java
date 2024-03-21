package com.cjh.common.controller.excel.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Holiday {

    private String name;
    private Integer month;
    private Integer day;

    private boolean isOvertime;

    public Holiday(String name, Integer month, Integer day) {
        this.name = name;
        this.month = month;
        this.day = day;
    }
}
