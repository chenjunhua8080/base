package com.cjh.common.controller;

import com.cjh.common.boss.BossService;
import com.cjh.common.boss.EmailService;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.feign.CloudFeignClient;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.service.ReqLogService;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class BossController {

    @Autowired
    private final CloudFeignClient cloudFeignClient;
    @Autowired
    private final BindFarmDao bindFarmDao;
    @Autowired
    private final ReqLogService reqLogService;
    @Autowired
    private final BossService bossService;

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
}
