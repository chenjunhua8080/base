package com.cjh.common.resp;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BankChinaResp {


    private int status;
    private Object msg;
    private String url;
    private boolean render;
    private List<DataBean> data;

    @NoArgsConstructor
    @Data
    public static class MsgBean {

        private int day;
        private String msg;
    }

    @NoArgsConstructor
    @Data
    public static class DataBean {

        private String name;
        private String url;
    }

}
