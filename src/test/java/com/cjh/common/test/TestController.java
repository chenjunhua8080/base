package com.cjh.common.test;

import com.cjh.common.po.NowPlayingPO;
import com.cjh.common.api.DoubanApi;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestController {

    @Value("${name:null}")
    private String username;

    @GetMapping(value = "/getUser")
    public String getUser() {
        return username;
    }

    @Autowired
    private DoubanApi doubanApi;

    @Test
    public void login() {
        boolean nowPlaying = doubanApi.login();
    }

    @Test
    public void getNowPlaying() {
        List<NowPlayingPO> nowPlaying = doubanApi.getNowPlaying();
        for (NowPlayingPO item : nowPlaying) {
            System.out.println(item);
        }
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/testApp")
    public String testApp() {
        return restTemplate.getForObject("http://wechat/nacos", String.class);
    }


    public static void main(String[] args) {
        //测试IDEA git v6
    }

}
