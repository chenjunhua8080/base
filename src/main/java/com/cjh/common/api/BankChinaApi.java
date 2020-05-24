package com.cjh.common.api;

import com.cjh.common.resp.BankChinaResp;
import com.cjh.common.resp.BankChinaResp.MsgBean;
import com.cjh.common.service.ReqLogService;
import com.cjh.common.util.HttpUtil;
import com.cjh.common.util.JsonUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

/**
 * 中银
 *
 * @author cjh
 * @date 2020/4/3
 */
@AllArgsConstructor
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
    private ReqLogService reqLogService;

    //#############################################################################

    /**
     * cookie 里存了cookie/token/userId
     */
    private static String[] parseCookie(String cookie) {
        String[] strings = new String[3];
        Pattern pattern = Pattern.compile("(.*?)\\[\\[");
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

    public static void main(String[] args) {
        String cookie = "cookie: acw_tc=7b39758815856454135256899e1a43c877e82bba292978fb2f1df3480ac466; hide_dialog=1[[token=5c9613b1b5d3c0b432b0315259b43e1d]][[userId=b556e5c5c5297a05]]";
        log.info(parseCookie(cookie)[0]);
        log.info(parseCookie(cookie)[1]);
        log.info(parseCookie(cookie)[2]);
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
            log.info(result);
        } else {
            result = "#### 豆豆数获取失败 ####";
            log.error(result);
        }
        if (addLog) {
            reqLogService.addLog(2, openId, result, null);
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
            log.info(result);
        } else {
            result = String.format("#### 签到失败, code: %s, msg: %S ####", resp.getStatus(), resp.getMsg());
            log.error(result);
        }
        reqLogService.addLog(2, openId, result, resp.toString());
    }


}
