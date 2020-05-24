package com.cjh.common.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignFallBackFactory implements FallbackFactory<CloudFeignClient> {

    @Override
    public CloudFeignClient create(Throwable throwable) {

        log.error("in FeignFallBackFactory...");
        throwable.printStackTrace();

        return (openId, body) -> null;
    }
}
