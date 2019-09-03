package com.cjh.douban.test;

import com.cjh.douban.po.NowPlayingPO;
import com.cjh.douban.service.DoubanService;
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
    private DoubanService doubanService;

    @Test
    public void login() {
        boolean nowPlaying = doubanService.login();
    }

    @Test
    public void getNowPlaying() {
        List<NowPlayingPO> nowPlaying = doubanService.getNowPlaying();
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
        //测试IDEA git v5
    }

}
