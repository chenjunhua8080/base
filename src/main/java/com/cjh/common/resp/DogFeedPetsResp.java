package com.cjh.common.resp;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DogFeedPetsResp extends DogBaseResp {

    private ResultBean result;

    @NoArgsConstructor
    @Data
    public static class ResultBean {

        private int petSportStatus;
        private int feedAmount;
        private int foodAmount;
        private int firstFeedReward;
        private int feedReachReward;
        private boolean showNeedCollectPop;
        private boolean firstFeedFinishedPop;
        private boolean feedReachFinishedPop;
        private boolean showForceCollectPop;
        private List<PetPlaceInfoListBean> petPlaceInfoList;

        @NoArgsConstructor
        @Data
        public static class PetPlaceInfoListBean {

            private int place;
            private int energy;
        }
    }
}
