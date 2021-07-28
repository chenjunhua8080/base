package com.cjh.common.boss.resp;

import lombok.Data;

@Data
public class Cookie {

    private String wt2;
    private String toUrl;

    @Override
    public String toString() {
        return String.format("wt2=%s;toUrl=%s;", wt2, toUrl);
    }
}
