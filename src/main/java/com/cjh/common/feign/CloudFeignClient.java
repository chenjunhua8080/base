package com.cjh.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wechat-mp", fallbackFactory = FeignFallBackFactory.class)
public interface CloudFeignClient {

    //########################## 微信公众号 #############################

    /**
     * 推送错误消息
     */
    @GetMapping("/message/pushErrorMsg")
    String pushErrorMsg(@RequestParam String openId, @RequestParam String body);
}
