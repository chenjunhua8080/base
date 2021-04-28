package com.cjh.common.controller;

import com.cjh.common.boss.EmailService;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.feign.CloudFeignClient;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.service.ReqLogService;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getResumeZip")
    public Map<String, Object> getResumeZip(@RequestParam("openId") String openId) {
        Map<String, Object> map = new HashMap<>();
        String msg = null;
        BindFarmPO bindFarm = bindFarmDao.selectByOpenId(openId, PlatformEnum.BOSS_EMAIL.getCode());
        if (bindFarm == null) {
            msg = cloudFeignClient.pushErrorMsg(openId, "您还未绑定下载简历的邮箱！");
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

}
