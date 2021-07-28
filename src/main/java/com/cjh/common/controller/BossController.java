package com.cjh.common.controller;

import com.cjh.common.api.BossApi;
import com.cjh.common.boss.BossService;
import com.cjh.common.boss.EmailService;
import com.cjh.common.boss.resp.Cookie;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.dao.UserDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.feign.CloudFeignClient;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.po.UserPO;
import com.cjh.common.service.ReqLogService;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BossController {

    @Autowired
    private CloudFeignClient cloudFeignClient;
    @Autowired
    private BindFarmDao bindFarmDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ReqLogService reqLogService;
    @Autowired
    private BossService bossService;
    @Autowired
    private BossApi bossApi;

    @GetMapping("/getResumeZip")
    public Map<String, Object> getResumeZip(@RequestParam("openId") String openId) {
        Map<String, Object> map = new HashMap<>();
        String msg = null;
        BindFarmPO bindFarm = bindFarmDao.selectByOpenId(openId, PlatformEnum.BOSS_EMAIL.getCode());
        if (bindFarm == null) {
            msg = cloudFeignClient.pushErrorMsg(openId, "您还未绑定下载简历的邮箱！");
            map.put("msg", msg);
            return map;
        }
        try {
            String[] cookie = bindFarm.getCookie().split(";");
            map = EmailService.getResumeZip(cookie[0], cookie[1]);
            if (Integer.parseInt(map.get("count").toString()) == 0) {
                msg = "暂无简历，请稍后再试";
            }
        } catch (Exception e) {
            e.printStackTrace();
            //参数有http导致请求错误
//            msg = "下载简历失败：" + e.getMessage();
            msg = "下载简历失败了ToT...\n" + e.getMessage();
        }
        map.put("msg", msg);

        Map<String, Object> req = new HashMap<>();
        req.put("openId", openId);
        if (map.get("msg") == null) {
            req.put("link", map.get("link"));
            req.put("body", "本次共下载" + map.get("count") + "份简历哦！");
        } else {
            req.put("body", map.get("msg"));
        }
        cloudFeignClient.pushResumeMsg(req);

        reqLogService.addLog(PlatformEnum.BOSS_EMAIL.getCode(), openId,
            map.get("msg") == null ? map.get("link").toString() : map.get("msg").toString(), null);

        return map;
    }

    @GetMapping("/acceptResume")
    public Map<String, Object> acceptResume(@RequestParam("openId") String openId) {
        Map<String, Object> map = new HashMap<>();
        String msg = null;
        BindFarmPO bindFarm = bindFarmDao.selectByOpenId(openId, PlatformEnum.BOSS.getCode());
        if (bindFarm == null) {
            msg = cloudFeignClient.pushErrorMsg(openId, "您还未绑定BOSS账号！");
            map.put("msg", msg);
            return map;
        }
        List<String> list = Lists.newArrayList();
        try {
            list = bossService.acceptResume(bindFarm.getCookie());
            if (CollectionUtils.isEmpty(list)) {
                msg = "暂无待签收的简历，请稍后再试";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "签收简历异常ToT...\n" + e.getMessage();
        }
        map.put("msg", msg);

        Map<String, Object> req = new HashMap<>();
        req.put("openId", openId);
        if (map.get("msg") == null) {
//            req.put("link", "http://");
            req.put("body", "本次共签收" + list.size() + "份简历哦！\n" + String.join(",", list));
        } else {
            req.put("body", map.get("msg"));
        }
        cloudFeignClient.pushResumeMsg(req);

        reqLogService.addLog(PlatformEnum.BOSS.getCode(), openId,
            map.get("msg") == null ? map.get("body").toString() : map.get("msg").toString(), null);

        return map;
    }

    @GetMapping("/findGeek")
    public Map<String, Object> findGeek(@RequestParam("openId") String openId) {
        Map<String, Object> map = new HashMap<>();
        String msg = null;
        BindFarmPO bindFarm = bindFarmDao.selectByOpenId(openId, PlatformEnum.BOSS.getCode());
        if (bindFarm == null) {
            msg = cloudFeignClient.pushErrorMsg(openId, "您还未绑定BOSS账号！");
            map.put("msg", msg);
            return map;
        }
        List<String> list = Lists.newArrayList();
        try {
            list = bossService.findGeek(bindFarm.getCookie());
            if (CollectionUtils.isEmpty(list)) {
                msg = "暂无符合条件的牛人，请稍后再试";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "搜索牛人异常ToT...\n" + e.getMessage();
        }
        map.put("msg", msg);

        Map<String, Object> req = new HashMap<>();
        req.put("openId", openId);
        if (map.get("msg") == null) {
//            req.put("link", "http://");
            req.put("body", "本次共发起沟通" + list.size() + "个牛人哦！\n" + String.join(",", list));
        } else {
            req.put("body", map.get("msg"));
        }
        cloudFeignClient.pushResumeMsg(req);

        reqLogService.addLog(PlatformEnum.BOSS.getCode(), openId,
            map.get("msg") == null ? map.get("body").toString() : map.get("msg").toString(), null);

        return map;
    }

    @SneakyThrows
    @GetMapping("/loginBoss")
    public Map<String, Object> loginBoss(@RequestParam("openId") String openId) {
        reqLogService.addLog(PlatformEnum.BOSS.getCode(), openId, "绑定BOSS", null);

        String qrcodeKey = bossService.getQrcodeKey();
        String qrcodeDownPath = bossService.getQrcodeDownPath(qrcodeKey);

        Map<String, Object> map = new HashMap<>();
        map.put("openId", openId);
        map.put("body", "1、请点击此消息跳转到二维码页面进行扫码登录！");
        map.put("link", qrcodeDownPath);
        cloudFeignClient.pushResumeMsg(map);

        Thread.sleep(2000);
        for (int i = 0; i <= BossService.TIMEOUT_COUNT; i++) {
            if (i == BossService.TIMEOUT_COUNT) {
                log.error("扫码超时了...");
                cloudFeignClient.pushErrorMsg(openId, "扫码超时了，请重新发起绑定");
                return map;
            }
            boolean scanOk = bossApi.scanOk(qrcodeKey);
            if (scanOk) {
                log.info("扫码成功");
                String headImg = bossApi.getHeadImg(qrcodeKey);
                log.info("获取头像成功\n{}", headImg);
                map.put("body", "2、扫码成功，请尽快确认登录！");
                map.put("link", headImg);
                cloudFeignClient.pushResumeMsg(map);
                break;
            }
        }

        Thread.sleep(2000);
        for (int i = 0; i <= BossService.TIMEOUT_COUNT; i++) {
            if (i == BossService.TIMEOUT_COUNT) {
                cloudFeignClient.pushErrorMsg(openId, "登录超时了，请重新发起绑定");
                return map;
            }
            Thread.sleep(2000);
            boolean loginOk = bossApi.scanAndLoginOk(qrcodeKey);
            if (loginOk) {
                log.info("登录成功");
                Cookie cookie = bossApi.getDispatcher(qrcodeKey);
                log.info("获取cookie成功\n{}", cookie);
                bossApi.addLog(cookie.toString(), BossApi.log_login);

                Integer id = doBind(openId, cookie.toString(), PlatformEnum.BOSS.getCode());
                map.put("body", "登录成功，完成绑定！id：" + id);
                cloudFeignClient.pushResumeMsg(map);
                break;
            }
        }

        return map;
    }

    private Integer doBind(String openId, String cookie, Integer platformType) {
        UserPO user = userDao.selectByOpenId(openId);
        BindFarmPO old = bindFarmDao.getBindUser(user.getId(), platformType);
        if (old != null) {
            bindFarmDao.deleteById(old.getId());
        }
        BindFarmPO entity = new BindFarmPO();
        entity.setUserId(user.getId());
        entity.setPlatformType(platformType);
        entity.setPlatformId(openId);
        entity.setCookie(cookie);
        bindFarmDao.insert(entity);
        return entity.getId();
    }


}
