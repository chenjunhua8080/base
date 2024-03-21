package com.cjh.common.controller.excel.core;

import java.util.List;
import lombok.Data;

@Data
public class Clock {

    private String on;
    private String off;
    private List<String> list;
    private String raw;
}
