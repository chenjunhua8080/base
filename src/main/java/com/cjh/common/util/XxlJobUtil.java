package com.cjh.common.util;

import cn.hutool.extra.spring.SpringUtil;
import com.cjh.common.feign.CloudFeignClient;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cjh
 * @date 2021/12/17
 */
@Slf4j
public class XxlJobUtil {

    /**
     * 本地输出log && xxl-job输出log
     *
     * @param msg msg
     */
    public static void showLog(String msg) {
        log.info(msg);
        XxlJobHelper.log(msg);
    }

    /**
     * 本地输出log && xxl-job输出log
     *
     * @param msg msg
     */
    public static void showErrorLog(String msg) {
        log.error(msg);
        XxlJobHelper.log(msg);
        XxlJobHelper.handleFail();
    }

    /**
     * 本地输出log && xxl-job输出log && 推送微信
     *
     * @param msg    msg
     * @param openId openId
     */
    public static void showErrorLog(String msg, String openId) {
        log.error(msg);
        XxlJobHelper.log(msg);
        XxlJobHelper.handleFail();
        CloudFeignClient feignClient = SpringUtil.getBean(CloudFeignClient.class);
        if (feignClient == null) {
            log.error("推送失败，获取CloudFeignClient为空");
            return;
        }
        feignClient.tempPush(openId, msg);
        XxlJobHelper.log("推送消息到微信：", openId);
    }

}
