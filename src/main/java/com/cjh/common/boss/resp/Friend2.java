package com.cjh.common.boss.resp;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Friend2 {

    private List<FriendListBean> friendList;

    @NoArgsConstructor
    @Data
    public static class FriendListBean {

        /**
         * friendSource : 0
         * securityId : cXW6tIYzcfrg1-41maMI9UB9BpIP2rXuflBB6sUdOp-833YICmeiZ3uIu29ocxOUM5KmN_15uLc-rLAv5a6yC78bMKyxI1u6OpFy_VL5xoYQacLoUfCNS_ORRl2dz5PsQ5qvVn3o4MEDHQtLaR1GKtfiC7WXZYv8M60o1CUUDyPMF23Y6hkO9W5e9RVmYQ3fJoR9NZn8l7Pe-pNpJNQayBX5TmZPKJ12a5Sqh5uqONagg-KjC9aHTij45_T-SLpLwMxYGlQl5nxmlGJUpLBWBKcJmriR-jP3AWhTq19zDjgIQoqg2CN3ifYskMVgxsbF-aSiYbsA7gg3JzEpH-lDN0nUPkwG1UrAYKSIMyNV57XWPR4LiXVR3LOh06POGSDslsESvigT2602IgyV4LwiXyO2qQ~~
         * name : 苏贵
         * avatar : https://img.bosszhipin.com/boss/avatar/avatar_12.png
         * isTop : 0
         * sourceTitle :
         * relationType : 5
         * lastMsg : null
         * lastMessageInfo : null
         * lastTime : 09:23
         * lastTS : 1620437035000
         * sourceType : 2
         * sourceExtend : null
         * jobId : 151429687
         * encryptJobId : null
         * itemType : 0
         * newGeek : 1
         * filterReasonList : null
         * expectId : 67589144
         * encryptExpectId : null
         * uid : 44429488
         * encryptUid : 181e39841f65d5cb03B-2dS5GFo~
         * isFiltered : false
         */

        private Integer friendSource;
        private String securityId;
        private String name;
        private String avatar;
        private Integer isTop;
        private String sourceTitle;
        private Integer relationType;
        private Object lastMsg;
        private Object lastMessageInfo;
        private String lastTime;
        private Long lastTS;
        private Integer sourceType;
        private Object sourceExtend;
        private Integer jobId;
        private Object encryptJobId;
        private Integer itemType;
        private Integer newGeek;
        private Object filterReasonList;
        private Integer expectId;
        private Object encryptExpectId;
        private String uid;
        private String encryptUid;
        private Boolean isFiltered;
    }
}
