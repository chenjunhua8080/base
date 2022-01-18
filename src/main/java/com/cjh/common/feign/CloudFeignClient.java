package com.cjh.common.feign;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("wechat-mp")
public interface CloudFeignClient {

    //########################## 微信公众号 #############################

    /**
     * 推送错误消息
     */
    @GetMapping("/message/pushErrorMsg")
    String pushErrorMsg(@RequestParam String openId, @RequestParam String body);

    /**
     * 推送消息
     */
    @GetMapping("/message/tempPush")
    String tempPush(@RequestParam String openId, @RequestParam String body);

    /**
     * 推送下载简历消息
     */
    @PostMapping("/message/pushResumeMsg")
    String pushResumeMsg(@RequestBody Map<String, Object> map);

}
