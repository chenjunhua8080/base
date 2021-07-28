package com.cjh.common.boss;

import com.alibaba.fastjson.JSON;
import com.cjh.common.api.BossApi;
import com.cjh.common.boss.resp.Cookie;
import com.cjh.common.boss.resp.Friend1.ResultBean;
import com.cjh.common.boss.resp.Friend2.FriendListBean;
import com.cjh.common.boss.resp.GeekInfo;
import com.cjh.common.boss.resp.GeekInfo.DataBean;
import com.cjh.common.boss.resp.HistoryMsg.MessagesBean;
import com.cjh.common.boss.resp.NewGeek.NewGeekItemBean;
import com.cjh.common.boss.resp.NewGeek.NewGeekItemBean.NewGeekCardBean;
import com.cjh.common.boss.resp.RecJobList;
import com.cjh.common.boss.resp.RecJobList.OnlineJobListBean;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * boss
 *
 * @author cjh
 * @date 2021/5/7
 */
@Slf4j
@Component
public class BossService {

    public static final int TIMEOUT_COUNT = 5;

    @Autowired
    private BossApi bossApi;

    /**
     * getQrcodeKey
     */
    public String getQrcodeKey() {
        return bossApi.getQrcodeKey();
    }

    /**
     * getQrcodeDownPath
     */
    public String getQrcodeDownPath(String qrcodeKey) {
        String qrcodePath = bossApi.getQrcodePath(qrcodeKey);
//        /root/resume/boss/2021-5-17_1/xxx.jprg
        return "http://resume.springeasy.cn/" + qrcodePath.replaceAll("^.*?boss", "boss");
    }

    /**
     * 登录流程
     */
    @SneakyThrows
    public Cookie login() {
        String qrcodeKey = bossApi.getQrcodeKey();
        String qrcodePath = bossApi.getQrcodePath(qrcodeKey);
        log.info("获取二维码成功\n{}", qrcodePath);
        for (int i = 0; i <= TIMEOUT_COUNT; i++) {
            if (i == TIMEOUT_COUNT) {
                log.error("扫码超时了...");
                return null;
            }
            boolean scanOk = bossApi.scanOk(qrcodeKey);
            if (scanOk) {
                log.info("扫码成功");
                String headImg = bossApi.getHeadImg(qrcodeKey);
                log.info("获取头像成功\n{}", headImg);
                break;
            }
        }
        for (int i = 0; i <= TIMEOUT_COUNT; i++) {
            if (i == TIMEOUT_COUNT) {
                log.error("登录超时了...");
                return null;
            }
            Thread.sleep(2000);
            boolean loginOk = bossApi.scanAndLoginOk(qrcodeKey);
            if (loginOk) {
                log.info("登录成功");
                Cookie cookie = bossApi.getDispatcher(qrcodeKey);
                log.info("获取cookie成功\n{}", cookie);
                bossApi.addLog(cookie.toString(), BossApi.log_login);
                return cookie;
            }
        }
        return null;
    }

    /**
     * 收简历流程
     * <p>
     * TODO 标记消息已读
     */
    public List<String> acceptResume(String cookie) {
        List<String> list = Lists.newArrayList();

        //1、获取朋友列表基本信息
        List<ResultBean> friendSimpleList = bossApi.getFriendSimpleList(cookie);
        if (CollectionUtils.isEmpty(friendSimpleList)) {
            log.error("getFriendSimpleList result isEmpty");
            return list;
        }
        List<String> friendIdList = Lists.newArrayList();
        for (ResultBean resultBean : friendSimpleList) {
            friendIdList.add(resultBean.getFriendId());
        }
        //只取前100个
        if (friendIdList.size() > 100) {
            friendIdList = friendIdList.subList(0, 10);
        }

        //2、获取朋友列表完整信息
        List<FriendListBean> friendFullList = bossApi.getFriendFullList(cookie, friendIdList);
        if (CollectionUtils.isEmpty(friendFullList)) {
            log.error("getFriendFullList result isEmpty");
            return list;
        }

        //3、遍历朋友，获取消息
        for (FriendListBean friendListBean : friendFullList) {

            //################### 更真实模拟 这部分可以不要 s###################
            //同步朋友状态
            try {
                bossApi.syncFriendStatusInfo(cookie, friendListBean.getUid());
                //获取牛人信息
                GeekInfo geekInfo = bossApi
                    .getGeekInfo(cookie, friendListBean.getUid(), friendListBean.getSecurityId());
                DataBean data = geekInfo.getData();
                //bossEnter
                bossApi.bossEnter(cookie, data.getEncryptUid(), data.getEncryptExpectId(),
                    data.getToPositionId(), data.getSecurityId());
                //添加聊天点击日志
                bossApi.addLog(cookie, BossApi.log_click_chat.replace("FRIENDID", friendListBean.getUid()));
            } catch (Exception e) {
                log.error("真实模拟发生异常", e);
            }
            //################### 更真实模拟 这部分可以不要 e###################

            //获取消息
            List<MessagesBean> messagesList = bossApi.getHistoryMsg(cookie, friendListBean.getUid());
            if (CollectionUtils.isEmpty(messagesList)) {
                log.warn("getHistoryMsg result isEmpty: {}", friendListBean.getName());
                continue;
            }
            //4、如果有发送简历申请，并且没有接收过的，就同意
            String msgAll = JSON.toJSONString(messagesList);
            if (msgAll.contains("对方想发送附件简历到您邮箱") && !msgAll.contains("对方已投递简历")) {
                //找到发送简历申请的消息Id
                String msgId = null;
                for (MessagesBean messagesBean : messagesList) {
                    if (messagesBean.getBody().getType() == 7) {
                        log.info(messagesBean.getPushText());
                        msgId = String.valueOf(messagesBean.getMid());
                        break;
                    }
                }
                if (StringUtils.isEmpty(msgId)) {
                    log.warn("msgId isEmpty in: {}", msgAll);
                } else {
                    //5、接收简历
                    log.info("接收{}的简历...", friendListBean.getName());
                    bossApi.exchangeAccept(cookie, msgId, friendListBean.getSecurityId());

                    list.add(friendListBean.getName());
                }
            }
        }
        return list;
    }


    /**
     * 找牛人流程
     * <p>
     * TODO 会被解析行为异常
     */
    @SneakyThrows
    public List<String> findGeek(String cookie) {
        List<String> list = Lists.newArrayList();

        //添加日志
        addPcActiveLog(cookie);

        //1、获取我的岗位列表
        RecJobList recJobList = bossApi.getRecJobList(cookie);
        List<OnlineJobListBean> onlineJobList = recJobList.getOnlineJobList();
        if (CollectionUtils.isEmpty(onlineJobList)) {
            log.error("onlineJobList isEmpty");
            return list;
        }

        //2、遍历在线的岗位
        for (OnlineJobListBean onlineJob : onlineJobList) {
            //5分钟一个岗位
            //添加日志
            addPcActiveLog(cookie);
            Thread.sleep(60 * 1000L);
            //添加日志
            addPcActiveLog(cookie);
            Thread.sleep(60 * 1000L);
            //添加日志
            addPcActiveLog(cookie);
            Thread.sleep(60 * 1000L);
            //添加日志
            addPcActiveLog(cookie);
            Thread.sleep(60 * 1000L);
            //添加日志
            addPcActiveLog(cookie);
            Thread.sleep(60 * 1000L);
            log.info("正在查询[{}]的牛人...", onlineJob.getJobName());

//            3、获取牛人列表 - 系统推荐
//            List<GeekListBean> geekList = bossApi.getRecommendGeek(cookie, onlineJob.getEncryptId());
//            if (CollectionUtils.isEmpty(geekList)) {
//                log.error("getRecommendGeek result isEmpty");
//                continue;
//            }
//            4、过滤牛人
//            for (GeekListBean geek : geekList) {
//                GeekCardBean geekCard = geek.getGeekCard();
//                //应届生、5K及以下、23岁及以下
//                if (geekCard.getGeekWorkYear().contains("应届生")
//                    && geekCard.getHighSalary() <= 5
//                    && Integer.parseInt(geekCard.getAgeDesc().replace("岁", "")) <= 23) {
//
//                    //1分钟一次沟通
//                    Thread.sleep(60 * 1000L);
//                    //5发起沟通
//                    bossApi.startChat(cookie,
//                        geekCard.getEncryptGeekId(),
//                        geekCard.getEncryptJobId(),
//                        geekCard.getLid(),
//                        geekCard.getExpectId(),
//                        geekCard.getSecurityId()
//                    );
//
//                    return;
//                }
//            }

            //3、获取牛人列表 - 新牛人
            List<NewGeekItemBean> geekList = bossApi.getNewGeekList(cookie, onlineJob.getEncryptId());
            if (CollectionUtils.isEmpty(geekList)) {
                log.error("getRecommendGeek result isEmpty");
                continue;
            }

            //4、过滤牛人
            for (NewGeekItemBean geek : geekList) {
                NewGeekCardBean geekCard = geek.getGeekCard();
                //应届生、5K及以下、23岁及以下,talkTimeDesc: "15天前由您发起沟通"
                if ((geekCard.getGeekWorkYear().contains("应届生")
                    || geekCard.getGeekWorkYear().contains("1年以内")
                    || geekCard.getGeekWorkYear().contains("1年"))
                    && (
                    "面议".equals(geekCard.getSalary())
                        || Integer.parseInt(geekCard.getSalary().replace("K", "").split("-")[1]) <= 5)
                    && geekCard.getTalkTimeDesc() == null
                    && Integer.parseInt(geekCard.getAgeDesc().replace("岁", "")) <= 23) {

                    //1分钟一次沟通
                    Thread.sleep(60 * 1000L);
                    //添加日志
                    addPcActiveLog(cookie);
                    //5发起沟通
                    bossApi.startChat(cookie,
                        geekCard.getEncryptGeekId(),
                        geekCard.getEncryptJobId(),
                        geekCard.getLid(),
                        geekCard.getExpectId(),
                        geekCard.getSecurityId()
                    );

                    list.add(onlineJob.getJobName()
                        + "|" + geekCard.getGeekName()
                        + "|" + geekCard.getSalary()
                        + "|" + geekCard.getAgeDesc()
                        + "|" + geekCard.getGeekWorkYear()
                    );
                }
            }
        }
        return list;
    }

    /**
     * add log
     *
     * @author cjh
     * @date 2021/5/17
     */
    public void addPcActiveLog(String cookie) {
        bossApi.addLog(cookie, BossApi.log_pc_active);
    }

}
