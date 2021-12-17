package com.cjh.common.api;

import com.alibaba.fastjson.JSONObject;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.resp.UHomeResp;
import com.cjh.common.resp.UHomeResp.DataBean;
import com.cjh.common.service.ReqLogService;
import com.cjh.common.util.HttpUtil;
import com.cjh.common.util.XxlJobUtil;
import com.xxl.job.core.context.XxlJobHelper;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 有家便利店api
 *
 * @author cjh
 * @date 2021/7/28
 */
@Slf4j
@Component
public class UHomeApi {

    private final String url_sign = "https://vip.uh24.com.cn/uhome-member-server/task/signIn/memberSign?memberId=MEMBERID&city=CITY&phone=PHONE";

    @Autowired
    private ReqLogService reqLogService;

    /**
     * sign
     *
     * @param cookie memberId;city;phone;x-auth-token
     */
    public String sign(String openId, String cookie) {
        String[] split = cookie.split(";");
        Map<String, Object> headers = new HashMap<>();
        headers.put("Host", "vip.uh24.com.cn");
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("content-type", "application/x-www-form-urlencoded");
        headers.put("User-Agent",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        headers.put("x-auth-token", split[3]);
        String resp = HttpUtil.doGet(url_sign.replace("MEMBERID", split[0])
            .replace("CITY", split[1])
            .replace("PHONE", split[2]), headers);
        UHomeResp uHomeResp = JSONObject.parseObject(resp, UHomeResp.class);
        String result;
        if (uHomeResp.getCode() == 0) {
            DataBean data = uHomeResp.getData();
            if (data.getStatus() == 1) {
                result = String.format("#### 签到成功, 获得积分%s, 连续签到%s天 ####", data.getGiftNumber(), data.getDaySign());
                XxlJobHelper.log(result);
            } else {
                result = String.format("#### 签到失败, sign status %s ####", data.getStatus());
                XxlJobUtil.showErrorLog(result, openId);
            }
        } else {
            result = String.format("#### 签到失败, %s ####", uHomeResp.getMessage());
            XxlJobUtil.showErrorLog(result, openId);
        }
        reqLogService.addLog(PlatformEnum.UHOME.getCode(), openId, result, resp);

        return result;
    }
}
