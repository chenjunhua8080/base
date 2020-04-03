package com.cjh.common.api;

import com.cjh.common.api.ApiConfig.BankChinaConfig;
import com.cjh.common.resp.BankChinaResp;
import com.cjh.common.resp.BankChinaResp.MsgBean;
import com.cjh.common.service.ReqLogService;
import com.cjh.common.util.HttpUtil;
import com.cjh.common.util.JsonUtil;
import java.util.HashMap;
import java.util.Map;
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

    private ReqLogService reqLogService;
    private ApiConfig apiConfig;

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

    //#############################################################################

    /**
     * 浇水
     */
    public void index() {
        BankChinaConfig bankChinaConfig = apiConfig.getBankChinaConfig();
        String url = url_index.replace("USER", bankChinaConfig.getUserId())
            .replace("TOKEN", bankChinaConfig.getToken());
        String resp = HttpUtil.doGet(url, bankChinaConfig.getCookie());
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
        reqLogService.addLog(bankChinaConfig.getUserId(), result, resp);
    }

    /**
     * 签到
     */
    public void sign() {
        BankChinaConfig bankChinaConfig = apiConfig.getBankChinaConfig();
        Map<String, Object> headers = new HashMap<>();
        headers.put("cookie", bankChinaConfig.getCookie());
        Map<String, Object> params = new HashMap<>();
        params.put("userid", bankChinaConfig.getUserId());
        params.put("token", bankChinaConfig.getToken());
        params.put("task_id", bankChinaConfig.getTaskId());
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
        reqLogService.addLog(bankChinaConfig.getUserId(), result, resp.toString());
    }


}
