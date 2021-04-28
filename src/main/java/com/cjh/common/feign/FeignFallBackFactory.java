package com.cjh.common.feign;

import feign.hystrix.FallbackFactory;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignFallBackFactory implements FallbackFactory<CloudFeignClient> {

    @Override
    public CloudFeignClient create(Throwable throwable) {
        log.error("in FeignFallBackFactory...\n", throwable);
        return new CloudFeignClient() {
            @Override
            public String pushErrorMsg(String openId, String body) {
                return null;
            }

            @Override
            public String pushResumeMsg(Map<String, Object> map) {
                log.error("in pushResumeMsg...");
                return null;
            }
        };
    }
}
