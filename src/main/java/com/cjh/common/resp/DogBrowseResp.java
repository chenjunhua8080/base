package com.cjh.common.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DogBrowseResp extends DogBaseResp {

    private ResultBean result;

    @NoArgsConstructor
    @Data
    public static class ResultBean {

        private int foodAmount;
        private int reward;
        /**
         * 1浏览成功，2领取成功
         */
        private int status;
    }
}
