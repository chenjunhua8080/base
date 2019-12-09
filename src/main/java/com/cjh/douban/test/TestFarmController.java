package com.cjh.douban.test;

import com.cjh.douban.service.FarmService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestFarmController {

    @Value("${douban.user.name}")
    private String username;

    @Test
    public void getUser() {
        System.out.println(username);
    }

    @Autowired
    private FarmService farmService;

    @Test
    public void gotThreeMealForFarm() {
        farmService.gotThreeMealForFarm();
    }

    @Test
    public void signForFarm() {
        farmService.signForFarm();
    }

    @Test
    public void waterGoodForFarm() {
        farmService.waterGoodForFarm();
    }

    @Test
    public void firstWaterTaskForFarm() {
        farmService.firstWaterTaskForFarm();
    }

}
