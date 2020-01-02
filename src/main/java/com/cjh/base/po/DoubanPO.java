package com.cjh.base.po;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DoubanPO {

    private String name;
    private boolean weixinBinded;
    private String phone;
    private AvatarBean avatar;
    private String id;
    private String uid;

    private String dbcl2;
    private String bid;

    @NoArgsConstructor
    @Data
    public static class AvatarBean {

        private String medium;
        private String median;
        private String large;
        private String raw;
        private String small;
        private String icon;
    }
}
