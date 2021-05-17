package com.cjh.common.boss.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Avatar {


    /**
     * wxMp : false
     * headImg : 0
     * large : https://img.bosszhipin.com/beijin/upload/avatar/20210228/801716949d5961d40db3fb8bab62bc8fc5618714e92a9866b62c70a8b143a719.png
     * tiny : https://img.bosszhipin.com/beijin/upload/avatar/20210228/801716949d5961d40db3fb8bab62bc8fc5618714e92a9866b62c70a8b143a719_s.png
     */

    private Boolean wxMp;
    private Integer headImg;
    private String large;
    private String tiny;
}
