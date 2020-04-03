package com.cjh.common.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 共用动态配置
 *
 * @author cjh
 * @date 2020/4/3
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "api")
@Data
public class ApiConfig {

    private FarmConfig farmConfig;
    private BankChinaConfig bankChinaConfig;

    @Data
    public static class FarmConfig {

        private String cookie;
    }

    @Data
    public static class BankChinaConfig {

        private String cookie;
        private String userId;
        private String token;
        private String taskId;
    }


}
