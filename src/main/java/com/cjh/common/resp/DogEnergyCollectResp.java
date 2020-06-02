package com.cjh.common.resp;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DogEnergyCollectResp extends DogBaseResp {

    private ResultBean result;

    @NoArgsConstructor
    @Data
    public static class ResultBean {

        private double medalPercent;
        private int needCollectEnergy;
        private int totalEnergy;
        private boolean showHongBaoExchangePop;
        private int medalNum;
        private boolean showMedalExchangePop;
        private List<PetPlaceInfoListBean> petPlaceInfoList;

        @NoArgsConstructor
        @Data
        public static class PetPlaceInfoListBean {

            private int place;
            private int energy;
        }
    }
}
