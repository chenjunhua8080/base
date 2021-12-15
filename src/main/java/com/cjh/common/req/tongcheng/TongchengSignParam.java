package com.cjh.common.req.tongcheng;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TongchengSignParam {

    /**
     * unionId
     */
    private String unionId;
    /**
     * openId
     */
    private String openId;

}
