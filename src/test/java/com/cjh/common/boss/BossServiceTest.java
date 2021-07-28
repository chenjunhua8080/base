package com.cjh.common.boss;

import com.cjh.common.api.BossApi;
import com.cjh.common.boss.resp.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class BossServiceTest {

    @Autowired
    private BossService bossService;
    @Autowired
    private BossApi bossApi;

    @BeforeEach
    public void setUp() {
        System.out.println("");
    }

    @Test
    public void login() {
        bossService.login();
    }

    @Test
    public void acceptResume() {
//        Cookie cookie = bossService.login();
        Cookie cookie = new Cookie();
        cookie.setWt2("DRW20V34V5a_VN8CL0ebxqyFU2uVleHwFupRUOdBsVxRkFEcqiw_pmnjD1Ls3lBNvbRuurfamdm1nge9XWvBrPg~~");
        cookie.setToUrl("");
        bossService.acceptResume(cookie.toString());
    }

    @Test
    public void test() {
//        Cookie cookie = bossApi.getDispatcher("bosszp-3227823c-705d-421a-aedf-a6fe8a0096b2");
//        log.info("获取cookie成功\n{}", cookie);

        Cookie cookie = new Cookie();
        cookie.setWt2("DRW20V34V5a_VN8CL0ebxqyFU2uVleHwFupRUOdBsVxRkFEcqiw_pmnjD1Ls3lBNvbRuurfamdm1nge9XWvBrPg~~");
        cookie.setToUrl("");

//        bossApi.addLog(cookie.toString(),BossApi.log_login);
//        bossApi.addLog(cookie.toString(),BossApi.log_pc_active);

//        String messagesList = bossApi.getZpToken(cookie.toString());
//        log.info("获取token成功\n{}", messagesList);

//        List<ResultBean> friendSimpleList = bossApi.getFriendSimpleList(cookie.toString());
//        log.info("获取朋友列表1成功\n{}", friendSimpleList);
//        List<String> list = Lists.newArrayList();
//        for (ResultBean resultBean : friendSimpleList) {
//            list.add(resultBean.getFriendId());
//        }
//
//        List<FriendListBean> friendFullList = bossApi.getFriendFullList(cookie.toString(), list);
//        log.info("获取朋友列表2成功\n{}", friendFullList);
//
//        List<LastMsg> lastMsgList = bossApi.getLastMsg(cookie.toString(), list);
//        log.info("获取朋友最后的消息成功\n{}", lastMsgList);

        //[507010500, 103952410, 106824212, 521661879, 97575228, 67974578, 111799174, 77980088, 61573893, 34661443]
        //530450896
//        List<MessagesBean> messagesList = bossApi.getHistoryMsg(cookie.toString(), "516474879");
//        log.info("获取历史消息成功\n{}", messagesList);

//        bossApi.exchangeAccept(cookie.toString(),"164451568364","9CIQbxLTM5GEs-51vitOPYmkxPt36fDr5S4wNxRnkoDB77L4MlHvEnajXqNgz7QJi0t6MYtsOSsc7MSCLgAx56SpbNh89K8OK_5fyDzfElYn4Qc5Ho--2PDIHOhgTcNSmarUPOwAAeNjPzuWkNim7htnFJcgFcyNT-oTFtZIjTbYJTMU8BW1Emxvg5xTmH8xar6-djcV4i2PpWc_VGc0j6-ozY9GVznKNIjI7mujiSC86Seqw9w4zfKkEJUAWRlvCSM-eUvSHxhQCxiP43bzGhB1Ig1YKsgrzHGT5x9Yjdns-LJcpcVVSDkm0SlPVInFALSk4zPcQ-6e9zRePySGPyafU7hYsuZR0C-cMcIvgvPkVAL2FkHgCLW1BbJm83uyJNFN5-bpn22t7VcuNsg-VHUtGh_4");

//        List<FriendListBean> friendFullList = bossApi.getFriendFullList(cookie.toString(), Lists.newArrayList("73976634"));
//        FriendListBean friend = friendFullList.get(0);
//        bossApi.syncFriendStatusInfo(cookie.toString(),friend.getUid());
//
//        GeekInfo geekInfo = bossApi.getGeekInfo(cookie.toString(), friend.getUid(), friend.getSecurityId());
//
//        DataBean data = geekInfo.getData();
//        bossApi.bossEnter(cookie.toString(), data.getEncryptUid(),data.getEncryptExpectId(),
//            data.getToPositionId(),data.getSecurityId());
//
//        bossApi.addLog(cookie.toString(),BossApi.log_click_chat.replace("FRIENDID",friend.getUid()));
//
//        //TODO 如何标记已读？

//        List<JobList> jobList = bossApi.getJobList(cookie.toString());
//        System.out.println(jobList);

//        RecJobList recJobList = bossApi.getRecJobList(cookie.toString());
//        System.out.println(recJobList);

//        bossService.findGeek(cookie.toString());

        bossService.addPcActiveLog(cookie.toString());
    }
}