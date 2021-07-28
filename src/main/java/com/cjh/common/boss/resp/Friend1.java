package com.cjh.common.boss.resp;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Friend1 {

    private List<ResultBean> result;
    private List<?> filterGeekIdList;
    private List<?> filterEncryptIdList;

    @NoArgsConstructor
    @Data
    public static class ResultBean {

        /**
         * encryptFriendId : 181e39841f65d5cb03B-2dS5GFo~
         * friendId : 44429488
         * friendSource : 0
         * name : 苏贵
         * updateTime : 1620437035000
         */

        private String encryptFriendId;
        private String friendId;
        private String friendSource;
        private String name;
        private String updateTime;
    }
}
