package com.cjh.common.req.coffee;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CoffeeCookie {

    /**
     * openid
     */
    private String openid;
    /**
     * unionid
     */
    private String unionid;
    /**
     * name
     */
    private String name;
    /**
     * avatar
     */
    private String avatar;
    /**
     * path
     */
    private String path;
}
