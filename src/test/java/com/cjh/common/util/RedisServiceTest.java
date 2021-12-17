package com.cjh.common.util;

import com.cjh.common.redis.RedisService;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisServiceTest {

    private static final String key = "testkey";
    private static String value;
    @Autowired
    private RedisService redisService;

    @Test
    void test() throws InterruptedException {
        value = "1";
        redisService.setToday(key, "1");
        System.out.println("设置key: " + key + ", value: " + value);
        System.out.println("剩余时间：" + redisService.getExpire(key));
        redisService.delete(key);
        System.out.println("删除: " + key);
        System.out.println("再获取: " + redisService.get(key));
        redisService.set(key, "1", 60);
        System.out.println("再设置带时间, key: " + key + ", value: " + value);
        System.out.println("剩余时间：" + redisService.getExpire(key));
        redisService.set(key, "1", 1, TimeUnit.HOURS);
        System.out.println("再设置带时间带单位, key: " + key + ", value: " + value);
        System.out.println("剩余时间：" + redisService.getExpire(key));
        TimeUnit.SECONDS.sleep(1L);
        redisService.updateValue(key, "1");
        System.out.println("设置key: " + key + ", value: " + value);
        System.out.println("剩余时间：" + redisService.getExpire(key));
        System.out.println("更新时间: " + redisService.expire(key, 3, TimeUnit.SECONDS));
        TimeUnit.SECONDS.sleep(1L);
        System.out.println("剩余时间：" + redisService.getExpire(key));
        System.out.println("再获取: " + redisService.get(key));
        for (; ; ) {
            if (redisService.get(key) == null) {
                System.out.println("已过期: " + key);
                return;
            }
        }
    }
}