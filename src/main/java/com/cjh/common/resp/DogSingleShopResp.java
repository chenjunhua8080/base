package com.cjh.common.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DogSingleShopResp extends DogBaseResp {

    private ResultBean result;

    @NoArgsConstructor
    @Data
    public static class ResultBean {

        private int reward;
        private int foodAmount;
        private boolean finished;
    }
}
