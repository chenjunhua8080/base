package com.cjh.common.test;

import com.cjh.common.api.DoubanApi;
import com.cjh.common.po.NowPlayingPO;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Disabled
@RefreshScope
@SpringBootTest
class TestController2 {

    @Value("${name:null}")
    private String username;
    @Autowired
    private DoubanApi doubanApi;
    @Autowired
    private RestTemplate restTemplate;

    static void main(String[] args) {
        //测试IDEA git v4 文件2
    }

    @GetMapping(value = "/getUser")
    String getUser() {
        return username;
    }

    @Test
    void login() {
        boolean nowPlaying = doubanApi.login();
    }

    @Test
    void getNowPlaying() {
        List<NowPlayingPO> nowPlaying = doubanApi.getNowPlaying();
        for (NowPlayingPO item : nowPlaying) {
            System.out.println(item);
        }
    }

    @GetMapping("/testApp")
    String testApp() {
        return restTemplate.getForObject("http://wechat/nacos", String.class);
    }

}
