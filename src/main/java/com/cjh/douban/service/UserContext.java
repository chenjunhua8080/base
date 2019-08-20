package com.cjh.douban.service;

import com.alibaba.fastjson.JSONObject;
import com.cjh.douban.po.DoubanPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserContext {

    @Autowired
    private RedisService redisService;

    public DoubanPO getUser() {
        String user = redisService.get("user", String.class);
        JSONObject jsonObject = JSONObject.parseObject(user);
        return jsonObject.toJavaObject(DoubanPO.class);
    }

    public String getUserName() {
        return getUser().getName();
    }

    public String getUserId() {
        return getUser().getId();
    }

    public void setUser(DoubanPO user) {
        redisService.set("user", JSONObject.toJSONString(user));
    }

    public void clearUser() {
        redisService.delete("user");
    }

}
