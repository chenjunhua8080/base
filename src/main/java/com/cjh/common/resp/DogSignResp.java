package com.cjh.common.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DogSignResp extends DogBaseResp {

    private ResultBean result;

    @NoArgsConstructor
    @Data
    public static class ResultBean {

        private String couponImage;
        private int signReward;
        private int foodAmount;
        private int signDay;
    }
}
