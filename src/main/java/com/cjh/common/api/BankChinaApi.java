package com.cjh.common.api;

import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.resp.BankChinaResp;
import com.cjh.common.resp.BankChinaResp.MsgBean;
import com.cjh.common.service.ReqLogService;
import com.cjh.common.util.HttpUtil;
import com.cjh.common.util.JsonUtil;
import com.cjh.common.util.XxlJobUtil;
import com.xxl.job.core.context.XxlJobHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 中银
 *
 * @author cjh
 * @date 2020/4/3
 */
@Component
@Slf4j
public class BankChinaApi {

    /**
     * @name 首页
     * @result html
     */
    private final String url_index = "https://gdwx.snrunning.com/yinyuehui_mobile/index/index/is_login/1/userid/USER/token/TOKEN.html";
    /**
     * @name 签到
     * @result {"status":200,"msg":{"day":3,"msg":"签到成功!"},"url":"","data":[{"name":"paco","url":"snrunning.com"}],"render":true}
     */
    private final String url_sign = "https://gdwx.snrunning.com/yinyuehui_mobile/task/signin.html";

    @Autowired
    private ReqLogService reqLogService;

    //#############################################################################

    /**
     * cookie 里存了cookie/token/userId
     */
    private static String[] parseCookie(String cookie) {
        String[] strings = new String[3];
        Pattern pattern;
        pattern = Pattern.compile("(.*?)\\[\\[");
        Matcher matcher = pattern.matcher(cookie);
        if (matcher.find()) {
            strings[0] = matcher.group(1);
        }
        pattern = Pattern.compile("\\[\\[token=(.*?)]]");
        matcher = pattern.matcher(cookie);
        if (matcher.find()) {
            strings[1] = matcher.group(1);
        }
        pattern = Pattern.compile("\\[\\[userId=(.*?)]]");
        matcher = pattern.matcher(cookie);
        if (matcher.find()) {
            strings[2] = matcher.group(1);
        }
        return strings;
    }

    /**
     * 首页
     */
    public String index(String openId, String cookie, boolean addLog) {
        String[] parseCookie = parseCookie(cookie);
        String url = url_index.replace("USER", parseCookie[2])
            .replace("TOKEN", parseCookie[1]);
        String resp = HttpUtil.doGet(url, parseCookie[0]);
        String result;
        if (resp.contains("integral")) {
            Document document = Jsoup.parse(resp);
            Element element = document.getElementById("integral");
            result = String.format("#### 当前豆豆个数: %s ####", element.html());
            XxlJobHelper.log(result);
        } else {
            result = "#### 豆豆数获取失败 ####";
            XxlJobUtil.showErrorLog(result);
        }
        if (addLog) {
            reqLogService.addLog(PlatformEnum.BANK_CHINA.getCode(), openId, result, null);
        }
        return result;
    }

    /**
     * 签到
     */
    public void sign(String openId, String cookie) {
        String[] parseCookie = parseCookie(cookie);
        Map<String, Object> headers = new HashMap<>();
        headers.put("cookie", parseCookie[0]);
        Map<String, Object> params = new HashMap<>();
        params.put("token", parseCookie[1]);
        params.put("userid", parseCookie[2]);
        params.put("task_id", 8);
        BankChinaResp resp = HttpUtil.doPost(url_sign, headers, params, BankChinaResp.class);
        String result;
        if (resp.getStatus() == 200) {
            MsgBean msg = JsonUtil.json2java(resp.getMsg().toString(), MsgBean.class);
            result = String.format("#### 签到结果: %s, 签到天数: %s ####", msg.getMsg(), msg.getDay());
            XxlJobHelper.log(result);
        } else {
            result = String.format("#### 签到失败, code: %s, msg: %S ####", resp.getStatus(), resp.getMsg());
            XxlJobUtil.showErrorLog(result);
        }
        reqLogService.addLog(PlatformEnum.BANK_CHINA.getCode(), openId, result, resp.toString());
    }


}
