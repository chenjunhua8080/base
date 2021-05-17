package com.cjh.common.api;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.cjh.common.boss.resp.Avatar;
import com.cjh.common.boss.resp.BossResp;
import com.cjh.common.boss.resp.CheckJob;
import com.cjh.common.boss.resp.Cookie;
import com.cjh.common.boss.resp.Dispatcher;
import com.cjh.common.boss.resp.Exchange;
import com.cjh.common.boss.resp.Friend1;
import com.cjh.common.boss.resp.Friend1.ResultBean;
import com.cjh.common.boss.resp.Friend2;
import com.cjh.common.boss.resp.Friend2.FriendListBean;
import com.cjh.common.boss.resp.Geek1;
import com.cjh.common.boss.resp.Geek1.GeekListBean;
import com.cjh.common.boss.resp.GeekInfo;
import com.cjh.common.boss.resp.HistoryMsg;
import com.cjh.common.boss.resp.HistoryMsg.MessagesBean;
import com.cjh.common.boss.resp.JobList;
import com.cjh.common.boss.resp.LastMsg;
import com.cjh.common.boss.resp.NewGeek;
import com.cjh.common.boss.resp.NewGeek.NewGeekItemBean;
import com.cjh.common.boss.resp.QrcodeKey;
import com.cjh.common.boss.resp.RecJobList;
import com.cjh.common.boss.resp.ScanResp;
import com.cjh.common.boss.resp.StartChat;
import com.cjh.common.boss.resp.SyncFriendStatus;
import com.cjh.common.boss.resp.Token;
import com.cjh.common.util.HttpUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * boss api
 *
 * @author cjh
 * @date 2021/5/7
 */
@Component
@Slf4j
public class BossApi {

    public static final String log_login = "{\"action\":\"user-sao-success\"}";
    public static final String log_pc_active = "{\"action\":\"pc-active\"}";
    public static final String log_click_chat = "{\"p2\":0,\"p4\":\"\",\"p5\":0,\"p6\":\"\",\"action\":\"action-list-chatrecent-click\",\"p\":FRIENDID}";

    /**
     * 获取 二维码/验证码 key
     * <p>
     * {"code":0,"message":"Success","zpData":{"qrId":"bosszp-5dce1efd-942e-46a1-a29d-625d15745e0d","randKey":"2Hqt0zD5euE848rwi5thew1k3emsM03P"}}
     */
    private final String url_qrcode_key = "https://login.zhipin.com/wapi/zppassport/captcha/randkey";

    /**
     * 获取二维码(需要先调用监听扫码)
     * <p>
     * img inputStream
     */
    private final String url_get_qrcode = "https://login.zhipin.com/wapi/zpweixin/qrcode/getqrcode?content=QRID&w=200&h=200";

    /**
     * 监听扫码
     * <p>
     * {"msg":"timeout","scaned":false}//超时
     * {"scaned":true,"allweb":true,"newScaned":true}
     */
    private final String url_scan_qrcode = "https://login.zhipin.com/scan?uuid=QRID&_=TIMESTAMP";

    /**
     * 监听扫码后登录
     * <p>
     * {"msg":"timeout","scaned":false}//超时
     * {"scaned":true,"newScaned":true,"login":true}
     */
    private final String url_scan_login = "https://login.zhipin.com/wapi/zppassport/qrcode/scanLogin?qrId=QRID&_=TIMESTAMP";

    /**
     * 扫码后获取头像
     * <p>
     * {"code":0,"message":"Success","zpData":{"wxMp":false,"headImg":0,"large":"https://img.bosszhipin.com/beijin/upload/avatar/20210228/801716949d5961d40db3fb8bab62bc8fc5618714e92a9866b62c70a8b143a719.png","tiny":"https://img.bosszhipin.com/beijin/upload/avatar/20210228/801716949d5961d40db3fb8bab62bc8fc5618714e92a9866b62c70a8b143a719_s.png"}}
     */
    private final String url_get_head_img = "https://login.zhipin.com/wapi/zppassport/qrcode/getHeadImg?qrId=QRID&_=TIMESTAMP";

    /**
     * 登录后获取路由， 返回cookie！
     * <p>
     * {"code":0,"message":"Success","zpData":{"toUrl":"/web/geek/resume","identity":0,"pcToUrl":"http://www.zhipin.com/web/geek/recommend","version":9.03}}
     */
    private final String url_get_dispatcher = "https://login.zhipin.com/wapi/zppassport/qrcode/dispatcher?qrId=QRID&_=TIMESTAMP";

    /**
     * 添加登录日志 POST
     * <p>
     * ba={"action":"user-sao-success"}//登录成功
     * ba={"action":"pc-active"}//pc，约1分钟一次
     * ba: {"action":"click-filter","p":"4","p2":23}//过滤筛选
     * ba: {"p2":0,"p4":"","p5":0,"p6":"","action":"action-list-chatrecent-click","p":518203105}//点击聊天
     * <p>
     * {"code":0,"message":"Success","zpData":true}
     */
    private final String url_add_login_log = "https://login.zhipin.com/wapi/zpCommon/actionLog/common.json";

    /**
     * 获取token，约4分钟一次
     * <p>
     * {"code":0,"message":"Success","zpData":{"token":"V1Sd8nEOD02VxgXdNrzxkcKS246TPWxQ~~"}}
     */
    private final String url_get_zp_token = "https://www.zhipin.com/wapi/zppassport/get/zpToken?v=TIMESTAMP";

    /**
     * 获取朋友1 POST
     * <p>
     * jobId=-1&workflow=%E5%85%A8%E9%83%A8&label=&conversationType=
     * <p>
     * {"code":0,"message":"Success","zpData":{
     * "result":[
     * {"encryptFriendId":"181e39841f65d5cb03B-2dS5GFo~","friendId":"44429488","friendSource":"0","name":"苏贵","updateTime":"1620437035000"}
     * ]
     * ,"filterGeekIdList":[],"filterEncryptIdList":[]
     * }
     * }
     */
    private final String url_get_friend_simple_list = "https://www.zhipin.com/wapi/zprelation/friend/filterV3.json";

    /**
     * 获取朋友2 POST
     * <p>
     * page=1&friendIds=44429488,530106854,27744241&dzFriendIds=
     * <p>
     * {"code":0,"message":"Success","zpData":{
     * "friendList":[{"friendSource":0,"securityId":"cXW6tIYzcfrg1-41maMI9UB9BpIP2rXuflBB6sUdOp-833YICmeiZ3uIu29ocxOUM5KmN_15uLc-rLAv5a6yC78bMKyxI1u6OpFy_VL5xoYQacLoUfCNS_ORRl2dz5PsQ5qvVn3o4MEDHQtLaR1GKtfiC7WXZYv8M60o1CUUDyPMF23Y6hkO9W5e9RVmYQ3fJoR9NZn8l7Pe-pNpJNQayBX5TmZPKJ12a5Sqh5uqONagg-KjC9aHTij45_T-SLpLwMxYGlQl5nxmlGJUpLBWBKcJmriR-jP3AWhTq19zDjgIQoqg2CN3ifYskMVgxsbF-aSiYbsA7gg3JzEpH-lDN0nUPkwG1UrAYKSIMyNV57XWPR4LiXVR3LOh06POGSDslsESvigT2602IgyV4LwiXyO2qQ~~","name":"苏贵","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_12.png","isTop":0,"sourceTitle":"","relationType":5,"lastMsg":null,"lastMessageInfo":null,"lastTime":"09:23","lastTS":1620437035000,"sourceType":2,"sourceExtend":null,"jobId":151429687,"encryptJobId":null,"itemType":0,"newGeek":1,"filterReasonList":null,"expectId":67589144,"encryptExpectId":null,"uid":44429488,"encryptUid":"181e39841f65d5cb03B-2dS5GFo~","isFiltered":false}]
     * }}
     */
    private final String url_get_friend_full_list = "https://www.zhipin.com/wapi/zprelation/friend/getBossFriendListV2.json";

    /**
     * 获取朋友个人信息
     * <p>
     * {"code":0,"message":"Success","zpData":{"data":{"uid":530450896,"encryptUid":"a54b7aa4c30978670nd639i9GFtW","lastTime":"17:38","encryptExpectId":"cc136ea56570ad8f1XB72dS6FVRU","addTime":"2021-05-12","year":"21年应届生","lowSalary":4,"highSalary":7,"salaryDesc":"4-7K","showCandidate":0,"firstTalk":false,"major":"应用电子技术","school":"广东松山职业技术学院","price":"4-5K","bothTalked":true,"block":false,"applyStatusDes":"现任","applyStatusDes2":null,"ageDesc":"21岁","toPosition":"PHP开发助理","weixinVisible":0,"sourceTitle":"简历刷新","labels":[],"newGeek":0,"jobId":151430497,"weixin":null,"toPositionId":"6b5bf76db6a660201nF73969FFtX","phone":null,"sourceType":1,"sourceExtend":null,"isTop":0,"edu":"大专","name":"廖碧琪","largeAvatar":"https://img.bosszhipin.com/beijin/upload/avatar/20210507/d0a2de87c185bd3e5549a66c727d1c08ea6bcafebd9e17716ae399ed1cfdeb00.png","position":"Python","positionName":"PHP开发助理","applyStatus":0,"lastCompany2":null,"note":"","gender":0,"city":"广州","resumeVisible":0,"mobileVisible":0,"cooperate":0,"expectId":241297564,"expectType":1,"outCount":1,"inCount":1,"belongGroup":null,"lastPosition":"行政专员/助理","avatar":"https://img.bosszhipin.com/beijin/upload/avatar/20210507/d0a2de87c185bd3e5549a66c727d1c08ea6bcafebd9e17716ae399ed1cfdeb00_s.png","lastCompany":"恩立经济咨询（深圳）有限公司","positionStatus":"离校-随时到岗","initTime":"16:38","interview":0,"lastPosition2":null,"relationType":6,"securityId":"Ufy2cao53rTMQ-j1mrOEEZD8njMivm56-Rfx0urNN6XV4M7RAw1_ZrhvNA_qoBfPdfxN2QvvJMaO5d_NLZsH6u7WgR8SnrBwFFz0CsBiAhwLJe6MlwL_aCh8fh1-JyDN8cYuvYBpGxBYvtKZiFUyuRVdwZOjfWW_qEuL6g3DHD7mEyimuPK0jaR9UkVbJU2HInn6DDbemmWOzMIzoPyHVB8t4A8Nu5g0KDdYhh_s1F1nHES0VJoK2mHiF3_KrDSlKCfFGFmH1rXLhA55Mgg6_RjdabeJB50UTpG9bpJTec1IIIY2k_u07g67h2ezYFd5iAy2Wkr6NNoCwYD-gPUVefaegdkXJNgSF82UaOgVUAQhdZTq1PjaEBms52f4AsorHp6ITIL_sBxaREVCsg82OlxSz0rV","filterReasonList":null,"everWorkPositionNameList":null,"workExpList":[{"timeDesc":"2021.03-至今","company":"恩立经济咨询（深圳）有限公司","positionName":"行政专员/助理"}],"eduExpList":[{"timeDesc":"2018-2021","school":"广东松山职业技术学院","major":"应用电子技术","degree":"大专","degreeCode":202}],"activeTimeDesc":"刚刚活跃","completeType":0,"resumeVideoInfo":null,"multiGeekVideoResume4BossVO":null,"highLightGeekResumeWords":null,"enablePmq":false,"closeInterview":false,"filtered":false,"systemJob":false,"isSystemJob":false,"isCloseInterview":false,"isFiltered":false},"status":1}}
     */
    private final String url_get_geek_info = "https://www.zhipin.com/wapi/zpboss/h5/chat/geek.json?uid=FRIENDID&geekSource=0&securityId=SECURITYID";

    /**
     * 获取朋友最后一条消息 POST
     * <p>
     * friendIds=44429488,530106854,27744241&src=0
     * <p>
     * {"code":0,"message":"Success","zpData":[
     * {"lastTime":"18:02","uid":84191709,"lastMsgInfo":{"msgId":162718684674,"encryptMsgId":"cf749f7ba0e2e6511nJ43Ny1FlpUxIu7","showText":"对方想发送附件简历到您邮箱，您是否同意","fromId":84191709,"toId":85014932,"status":0,"msgTime":1620468141463},"encryptUid":"249d4c5b2ffddb0833B70ty6EFs~","lastTS":1620468141463},{"lastTime":"09:23","uid":44429488,"lastMsgInfo":{"msgId":162456853047,"encryptMsgId":"583a56294a0da65c1nJ439i7GFdTwoi4","showText":"您好，我对贵司的这个职位很感兴趣，可以聊聊吗？","fromId":44429488,"toId":85014932,"status":0,"msgTime":1620437035743},"encryptUid":"181e39841f65d5cb03B-2dS5GFo~","lastTS":1620437035743}
     * ]}
     */
    private final String url_get_last_msg = "https://www.zhipin.com/wapi/zpchat/boss/userLastMsg";

    /**
     * 获取聊天历史
     * <p>
     * {"code":0,"message":"Success","zpData":{"messages":[{"uncount":1,"flag":1,"mid":160332060374,"received":true,"securityId":"KyJagoStAQRt0-i1RR_yjDXm_ye1BTrjtGYuDjYAypkOdCZTNJJAOCmJ2xPMpUBuhRM0g8CJ9BcE79F6wI2KNwSvYBcr8BiicJwaibNUjtu0VJDFmoFm-cXB_VlnlzuSa9Ptkxw0NPENFF_vGSY~","cmid":0,"type":3,"body":{"resume":{"jobSalary":"4-6K","brandName":"","education":"大专","gender":1,"city":"广州","lid":"","description":"担任过爱心社社长一职，有较好的组织管理能力，有较强的团队意识。并熟练HTML、CS，了解JS、Vue、Bootstrap以及微信小程序开发","securityId":"KyJagoStAQRt0-i1RR_yjDXm_ye1BTrjtGYuDjYAypkOdCZTNJJAOCmJ2xPMpUBuhRM0g8CJ9BcE79F6wI2KNwSvYBcr8BiicJwaibNUjtu0VJDFmoFm-cXB_VlnlzuSa9Ptkxw0NPENFF_vGSY~","expectId":176663097,"salary":"2-6K","bottomText":"4月29日12:45你就以下职位发起沟通","jobId":150891887,"content3":"bosszp://bosszhipin.app/openwith?type=vipprivilegedetail&params=%7B%22sf%22%3A24%7D&ba=&vipType=1","content2":"毕业于阳江职业技术学院|计算机网络技术","content1":"求职期望web前端","position":"web前端","workYear":"21年应届生","positionCategory":"web前段开发","user":{"uid":510528242,"headImg":15,"name":"廖聪华","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_15.png","source":0,"certification":0},"applyStatus":"离校-随时到岗","age":"21岁"},"type":9,"templateId":1,"headTitle":"点击查看牛人简历"},"offline":false,"pushSound":0,"from":{"uid":510528242,"headImg":15,"name":"廖聪华","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_15.png","source":0,"certification":0},"to":{"uid":85014932,"headImg":4,"name":"郑女士","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_4.png","source":0,"certification":0},"time":1619671554000,"taskId":0,"status":2},{"uncount":1,"flag":1,"mid":160332060442,"received":true,"securityId":"ti03Jb3eYhq9X-81B6kzKUEnaFJLZnag9LqVDYpLsp5ojHK8IcNpR-T_ihuKco3q2wnffWUFz9hiBJfj66kMIdfct_sFA2GQRrAv","cmid":0,"type":3,"body":{"action":{"extend":"{\"expectId\":176663097}","aid":51},"type":4,"templateId":1,"headTitle":""},"offline":false,"pushSound":0,"from":{"uid":85014932,"headImg":4,"name":"郑女士","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_4.png","source":0,"certification":0},"to":{"uid":510528242,"headImg":15,"name":"廖聪华","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_15.png","source":0,"certification":0},"time":1619671554000,"taskId":0,"status":2},{"uncount":0,"flag":1,"bizType":101,"mid":160332060492,"received":true,"securityId":"7JYAwPC5jfTK9-q1ahUuRBzKmRx1dejiUl4cLbAEOY1byDQll27LosCItSaQMh2099WugSRgq6sA9CAitleX0kLm_pVJasIRIF_e","cmid":0,"type":3,"body":{"extend":"","text":"你好，我们最近有在招web前段开发，您可以看一下职位信息，如果有兴趣，期待您的回复","type":1,"templateId":1,"headTitle":""},"offline":false,"pushSound":0,"from":{"uid":85014932,"headImg":4,"name":"郑女士","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_4.png","source":0,"certification":0},"to":{"uid":510528242,"headImg":15,"name":"廖聪华","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_15.png","source":0,"certification":0},"time":1619671554000,"pushText":"郑女士:你好，我们最近有在招web前段开发，您可以看一下职位信息，如果有兴趣，期待您的回复","taskId":0,"status":2},{"uncount":0,"flag":1,"mid":160332071258,"received":true,"securityId":"s41TmLfmLziYE-O1Ro4QTfB3TVMdMeMDg8vQCyCBFM4VWEKbiTkLIOL2KB7bDM2Enp4PwmWhB0bHOb92jlRlkXRgS2hNRFCP9FnA","cmid":1619671556913,"type":1,"body":{"extend":"","text":"方便发一份你的简历过来吗？","type":1,"templateId":1,"headTitle":""},"offline":false,"pushSound":0,"from":{"uid":85014932,"headImg":4,"name":"郑女士","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_4.png","source":0,"certification":0},"to":{"uid":510528242,"headImg":15,"name":"廖聪华","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_15.png","source":0,"certification":0},"time":1619671557000,"pushText":"郑女士:方便发一份你的简历过来吗？","taskId":0,"status":2},{"uncount":0,"flag":65537,"mid":162378976193,"received":true,"securityId":"geTP5xk2kwBNA-p1rEcaTxM4q7FambDYwBUEVZ4vexEZ7TPtNIqazzLMhlHvWL_dX2hbpzyunVMT7CulYCuMz7690XRj9uC5JX5R","cmid":1620388252311,"type":1,"body":{"extend":"","text":"好的","type":1,"templateId":1,"headTitle":""},"offline":false,"pushSound":0,"from":{"uid":510528242,"headImg":15,"name":"廖聪华","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_15.png","source":0,"certification":0},"to":{"uid":85014932,"headImg":4,"name":"郑女士","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_4.png","source":0,"certification":0},"time":1620388253441,"pushText":"廖聪华:好的","taskId":0,"status":1},{"uncount":0,"flag":65537,"bizType":14,"mid":162378990781,"received":true,"securityId":"f2xWgr7NEKocH-J1qv5NjvjCFv_oVNs9lqqfM8_g69R4Asf0Zn7rXITJpiWJVQksJVQDu0zaTPueH-fvVBTuLWAo2ky78PiyldHI","cmid":0,"type":1,"body":{"dialog":{"backgroundUrl":"","clickMore":false,"buttons":[{"text":"同意","templateId":0,"url":"bosszp://bosszhipin.app/openwith?type=sendaction&uid=510528242&aid=41&extends={\"resumeId\":62825830,\"resumeName\":\"廖聪华简历.docx\",\"annexType\":0}"},{"text":"拒绝","templateId":1,"url":"bosszp://bosszhipin.app/openwith?type=sendaction&uid=510528242&aid=42&extends={\"resumeId\":62825830,\"resumeName\":\"廖聪华简历.docx\",\"annexType\":0}"}],"operated":false,"statisticParameters":"","type":2,"title":"","timeout":0,"url":"","content":"","selectedIndex":0,"extend":"","text":"对方想发送附件简历到您邮箱，您是否同意"},"type":7,"templateId":1,"headTitle":""},"offline":false,"pushSound":0,"bizId":"162378990759","from":{"uid":510528242,"headImg":15,"name":"廖聪华","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_15.png","source":0,"certification":0},"to":{"uid":85014932,"headImg":4,"name":"郑女士","company":"","avatar":"https://img.bosszhipin.com/boss/avatar/avatar_4.png","source":0,"certification":0},"time":1620388257909,"pushText":"廖聪华:对方想发送附件简历到您邮箱，您是否同意","taskId":0,"status":1}],"type":1}}
     */
    private final String url_get_history_msg = "https://www.zhipin.com/wapi/zpchat/boss/historyMsg?src=0&gid=GID&maxMsgId=0&c=20&page=1";

    /**
     * BOSS输入 POST
     * <p>
     * <b>geekId>geek.json>encryptUid</b>
     * <p>
     * <b>expectId>geek.json>encryptExpectId</b>
     * <p>
     * <b>jobId>geek.json>toPositionId</b>
     * <p>
     * <b>securityId>geek.json>securityId</b>
     * <p>
     * geekId=f0a514f68efc29930nV63t-1ElZS&expectId=e0448a06dd44403b1nN83du-EFtX&jobId=8a878d2ce0acdd611nF609S8GFpX&securityId=nJB5gbMv3c-ui-61fRfgfAyhPTDEMxk8Uq2Ox0_kgmskHgUJjDBaNXF6GAMzYullnwjQkO60Z6ZObDqdAIHInspVWCz6KUpujurz85XB5BwpI-PckHPBDMIjh4mHBp1s9k7sf3w76UkaHTC69X-HZ0ZSkHgmZRaKN-GFu3vS2tFDmT3f39RxEttlqmZd3O8_raiLbwtSjOPHUZoN9by183coiFgga65BMI_NUKPqjLYx6Na-T4qHdRtjacm8mYDlIAaA0Nr1imwjVsgxEuBnyuBo2VB91xc1gI6n7ql5TvnpyTm2goigc3rgEqzUJ7MUm9FZ6H53iEtDgz47Br98trtD3JpsOs17UAaXgUJlbnyyIBoXX73YqTikDT0kkbIlrp9U3rmpVMp6PPLBZ0V4ED1c2NVc
     * <p>
     * {"code":0,"message":"Success","zpData":{}}
     */
    private final String url_boss_enter = "https://www.zhipin.com/wapi/zpchat/session/bossEnter";

    /**
     * 朋友状态同步
     * <p>
     * {"code":0,"message":"Success","zpData":{"resumeAuthStatus":0,"contactAuthStatus":0}}
     */
    private final String url_sync_friend_status_info = "https://www.zhipin.com/wapi/zprelation/friend/syncFriendStatusInfo?friendId=FRIENDID";

    /**
     * 接受简历 POST
     * <p>
     * mid=162378990781&type=3&securityId=nJB5gbMv3c-ui-61fRfgfAyhPTDEMxk8Uq2Ox0_kgmskHgUJjDBaNXF6GAMzYullnwjQkO60Z6ZObDqdAIHInspVWCz6KUpujurz85XB5BwpI-PckHPBDMIjh4mHBp1s9k7sf3w76UkaHTC69X-HZ0ZSkHgmZRaKN-GFu3vS2tFDmT3f39RxEttlqmZd3O8_raiLbwtSjOPHUZoN9by183coiFgga65BMI_NUKPqjLYx6Na-T4qHdRtjacm8mYDlIAaA0Nr1imwjVsgxEuBnyuBo2VB91xc1gI6n7ql5TvnpyTm2goigc3rgEqzUJ7MUm9FZ6H53iEtDgz47Br98trtD3JpsOs17UAaXgUJlbnyyIBoXX73YqTikDT0kkbIlrp9U3rmpVMp6PPLBZ0V4ED1c2NVc
     * <p>
     * {"code":0,"message":"Success","zpData":{"type":3,"status":0}}
     */
    private final String url_exchange_accept = "https://www.zhipin.com/wapi/zpchat/exchange/accept";

    /**
     * 请求简历1
     * <p>
     * type=4&check=true&securityId=l3AkeX8m35OMu-G11PWGEqqyaDaDz4bOdqMLk_BgqQ_6O6XTylIsx79mKwxFXU-Tklvgps7AxPN_kGPshV4s_t6aexkqRn429Fby84mJ9twsmv6R2xyJjseiotsnA2PDD2mOruK5HfAYQNKZcuE0Gve0_8wAx7DstNxkFbcgXzpCdaJG3HauNLL0jkEnwLNJU0bDO_E7jWxiYlGiJLPwjcQINU6AYAmh9ykZUZDq5b1Ep0SfFVpDT-pkHreWYP8xoGlE245RJsbYSDTIE4L-5xbsxF77O2xnXxfdDx4gEPU2F1WBsHHKaqyyyzTSD6RfEioXkj7RYmoispGhF-oqe9-hHi5LUExraA0fUArrgA0x6iZD0ETspQSefCaItVJSgVVszWEW5RTP8V8yBK2QRPyC9jD-
     * <p>
     * {"code":0,"message":"Success","zpData":{"type":4,"status":0}}
     */
    private final String url_exchange_test = "https://www.zhipin.com/wapi/zpchat/exchange/accept";

    /**
     * 请求简历2
     * <p>
     * type=4&securityId=l3AkeX8m35OMu-G11PWGEqqyaDaDz4bOdqMLk_BgqQ_6O6XTylIsx79mKwxFXU-Tklvgps7AxPN_kGPshV4s_t6aexkqRn429Fby84mJ9twsmv6R2xyJjseiotsnA2PDD2mOruK5HfAYQNKZcuE0Gve0_8wAx7DstNxkFbcgXzpCdaJG3HauNLL0jkEnwLNJU0bDO_E7jWxiYlGiJLPwjcQINU6AYAmh9ykZUZDq5b1Ep0SfFVpDT-pkHreWYP8xoGlE245RJsbYSDTIE4L-5xbsxF77O2xnXxfdDx4gEPU2F1WBsHHKaqyyyzTSD6RfEioXkj7RYmoispGhF-oqe9-hHi5LUExraA0fUArrgA0x6iZD0ETspQSefCaItVJSgVVszWEW5RTP8V8yBK2QRPyC9jD-
     * <p>
     * {"code":0,"message":"Success","zpData":{"type":4,"status":0}}
     */
    private final String url_exchange_request = "https://www.zhipin.com/wapi/zpchat/exchange/accept";


    /**
     * 获取我的招聘岗位
     * <p>
     * {"code":0,"message":"Success","zpData":{"unpaidJobList":[{"encryptId":"a9672cad08f7b5cb1nF62d65FVBX","jobName":"售后客服","brandName":null,"proxyJob":0,"locationName":"广州","salaryDesc":"3-4K"}],"onlineJobList":[{"encryptId":"6b5bf76db6a660201nF73969FFtX","jobName":"PHP开发助理","brandName":null,"proxyJob":0,"locationName":"广州","salaryDesc":"4-7K"},{"encryptId":"5008c4e58dd063dd1nF739-0FlpX","jobName":"HTML前端工程师","brandName":null,"proxyJob":0,"locationName":"广州","salaryDesc":"4-7K"},{"encryptId":"8a878d2ce0acdd611nF609S8GFpX","jobName":"web前段开发","brandName":null,"proxyJob":0,"locationName":"广州","salaryDesc":"4-7K"},{"encryptId":"f9c6a8f8bdd82f1c1nF639W-FVdT","jobName":"设计师助理","brandName":null,"proxyJob":0,"locationName":"广州","salaryDesc":"4-6K"}]}}
     */
    private final String url_get_rec_job_list = "https://www.zhipin.com/wapi/zpjob/job/recJobList";

    /**
     * 获取我的招聘岗位
     * <p>
     * {"code":0,"message":"Success","zpData":[{"encryptJobId":"6b5bf76db6a660201nF73969FFtX","description":"PHP开发助理_广州4-7K","jobId":151430497,"jobType":0},{"encryptJobId":"5008c4e58dd063dd1nF739-0FlpX","description":"HTML前端工程师_广州4-7K","jobId":151429687,"jobType":0},{"encryptJobId":"8a878d2ce0acdd611nF609S8GFpX","description":"web前段开发_广州4-7K","jobId":150891887,"jobType":0},{"encryptJobId":"f9c6a8f8bdd82f1c1nF639W-FVdT","description":"设计师助理_广州4-6K","jobId":150483553,"jobType":0},{"encryptJobId":"a9672cad08f7b5cb1nF62d65FVBX","description":"售后客服（关闭）_广州3-4K","jobId":150234527,"jobType":0}]}
     */
    private final String url_get_job_list = "https://www.zhipin.com/wapi/zpjob/job/chatted/jobList";

    /**
     * 获取我的信息
     * <p>
     * ["method=/wapi/zppassport/get/wt","method=/wapi/zpuser/wap/getUserInfo.json"]
     * <p>
     * {"code":0,"message":"Success","zpData":{"/wapi/zppassport/get/wt":{"code":0,"message":"Success","zpData":{"wt2":"DRW20V34V5a_VN8CL0ebxqyFU2uVleHwFupRUOdBsVxRkFEcqiw_pmnjD1Ls3lBNvbRuurfamdm1nge9XWvBrPg~~","wt":"qhjETNvvDh4VnKVh"}},"/wapi/zpuser/wap/getUserInfo.json":{"code":0,"message":"Success","zpData":{"userId":85014932,"identity":1,"encryptUserId":"637199ec7ae3eb9c33F62tm0E1A~","name":"郑淑珍","showName":"郑女士","tinyAvatar":"https://img.bosszhipin.com/boss/avatar/avatar_4.png","largeAvatar":"https://img.bosszhipin.com/boss/avatar/avatar_4.png","token":"JPBEnXU2QtHvsvk","isHunter":false,"clientIP":"119.129.85.195","email":"2920610795@qq.com","phone":null,"brandName":"广州嘉信医疗技术...","doubleIdentity":true,"recruit":false,"agentRecruit":false,"industryCostTag":0}}}}
     */
    private final String url_get_userinfo = "https://www.zhipin.com/wapi/batch/batchRunV3?batch_method_feed=[%22method%3D%2Fwapi%2Fzppassport%2Fget%2Fwt%22,+%22method%3D%2Fwapi%2Fzpuser%2Fwap%2FgetUserInfo.json%22]";

    /**
     * 系统推荐牛人
     * <p>
     * {"code":0,"message":"Success","zpData":{"displayTraineeStyle":0,"heightGray":0,"cityCode":101280100,"advantageShow":0,"hasMore":true,"displayAboard":true,"hasJobCompetitive":false,"jobExperience":101,"geekList":[{"encryptGeekId":"5418d8eb5b904b960XB739u8EVQ~","isFriend":0,"talkTimeDesc":null,"cooperate":0,"blur":0,"mateName":null,"shareNote":null,"shareMessage":0,"encryptShareId":null,"geekCallStatus":0,"suid":null,"geekCard":{"securityId":"-B8paIgcY0CwWGT710UwOox4_RkhKwISZi3-SiXO1IMtK6_-sHOj_uXCp6JaDS_n4Ijc9Gzt0vUbEB44IQIayXJdA-0Fjvb4JGWmKtFJ3B_ZTvkjWUi0Jh7G8jN1dSdUqhjSh07_SfuLgshjAxz7d0hGO1KV9X3CGdJQmew_YeNbBG9tiO6ZH-mqUGS3q47FYDhsiVfU6kOzF4eZxA7PtLfs2-uBpOVni0EJHuxKA8BkKv9d4LdQ-k9DOy0vRWe8H17Q9aBPYuTwuWewNLHATcvsf2Jlf_k8LLBZtqeRaSYyUgIiEEJoQdTCClQQVwyWE3npLvivT0brDAxJTV4ibFUBrfNdq0UyvAwbPVgwOOeVN8KuAOdMh6UVscYgzZKTzUZvKACSAglUGrgqckwkyg~~","geekId":64146116,"geekName":"王健乐","geekAvatar":"https://img.bosszhipin.com/beijin/upload/avatar/20201201/1a05dc98c5a99307254b979189d9bfabb4012ecc57a5035aa39b60cf74de956f_s.png","geekGender":1,"geekWorkYear":"21年应届生","geekDegree":"大专","freshGraduate":3,"geekDesc":{"content":"2021年应届生，2021年6月正式毕业。有一定的上线项目经验。\n1、熟悉PHP+MySql和Laravel、Think5、redis的使用，配置过阿里云服务器。。\n2、熟悉DIVCSSbootstrapSwiperJavaScript、Jquery的使用。\n3、熟悉Linux系统下的LNMP，LAMP，redis环境的搭建。\n4、熟悉Vue和Vue脚手架+elementui。","indexList":[]},"expectId":172237126,"expectType":1,"expectPositionType":0,"salary":"4-8K","lowSalary":4,"highSalary":8,"middleContent":{"content":"期望职位PHP","indexList":[]},"jobId":151430497,"lid":"3e6f46ac-e626-4bfe-9c37-35a02595d626.f1:common.eyJzZXNzaW9uSWQiOiJkNTZkM2U1OS1lNmJlLTQ3ZGYtODIzNS0wYmRkNGMwODhiNjQiLCJyY2RCelR5cGUiOiJmMV9icmNkIn0.1","actionDateDesc":null,"expectPositionCode":10000002,"expectPositionName":"PHP","expectPositionNameLv2":"","expectLocationCode":101280100,"expectLocationName":"广州","expectSubLocation":0,"expectSubLocationName":null,"applyStatus":0,"applyStatusDesc":"","activeTime":1620444395,"birthday":"19990501","ageDesc":"22岁","ageLight":{"content":"22岁","highlight":false},"geekEdu":{"id":0,"userId":0,"school":"私立华联学院","schoolId":0,"major":"计算机应用技术","degree":0,"degreeName":"大专","eduType":0,"startDate":"20190101","endDate":"20210101","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2019","endYearStr":"2021"},"geekEdus":[{"id":0,"userId":0,"school":"私立华联学院","schoolId":0,"major":"计算机应用技术","degree":0,"degreeName":"大专","eduType":0,"startDate":"2019.01","endDate":"2021.01","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2019","endYearStr":"2021"}],"geekWorks":[{"id":116093712,"geekId":0,"company":"合优","industryCode":0,"industry":null,"industryCategory":null,"position":0,"positionCategory":"全栈工程师","blueCollarPosition":false,"positionName":"PHP程序","positionLv2":0,"isPublic":0,"department":null,"responsibility":"1.根据设计稿完成前端切片和后端程序。\n2.日常对已上线项目的维护和修改\n3.项目优化上线","startDate":"2020.12","endDate":"2021.03","customPositionId":0,"customIndustryId":0,"workPerformance":"","workEmphasisList":["PHP","JQuery","ThinkPHP","Laravel","MySQL"],"certStatus":0,"workType":0,"addTime":null,"updateTime":null,"companyHighlight":{"content":"合优","indexList":[]},"positionNameHighlight":{"content":"全栈工程师","indexList":[]},"workTime":"3个月","workMonths":0,"current":false,"stillWork":false,"startYearMonStr":"2020","endYearMonStr":"2021","workTypeIntern":false}],"geekDoneWorks":null,"geekHighestDegreeEdu":null,"geekSource":0,"matches":["Vue","LNMP","Laravel","LAMP","端视图"],"markWords":[{"content":"Vue","highlight":false},{"content":"LNMP","highlight":false},{"content":"Laravel","highlight":false},{"content":"LAMP","highlight":false},{"content":"端视图","highlight":false}],"encryptGeekId":"5418d8eb5b904b960XB739u8EVQ~","encryptJobId":"6b5bf76db6a660201nF73969FFtX","eliteGeek":0,"viewed":false,"completeType":0},"geekLastWork":{"id":116093712,"geekId":0,"company":"合优","industryCode":0,"industry":null,"industryCategory":null,"position":0,"positionCategory":"全栈工程师","blueCollarPosition":false,"positionName":"PHP程序","positionLv2":0,"isPublic":0,"department":null,"responsibility":"1.根据设计稿完成前端切片和后端程序。\n2.日常对已上线项目的维护和修改\n3.项目优化上线","startDate":"2020.12","endDate":"2021.03","customPositionId":0,"customIndustryId":0,"workPerformance":"","workEmphasisList":["PHP","JQuery","ThinkPHP","Laravel","MySQL"],"certStatus":0,"workType":0,"addTime":null,"updateTime":null,"companyHighlight":{"content":"合优","indexList":[]},"positionNameHighlight":{"content":"全栈工程师","indexList":[]},"workTime":"3个月","workMonths":0,"current":false,"stillWork":false,"startYearMonStr":"2020","endYearMonStr":"2021","workTypeIntern":false},"showEdus":[{"id":0,"userId":0,"school":"私立华联学院","schoolId":0,"major":"计算机应用技术","degree":0,"degreeName":"大专","eduType":0,"startDate":"2019.01","endDate":"2021.01","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2019","endYearStr":"2021"}],"showWorks":[{"id":116093712,"geekId":0,"company":"合优","industryCode":0,"industry":null,"industryCategory":null,"position":0,"positionCategory":"全栈工程师","blueCollarPosition":false,"positionName":"PHP程序","positionLv2":0,"isPublic":0,"department":null,"responsibility":"1.根据设计稿完成前端切片和后端程序。\n2.日常对已上线项目的维护和修改\n3.项目优化上线","startDate":"2020.12","endDate":"2021.03","customPositionId":0,"customIndustryId":0,"workPerformance":"","workEmphasisList":["PHP","JQuery","ThinkPHP","Laravel","MySQL"],"certStatus":0,"workType":0,"addTime":null,"updateTime":null,"companyHighlight":{"content":"合优","indexList":[]},"positionNameHighlight":{"content":"全栈工程师","indexList":[]},"workTime":"3个月","workMonths":0,"current":false,"stillWork":false,"startYearMonStr":"2020","endYearMonStr":"2021","workTypeIntern":false}],"activeTimeDesc":"刚刚活跃","anonymousGeek":0,"hasAttachmentResume":0,"haveChatted":0,"hasBg":0,"canUseDirectCall":false,"feedback":[{"code":16,"memo":"期望(PHP)与职位不符","showType":0,"titleL2":null,"feedbackL2List":null},{"code":10,"memo":"工作经历和\"PHP\"无关","showType":0,"titleL2":null,"feedbackL2List":null},{"code":9,"memo":"行业经验与要求不符","showType":0,"titleL2":null,"feedbackL2List":null},{"code":3,"memo":"学历低于要求","showType":0,"titleL2":null,"feedbackL2List":null},{"code":15,"memo":"年龄不合适","showType":0,"titleL2":null,"feedbackL2List":null},{"code":4,"memo":"期望薪资偏高","showType":0,"titleL2":null,"feedbackL2List":null},{"code":13,"memo":"与工作地点距离远","showType":0,"titleL2":null,"feedbackL2List":null},{"code":5,"memo":"其他原因","showType":1,"titleL2":null,"feedbackL2List":null}],"feedbackTitle":"选择不喜欢的原因，为您优化推荐","searchChatCardCostCount":0,"showSelectJob":false,"blurGeek":false,"friendGeek":false},{"encryptGeekId":"53deba13afa0dadf0nZ63t21FlBQ","isFriend":0,"talkTimeDesc":null,"cooperate":0,"blur":0,"mateName":null,"shareNote":null,"shareMessage":0,"encryptShareId":null,"geekCallStatus":0,"suid":null,"geekCard":{"securityId":"nhclcLxmDUjNgVP0O55CXMiqTPUmn303XbyQMYc_QWrUu8_z-rcs9DvEeBPh8HqjOdC-_-DVXbK-PGBT26CxJRUZqyQcWB5rrq6PpmPDQaKr_bIjfs-mCdhyfoUV5GQC27EBBJFiU7VPBfdvpSLm792egI_tpIlPPw-qUHgyW9SEoIcYEckHZCex6K19CpPMWuONws7j5uOMxF9gpPriKMDealhAx30o-thPpp8yKOFiMzvSbfkPV41w5qv8xaeJaDSJDJ0WqqBe2ZCIm3p-1Fs4UPSZonlAd_u1AXsFp159gBU0o4Ezy_3sBDwd5ru1iIV5m1T_8qpqfzWab0DruNls6gz0JV_JHJCm4THikeqvKC-TIjYQ6KfthpWd7nW0LGkMXPO-8XJ5Dm3UoRNN-OE~","geekId":520508620,"geekName":"朱家颖","geekAvatar":"https://img.bosszhipin.com/beijin/upload/avatar/20210419/d45026fdcd8aa3df2e8e2a2e43897f9e833c2b5d8adb4d742010583e25899d5f_s.jpg","geekGender":1,"geekWorkYear":"21年应届生","geekDegree":"本科","freshGraduate":3,"geekDesc":{"content":"1.有毕业实习经验，掌握基本的项目开发，独自开发过一个微信小程序，熟悉PHP+MySQL，mvc，ajax，了解前端HTML，css，js等，有api接口经验。\n2.有较强的逻辑结构，年轻有活力，抗压能力强。\n3.连续两年担任校级KYA街舞协会宣传部部长和Breaking舞种负责人，均获得相应证书。获得校级优秀先进个人证书。","indexList":[]},"expectId":204879086,"expectType":1,"expectPositionType":0,"salary":"5-8K","lowSalary":5,"highSalary":8,"middleContent":{"content":"火龙果·PHP","indexList":[]},"jobId":151430497,"lid":"3e6f46ac-e626-4bfe-9c37-35a02595d626.f1:common.eyJzZXNzaW9uSWQiOiJkNTZkM2U1OS1lNmJlLTQ3ZGYtODIzNS0wYmRkNGMwODhiNjQiLCJyY2RCelR5cGUiOiJmMV9icmNkIn0.2","actionDateDesc":null,"expectPositionCode":10000019,"expectPositionName":"后端开发","expectPositionNameLv2":"","expectLocationCode":101280100,"expectLocationName":"广州","expectSubLocation":0,"expectSubLocationName":null,"applyStatus":2,"applyStatusDesc":"","activeTime":1620442418,"birthday":"19970201","ageDesc":"24岁","ageLight":{"content":"24岁","highlight":false},"geekEdu":{"id":0,"userId":0,"school":"河池学院","schoolId":0,"major":"计算机科学与技术","degree":0,"degreeName":"本科","eduType":0,"startDate":"20170101","endDate":"20210101","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2017","endYearStr":"2021"},"geekEdus":[{"id":0,"userId":0,"school":"河池学院","schoolId":0,"major":"计算机科学与技术","degree":0,"degreeName":"本科","eduType":0,"startDate":"2017.01","endDate":"2021.01","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2017","endYearStr":"2021"}],"geekWorks":[{"id":115158405,"geekId":0,"company":"火龙果","industryCode":0,"industry":null,"industryCategory":null,"position":0,"positionCategory":"PHP","blueCollarPosition":false,"positionName":"PHP","positionLv2":0,"isPublic":0,"department":null,"responsibility":"1.研究和编写API接口；\n2.熟悉MySQL数据库并掌握简单的调用：\n3.编写php的一些基本功能和调用函数。\n4.独立开发简单的功能；\n5.日常维护代码。","startDate":"2021.03","endDate":"","customPositionId":0,"customIndustryId":0,"workPerformance":"协助正式员工开发维护","workEmphasisList":["vscode","ThinkPHP","小程序","后端开发","MySQL"],"certStatus":0,"workType":0,"addTime":null,"updateTime":null,"companyHighlight":{"content":"火龙果","indexList":[]},"positionNameHighlight":{"content":"PHP","indexList":[]},"workTime":"2个月","workMonths":0,"current":true,"stillWork":true,"startYearMonStr":"2021","endYearMonStr":"至今","workTypeIntern":false}],"geekDoneWorks":null,"geekHighestDegreeEdu":null,"geekSource":0,"matches":["后端开发","Vue","沉余","调用方式","ThinkPHP"],"markWords":[{"content":"后端开发","highlight":false},{"content":"Vue","highlight":false},{"content":"沉余","highlight":false},{"content":"调用方式","highlight":false},{"content":"ThinkPHP","highlight":false}],"encryptGeekId":"53deba13afa0dadf0nZ63t21FlBQ","encryptJobId":"6b5bf76db6a660201nF73969FFtX","eliteGeek":0,"viewed":false,"completeType":0},"geekLastWork":{"id":115158405,"geekId":0,"company":"火龙果","industryCode":0,"industry":null,"industryCategory":null,"position":0,"positionCategory":"PHP","blueCollarPosition":false,"positionName":"PHP","positionLv2":0,"isPublic":0,"department":null,"responsibility":"1.研究和编写API接口；\n2.熟悉MySQL数据库并掌握简单的调用：\n3.编写php的一些基本功能和调用函数。\n4.独立开发简单的功能；\n5.日常维护代码。","startDate":"2021.03","endDate":"","customPositionId":0,"customIndustryId":0,"workPerformance":"协助正式员工开发维护","workEmphasisList":["vscode","ThinkPHP","小程序","后端开发","MySQL"],"certStatus":0,"workType":0,"addTime":null,"updateTime":null,"companyHighlight":{"content":"火龙果","indexList":[]},"positionNameHighlight":{"content":"PHP","indexList":[]},"workTime":"2个月","workMonths":0,"current":true,"stillWork":true,"startYearMonStr":"2021","endYearMonStr":"至今","workTypeIntern":false},"showEdus":[{"id":0,"userId":0,"school":"河池学院","schoolId":0,"major":"计算机科学与技术","degree":0,"degreeName":"本科","eduType":0,"startDate":"2017.01","endDate":"2021.01","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2017","endYearStr":"2021"}],"showWorks":[{"id":115158405,"geekId":0,"company":"火龙果","industryCode":0,"industry":null,"industryCategory":null,"position":0,"positionCategory":"PHP","blueCollarPosition":false,"positionName":"PHP","positionLv2":0,"isPublic":0,"department":null,"responsibility":"1.研究和编写API接口；\n2.熟悉MySQL数据库并掌握简单的调用：\n3.编写php的一些基本功能和调用函数。\n4.独立开发简单的功能；\n5.日常维护代码。","startDate":"2021.03","endDate":"","customPositionId":0,"customIndustryId":0,"workPerformance":"协助正式员工开发维护","workEmphasisList":["vscode","ThinkPHP","小程序","后端开发","MySQL"],"certStatus":0,"workType":0,"addTime":null,"updateTime":null,"companyHighlight":{"content":"火龙果","indexList":[]},"positionNameHighlight":{"content":"PHP","indexList":[]},"workTime":"2个月","workMonths":0,"current":true,"stillWork":true,"startYearMonStr":"2021","endYearMonStr":"至今","workTypeIntern":false}],"activeTimeDesc":"刚刚活跃","anonymousGeek":0,"hasAttachmentResume":0,"haveChatted":0,"hasBg":0,"canUseDirectCall":false,"feedback":[{"code":16,"memo":"期望(后端开发)与职位不符","showType":0,"titleL2":null,"feedbackL2List":null},{"code":10,"memo":"工作经历和\"PHP\"无关","showType":0,"titleL2":null,"feedbackL2List":null},{"code":9,"memo":"行业经验与要求不符","showType":0,"titleL2":null,"feedbackL2List":null},{"code":3,"memo":"学历低于要求","showType":0,"titleL2":null,"feedbackL2List":null},{"code":15,"memo":"年龄不合适","showType":0,"titleL2":null,"feedbackL2List":null},{"code":4,"memo":"期望薪资偏高","showType":0,"titleL2":null,"feedbackL2List":null},{"code":13,"memo":"与工作地点距离远","showType":0,"titleL2":null,"feedbackL2List":null},{"code":5,"memo":"其他原因","showType":1,"titleL2":null,"feedbackL2List":null}],"feedbackTitle":"选择不喜欢的原因，为您优化推荐","searchChatCardCostCount":0,"showSelectJob":false,"blurGeek":false,"friendGeek":false}],"displayBlueStyle":0,"jobId":151430497,"startIndex":0,"partTimeJob":false,"quickTopTimeIndex":-1,"lstSelectedLabel":[],"page":1,"recommendABTest":false}}
     */
    private final String url_get_recommend_geek = "https://www.zhipin.com/wapi/zpboss/h5/boss/recommendGeekList?jobid=JOBID&status=0&refresh=REFRESH_TIMESTAMP&source=0&age=16,-1&gender=0&switchJobFrequency=0&exchangeResumeWithColleague=0&recentNotView=0&activation=0&school=0&major=0&experience=0&degree=0&salary=0&intention=0&jobId=JOBID&page=1&_=TIMESTAMP";

    /**
     * 自己筛选牛人
     * <p>
     * 新牛人1，看过我的2，对我感兴趣的4，我看过的8: jobid=-1&status=1&tag=1
     * <p>
     * {"code":0,"message":"Success","zpData":{"needGuide":false,"startIndex":0,"hasMore":true,"page":1,"tag":1,"itemCardIdx":-1,"geekList":[{"geekCard":{"securityId":"jPPodbDV9E79e-v1U8pfoqehF4Tk5DR8kTnOFgH78V-Mo8IwrZWy4v6wTyTHxxnfAIAOsNK71S2Wdy4XvDFGMjkBZD10dr6HhW-jpHZMB_cYfRZy6K4-CyxXwRMLu22SINzLVR5o1fJjfMkaD33OiStkAAAfhSRwjZ38z6kdoRZv1KuPqhpir3jt2am3AUl2BOM3YWkFFXE609ODR09XjIEsXRFbBNGUgzh8zv0hJY5fLGV0Ok0QyVx5VKvrapa8ft8BzE3kZWw-sU1_-zq40p95p9277wg-zBjurRTquaOc2gTE8HL2xRXhoBevWmkByDFltirTy1UF0SZjyKNTbU09TOB6CqUa1PaJgIj1YWZXUUYM8gNYnqO5p9zKC_pCRg~~","geekId":57614302,"geekName":"卓建阳","geekAvatar":"https://img.bosszhipin.com/boss/avatar/avatar_15.png","geekGender":1,"geekWorkYear":"2年","geekDegree":"大专","geekDesc":{"content":"拥有较强的学习能力，能快速适应工作环境，独立生活能力强，同时富于团队精神和责任感!\r\n空闲时间喜欢在网上了解一些感兴趣的事物，\r\n我会认真对待自己的工作，尽力做到不断进步。","indexList":[]},"expectId":80717682,"expectType":0,"salary":"面议","middleContent":{"content":"友创·移动开发","indexList":[]},"jobId":151429687,"encryptJobId":"5008c4e58dd063dd1nF739-0FlpX","lid":"ZfLrziNm2f.notify_refresh.2","actionDateDesc":"2019年03月18日08:33","actionDate":1552869222000,"geekWorks":[{"id":0,"geekId":0,"company":"友创","industryCode":0,"industry":null,"industryCategory":null,"position":0,"positionCategory":null,"blueCollarPosition":false,"positionName":"移动开发","positionLv2":0,"isPublic":0,"department":null,"responsibility":null,"startDate":"2020.07","endDate":"2021.04","customPositionId":0,"customIndustryId":0,"workPerformance":null,"workEmphasisList":null,"certStatus":0,"workType":0,"addTime":null,"updateTime":null,"companyHighlight":null,"positionNameHighlight":null,"workTime":null,"workMonths":0,"current":false,"stillWork":false,"startYearMonStr":"2020","endYearMonStr":"2021","workTypeIntern":false},{"id":0,"geekId":0,"company":"广东精工智能系统","industryCode":0,"industry":null,"industryCategory":null,"position":0,"positionCategory":null,"blueCollarPosition":false,"positionName":"Java","positionLv2":0,"isPublic":0,"department":null,"responsibility":null,"startDate":"2019.04","endDate":"2020.06","customPositionId":0,"customIndustryId":0,"workPerformance":null,"workEmphasisList":null,"certStatus":0,"workType":0,"addTime":null,"updateTime":null,"companyHighlight":null,"positionNameHighlight":null,"workTime":null,"workMonths":0,"current":false,"stillWork":false,"startYearMonStr":"2019","endYearMonStr":"2020","workTypeIntern":false}],"geekEdu":{"id":0,"userId":0,"school":"广东开放大学","schoolId":0,"major":"计算机应用技术","degree":0,"degreeName":"大专","eduType":0,"startDate":"2020.01","endDate":"2023.01","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2020","endYearStr":"2023"},"geekEdus":[{"id":0,"userId":0,"school":"广东开放大学","schoolId":0,"major":"计算机应用技术","degree":0,"degreeName":"大专","eduType":0,"startDate":"2020.01","endDate":"2023.01","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2020","endYearStr":"2023"}],"expectLocation":101280100,"expectPosition":100901,"applyStatus":0,"activeSec":1620388262,"birthday":"20000204","expectLocationName":"广州","expectPositionName":"web前端","applyStatusDesc":"离职-随时到岗","freshGraduate":0,"ageDesc":"21岁","geekSource":0,"encryptGeekId":"b6961efc2ee977f00nN82tm-EFA~","anonymous":false,"contactStatus":null,"expectInfos":[],"experiences":[],"edus":[],"feedbackTitle":"选择不喜欢的原因，为您优化推荐","feedback":[{"code":16,"memo":"期望(web前端)与职位不符","showType":0,"feedbackL2List":null,"titleL2":null},{"code":10,"memo":"工作经历和\"HTML前端工程师\"无关","showType":0,"feedbackL2List":null,"titleL2":null},{"code":9,"memo":"行业经验与要求不符","showType":0,"feedbackL2List":null,"titleL2":null},{"code":3,"memo":"学历低于要求","showType":0,"feedbackL2List":null,"titleL2":null},{"code":15,"memo":"年龄不合适","showType":0,"feedbackL2List":null,"titleL2":null},{"code":4,"memo":"期望薪资偏高","showType":0,"feedbackL2List":null,"titleL2":null},{"code":13,"memo":"与工作地点距离远","showType":0,"feedbackL2List":null,"titleL2":null},{"code":5,"memo":"其他原因","showType":1,"feedbackL2List":null,"titleL2":null}],"interactDesc":{"content":"与您的职位匹配：HTML前端工程师","indexList":[{"start":8,"end":17}]},"clicked":false,"forHomePage":false,"homePageAction":0,"canUseDirectCall":false,"exposeEnhanced":null,"viewed":false,"contacting":false},"activeTimeDesc":"刚刚活跃","talkTimeDesc":"10天前由牛人发起沟通","positionName":"HTML前端工程师","cooperate":0,"isFriend":1,"itemId":0,"suid":"","geekCallStatus":0,"blur":0,"haveChatted":0,"tag":1,"mateShareId":0,"mateName":null,"shareMessage":0,"shareNote":null,"encryptShareId":"07b5df7e131fbe2e1w~~","friendGeek":true,"usingGeekCall":false,"blurGeek":false,"encryptGeekId":"b6961efc2ee977f00nN82tm-EFA~"},{"geekCard":{"securityId":"6AMJlSifDm2cK-d1ipRfelMeCeRMEPvfF___9rDY5zfHYgIDnK__39-OgMeYOByECMpR2R_7re1U-FjeM2A1-GLIWIMH69X8mDG9AkWXu5XyD_FTDUUkkL8o0Ozx7f7Oh8ZwHQWodkZFk9d5-LDgvVTn-KtpUnR86CIDLhKZQK5iEATSTzy9Nx8xjPpI-HEbLG1qI7B6SwWxl8EoUsofw8yYaoXLRtjuDoG4_AbV3N10KDFjBomP537t5ARVgUOBtQtvI6ORB4iNS5SBBp5Z4N4FzEJDF1BrkyhbU2SSgrWrprDk3pz_9xBftPjw2-A_ml5taBuLcPcPIPpzCoxRf__kNx-gdsKE1qt9Z0CuP6LzUFgLkaU4ukyLfLP1AhKOzSYq","geekId":512585553,"geekName":"笑嘻嘻","geekAvatar":"https://img.bosszhipin.com/beijin/upload/avatar/20210105/36bfd95c30e684940c8c79889072a834cc8c99d333519d55670bc384097e9296_s.png","geekGender":1,"geekWorkYear":"21年应届生","geekDegree":"大专","geekDesc":{"content":"学习过PS等软件","indexList":[]},"expectId":181950966,"expectType":1,"salary":"6-10K","middleContent":{"content":"毕业于广东南方职业学院·软件技术","indexList":[]},"jobId":151429687,"encryptJobId":"5008c4e58dd063dd1nF739-0FlpX","lid":"ZfLrziNm2f.notify_refresh.1","actionDateDesc":"01月05日15:30","actionDate":1609831824000,"geekWorks":null,"geekEdu":{"id":0,"userId":0,"school":"广东南方职业学院","schoolId":0,"major":"软件技术","degree":0,"degreeName":"大专","eduType":0,"startDate":"2018.01","endDate":"2021.01","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2018","endYearStr":"2021"},"geekEdus":[{"id":0,"userId":0,"school":"广东南方职业学院","schoolId":0,"major":"软件技术","degree":0,"degreeName":"大专","eduType":0,"startDate":"2018.01","endDate":"2021.01","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2018","endYearStr":"2021"}],"expectLocation":101280100,"expectPosition":10000009,"applyStatus":3,"activeSec":1620441629,"birthday":"19980901","expectLocationName":"广州","expectPositionName":"web前端","applyStatusDesc":"","freshGraduate":3,"ageDesc":"22岁","geekSource":0,"encryptGeekId":"8228ef749112e5610nV43tW4FVdT","anonymous":false,"contactStatus":null,"expectInfos":[],"experiences":[],"edus":[],"feedbackTitle":"选择不喜欢的原因，为您优化推荐","feedback":[{"code":16,"memo":"期望(web前端)与职位不符","showType":0,"feedbackL2List":null,"titleL2":null},{"code":10,"memo":"工作经历和\"HTML前端工程师\"无关","showType":0,"feedbackL2List":null,"titleL2":null},{"code":9,"memo":"行业经验与要求不符","showType":0,"feedbackL2List":null,"titleL2":null},{"code":3,"memo":"学历低于要求","showType":0,"feedbackL2List":null,"titleL2":null},{"code":15,"memo":"年龄不合适","showType":0,"feedbackL2List":null,"titleL2":null},{"code":4,"memo":"期望薪资偏高","showType":0,"feedbackL2List":null,"titleL2":null},{"code":13,"memo":"与工作地点距离远","showType":0,"feedbackL2List":null,"titleL2":null},{"code":5,"memo":"其他原因","showType":1,"feedbackL2List":null,"titleL2":null}],"interactDesc":{"content":"与您的职位匹配：HTML前端工程师","indexList":[{"start":8,"end":17}]},"clicked":false,"forHomePage":false,"homePageAction":0,"canUseDirectCall":false,"exposeEnhanced":null,"viewed":false,"contacting":false},"activeTimeDesc":"刚刚活跃","talkTimeDesc":null,"positionName":"HTML前端工程师","cooperate":0,"isFriend":0,"itemId":0,"suid":"","geekCallStatus":0,"blur":0,"haveChatted":0,"tag":1,"mateShareId":0,"mateName":null,"shareMessage":0,"shareNote":null,"encryptShareId":"07b5df7e131fbe2e1w~~","friendGeek":false,"usingGeekCall":false,"blurGeek":false,"encryptGeekId":"8228ef749112e5610nV43tW4FVdT"}],"tagDesc":"fresh"}}
     */
    private final String url_get_boss_geek = "https://www.zhipin.com/wapi/zprelation/interaction/bossGetGeek?jobid=-1&status=STATUS&refresh=REFRESH_TIMESTAMP&source=0&age=16,-1&gender=0&switchJobFrequency=0&exchangeResumeWithColleague=0&recentNotView=0&activation=0&school=0&major=0&experience=0&degree=0&salary=0&intention=0&cityCode=&districtCode=&businessId=&jobId=-1&page=1&tag=TAG&_=TIMESTAMP";

    /**
     * 搜索牛人
     * <p>
     * HTML前端工程师*广州*近一个月未与同事交换简历*近14天没有看过*16岁-23岁*在校/应届-在校/应届
     * <p>
     * tag: 前端开发 & filterParams={"sortType":1,"region":{"cityCode":101280100,"cityName":"广州","areas":[]}}
     * <p>
     * {"code":0,"message":"Success","zpData":{"needGuide":false,"startIndex":0,"hasMore":true,"page":1,"tag":1,"itemCardIdx":-1,"geekList":[{"geekCard":{"securityId":"jPPodbDV9E79e-v1U8pfoqehF4Tk5DR8kTnOFgH78V-Mo8IwrZWy4v6wTyTHxxnfAIAOsNK71S2Wdy4XvDFGMjkBZD10dr6HhW-jpHZMB_cYfRZy6K4-CyxXwRMLu22SINzLVR5o1fJjfMkaD33OiStkAAAfhSRwjZ38z6kdoRZv1KuPqhpir3jt2am3AUl2BOM3YWkFFXE609ODR09XjIEsXRFbBNGUgzh8zv0hJY5fLGV0Ok0QyVx5VKvrapa8ft8BzE3kZWw-sU1_-zq40p95p9277wg-zBjurRTquaOc2gTE8HL2xRXhoBevWmkByDFltirTy1UF0SZjyKNTbU09TOB6CqUa1PaJgIj1YWZXUUYM8gNYnqO5p9zKC_pCRg~~","geekId":57614302,"geekName":"卓建阳","geekAvatar":"https://img.bosszhipin.com/boss/avatar/avatar_15.png","geekGender":1,"geekWorkYear":"2年","geekDegree":"大专","geekDesc":{"content":"拥有较强的学习能力，能快速适应工作环境，独立生活能力强，同时富于团队精神和责任感!\r\n空闲时间喜欢在网上了解一些感兴趣的事物，\r\n我会认真对待自己的工作，尽力做到不断进步。","indexList":[]},"expectId":80717682,"expectType":0,"salary":"面议","middleContent":{"content":"友创·移动开发","indexList":[]},"jobId":151429687,"encryptJobId":"5008c4e58dd063dd1nF739-0FlpX","lid":"ZfLrziNm2f.notify_refresh.2","actionDateDesc":"2019年03月18日08:33","actionDate":1552869222000,"geekWorks":[{"id":0,"geekId":0,"company":"友创","industryCode":0,"industry":null,"industryCategory":null,"position":0,"positionCategory":null,"blueCollarPosition":false,"positionName":"移动开发","positionLv2":0,"isPublic":0,"department":null,"responsibility":null,"startDate":"2020.07","endDate":"2021.04","customPositionId":0,"customIndustryId":0,"workPerformance":null,"workEmphasisList":null,"certStatus":0,"workType":0,"addTime":null,"updateTime":null,"companyHighlight":null,"positionNameHighlight":null,"workTime":null,"workMonths":0,"current":false,"stillWork":false,"startYearMonStr":"2020","endYearMonStr":"2021","workTypeIntern":false},{"id":0,"geekId":0,"company":"广东精工智能系统","industryCode":0,"industry":null,"industryCategory":null,"position":0,"positionCategory":null,"blueCollarPosition":false,"positionName":"Java","positionLv2":0,"isPublic":0,"department":null,"responsibility":null,"startDate":"2019.04","endDate":"2020.06","customPositionId":0,"customIndustryId":0,"workPerformance":null,"workEmphasisList":null,"certStatus":0,"workType":0,"addTime":null,"updateTime":null,"companyHighlight":null,"positionNameHighlight":null,"workTime":null,"workMonths":0,"current":false,"stillWork":false,"startYearMonStr":"2019","endYearMonStr":"2020","workTypeIntern":false}],"geekEdu":{"id":0,"userId":0,"school":"广东开放大学","schoolId":0,"major":"计算机应用技术","degree":0,"degreeName":"大专","eduType":0,"startDate":"2020.01","endDate":"2023.01","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2020","endYearStr":"2023"},"geekEdus":[{"id":0,"userId":0,"school":"广东开放大学","schoolId":0,"major":"计算机应用技术","degree":0,"degreeName":"大专","eduType":0,"startDate":"2020.01","endDate":"2023.01","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2020","endYearStr":"2023"}],"expectLocation":101280100,"expectPosition":100901,"applyStatus":0,"activeSec":1620388262,"birthday":"20000204","expectLocationName":"广州","expectPositionName":"web前端","applyStatusDesc":"离职-随时到岗","freshGraduate":0,"ageDesc":"21岁","geekSource":0,"encryptGeekId":"b6961efc2ee977f00nN82tm-EFA~","anonymous":false,"contactStatus":null,"expectInfos":[],"experiences":[],"edus":[],"feedbackTitle":"选择不喜欢的原因，为您优化推荐","feedback":[{"code":16,"memo":"期望(web前端)与职位不符","showType":0,"feedbackL2List":null,"titleL2":null},{"code":10,"memo":"工作经历和\"HTML前端工程师\"无关","showType":0,"feedbackL2List":null,"titleL2":null},{"code":9,"memo":"行业经验与要求不符","showType":0,"feedbackL2List":null,"titleL2":null},{"code":3,"memo":"学历低于要求","showType":0,"feedbackL2List":null,"titleL2":null},{"code":15,"memo":"年龄不合适","showType":0,"feedbackL2List":null,"titleL2":null},{"code":4,"memo":"期望薪资偏高","showType":0,"feedbackL2List":null,"titleL2":null},{"code":13,"memo":"与工作地点距离远","showType":0,"feedbackL2List":null,"titleL2":null},{"code":5,"memo":"其他原因","showType":1,"feedbackL2List":null,"titleL2":null}],"interactDesc":{"content":"与您的职位匹配：HTML前端工程师","indexList":[{"start":8,"end":17}]},"clicked":false,"forHomePage":false,"homePageAction":0,"canUseDirectCall":false,"exposeEnhanced":null,"viewed":false,"contacting":false},"activeTimeDesc":"刚刚活跃","talkTimeDesc":"10天前由牛人发起沟通","positionName":"HTML前端工程师","cooperate":0,"isFriend":1,"itemId":0,"suid":"","geekCallStatus":0,"blur":0,"haveChatted":0,"tag":1,"mateShareId":0,"mateName":null,"shareMessage":0,"shareNote":null,"encryptShareId":"07b5df7e131fbe2e1w~~","friendGeek":true,"usingGeekCall":false,"blurGeek":false,"encryptGeekId":"b6961efc2ee977f00nN82tm-EFA~"},{"geekCard":{"securityId":"6AMJlSifDm2cK-d1ipRfelMeCeRMEPvfF___9rDY5zfHYgIDnK__39-OgMeYOByECMpR2R_7re1U-FjeM2A1-GLIWIMH69X8mDG9AkWXu5XyD_FTDUUkkL8o0Ozx7f7Oh8ZwHQWodkZFk9d5-LDgvVTn-KtpUnR86CIDLhKZQK5iEATSTzy9Nx8xjPpI-HEbLG1qI7B6SwWxl8EoUsofw8yYaoXLRtjuDoG4_AbV3N10KDFjBomP537t5ARVgUOBtQtvI6ORB4iNS5SBBp5Z4N4FzEJDF1BrkyhbU2SSgrWrprDk3pz_9xBftPjw2-A_ml5taBuLcPcPIPpzCoxRf__kNx-gdsKE1qt9Z0CuP6LzUFgLkaU4ukyLfLP1AhKOzSYq","geekId":512585553,"geekName":"笑嘻嘻","geekAvatar":"https://img.bosszhipin.com/beijin/upload/avatar/20210105/36bfd95c30e684940c8c79889072a834cc8c99d333519d55670bc384097e9296_s.png","geekGender":1,"geekWorkYear":"21年应届生","geekDegree":"大专","geekDesc":{"content":"学习过PS等软件","indexList":[]},"expectId":181950966,"expectType":1,"salary":"6-10K","middleContent":{"content":"毕业于广东南方职业学院·软件技术","indexList":[]},"jobId":151429687,"encryptJobId":"5008c4e58dd063dd1nF739-0FlpX","lid":"ZfLrziNm2f.notify_refresh.1","actionDateDesc":"01月05日15:30","actionDate":1609831824000,"geekWorks":null,"geekEdu":{"id":0,"userId":0,"school":"广东南方职业学院","schoolId":0,"major":"软件技术","degree":0,"degreeName":"大专","eduType":0,"startDate":"2018.01","endDate":"2021.01","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2018","endYearStr":"2021"},"geekEdus":[{"id":0,"userId":0,"school":"广东南方职业学院","schoolId":0,"major":"软件技术","degree":0,"degreeName":"大专","eduType":0,"startDate":"2018.01","endDate":"2021.01","eduDescription":null,"addTime":null,"updateTime":null,"timeSlot":null,"startYearStr":"2018","endYearStr":"2021"}],"expectLocation":101280100,"expectPosition":10000009,"applyStatus":3,"activeSec":1620441629,"birthday":"19980901","expectLocationName":"广州","expectPositionName":"web前端","applyStatusDesc":"","freshGraduate":3,"ageDesc":"22岁","geekSource":0,"encryptGeekId":"8228ef749112e5610nV43tW4FVdT","anonymous":false,"contactStatus":null,"expectInfos":[],"experiences":[],"edus":[],"feedbackTitle":"选择不喜欢的原因，为您优化推荐","feedback":[{"code":16,"memo":"期望(web前端)与职位不符","showType":0,"feedbackL2List":null,"titleL2":null},{"code":10,"memo":"工作经历和\"HTML前端工程师\"无关","showType":0,"feedbackL2List":null,"titleL2":null},{"code":9,"memo":"行业经验与要求不符","showType":0,"feedbackL2List":null,"titleL2":null},{"code":3,"memo":"学历低于要求","showType":0,"feedbackL2List":null,"titleL2":null},{"code":15,"memo":"年龄不合适","showType":0,"feedbackL2List":null,"titleL2":null},{"code":4,"memo":"期望薪资偏高","showType":0,"feedbackL2List":null,"titleL2":null},{"code":13,"memo":"与工作地点距离远","showType":0,"feedbackL2List":null,"titleL2":null},{"code":5,"memo":"其他原因","showType":1,"feedbackL2List":null,"titleL2":null}],"interactDesc":{"content":"与您的职位匹配：HTML前端工程师","indexList":[{"start":8,"end":17}]},"clicked":false,"forHomePage":false,"homePageAction":0,"canUseDirectCall":false,"exposeEnhanced":null,"viewed":false,"contacting":false},"activeTimeDesc":"刚刚活跃","talkTimeDesc":null,"positionName":"HTML前端工程师","cooperate":0,"isFriend":0,"itemId":0,"suid":"","geekCallStatus":0,"blur":0,"haveChatted":0,"tag":1,"mateShareId":0,"mateName":null,"shareMessage":0,"shareNote":null,"encryptShareId":"07b5df7e131fbe2e1w~~","friendGeek":false,"usingGeekCall":false,"blurGeek":false,"encryptGeekId":"8228ef749112e5610nV43tW4FVdT"}],"tagDesc":"fresh"}}
     */
    private final String url_search_geek = "https://www.zhipin.com/wapi/zpitem/web/boss/search/geeks.json?page=1&jobId=JOBID&keywords=&tag=TAG&city=101280100&gender=-1&experience=-3,-1&salary=1,4&age=16,23&schoolLevel=-1&applyStatus=-1&degree=201,201&switchFreq=0&geekJobRequirements=0&exchangeResume=1&viewResume=1&source=4&majors=&activeness=0&filterParams=%7B%22sortType%22:1,%22region%22:%7B%22cityCode%22:101280100,%22cityName%22:%22%E5%B9%BF%E5%B7%9E%22,%22areas%22:[]%7D%7D&_=TIMESTAMP";

    /**
     * 检查工作状态
     * <p>
     * {"code":0,"message":"Success","zpData":{"checkJobOpenResult":{"needOpen":false,"encJid":"6b5bf76db6a660201nF73969FFtX","jobName":null,"jobCity":null,"jobSalary":null}}}
     */
    private final String url_check_job = "https://www.zhipin.com/wapi/zpboss/h5/geek/detail/checkJobOpen?gid=QID&jid=JID&expectId=EXPECTID&_=TIMESTAMP";

    /**
     * 开始沟通 POST
     * <p>
     * gid=27fe1bb9e6f21aa71nV63NW5EltQ&suid=&jid=6b5bf76db6a660201nF73969FFtX&expectId=133014504&lid=YFH2WL9l8D.notify_refresh.59&from=&securityId=sIGLmy8XmYuMZ-K1OCeqXBkZsn6H0M7uKabz3IdpoEbYc5Gf4NuWBe2ienHMng8EwMHHAh24uE1VUf8J0sPLaGVOB3YZoPdd02XNiR2MM_gBBe7zQpfE3mZVdPSOPPrtt3X3bTjgWjCX89fHcLwY_wnULM4BMl-gIu4r-i-US8oNcikWfPlyDmev0i9G2-eAWzUsPAGbytWi9TJozx6vFsm2yjJS9mZzi3ayLhRqgiyrbDS9WQNpSqvFW1NAm75dKcpqpMwx5DMsMHHOsSrrd11uz_bvD0k3CWubxDZXq17Fw8gA5XbxYl87pcmxA2ZsYUsxgkYybJD1uq1QGhxacAuzUHMmwwsm-f526VgCYLQlKVKoa87sHWNpttRJKd7pcsks
     * <p>
     * gid=5236802ec7e596080nZy09W6GVZZ&suid=&jid=f9c6a8f8bdd82f1c1nF639W-FVdT&expectId=235138051&lid=YucKZHWuvP.notify_seen.19&greet%5BisTrusted%5D=true&from=&securityId=efG5ImZPP7b41-x1xb99zVxhHXm6E6Lc4MMevBAzYEeDczdb5QssIUtc9BJYIJy609XF1jm7hO78dK1EcnNMMyPTidtkgMXTp1kv7u6BtUtIjSJ6E632Gg7S9mqIX5DXm-zVCIUbAQrQFUfP17WQV73Crq4hnhjue-Vs2GcvUtA0rKJU_Lno-t4TDqClMBaB7krkw_PsZWU4e_OyJ6UmOYCA4ODtDg7SWCu-uUktkV1CMftqxndasSUmL6QvxtYWjQY2wmcn7fXakQvOxaSk7L69R8mcylpb9DX-hR5OF8tEhWmAGB0Nzaol4ZkyqCJR6US1p_IRGsvr5ERXiQhJwfZGjy2xkABnoZ0ZhVCSatD0On8z2X1HcilfegJovZr6hw~~
     * <p>
     * {"code":0,"message":"Success","zpData":{"priceId":null,"targetId":null,"targetType":0,"payMode":0,"view":0,"chat":0,"securityId":null,"configId":null,"experiencePriceId":null,"newfriend":1,"limitTitle":null,"stateDesc":null,"stateDes":null,"status":1,"greeting":"你好，我们最近有在招PHP开发助理，您可以看一下职位信息，如果有兴趣，期待您的回复","greetingTip":1,"ba":null,"business":0,"chatToast":null,"blockPageData":null,"rightsUseTip":null,"notice":{"need":false,"msg":null,"usablePrivilegeMsg":null,"purchasableMsg":null}}}
     */
    private final String url_start_chat = "https://www.zhipin.com/wapi/zpboss/h5/chat/start?_=TIMESTAMP";


    /**
     * 获取二维码key
     */
    public String getQrcodeKey() {
        String resp = HttpUtil.doGet(url_qrcode_key);
        BossResp<QrcodeKey> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
        }
        return bossResp.getZpData(QrcodeKey.class).getQrId();
    }

    /**
     * 获取二维码
     */
    public String getQrcodePath(String qrId) {
        String dest;
        if (FileUtil.isWindows()) {
            dest = "D:\\" + qrId + ".jpeg";
        } else {
            dest = "root/boss/qrcode/" + qrId + ".jpeg";
        }
        long size = cn.hutool.http.HttpUtil.downloadFile(url_get_qrcode.replace("QRID", qrId), dest);
        if (size > 0) {
            log.info("download qrcode ok...\n{}", dest);
        }
        return dest;
    }

    /**
     * 监听扫码，扫了
     */
    public boolean scanOk(String qrId) {
        String url = url_scan_qrcode
            .replace("QRID", qrId)
            .replace("TIMESTAMP", String.valueOf(System.currentTimeMillis()));
        String resp = HttpUtil.doGet(url);
        ScanResp scanResp = JSON.parseObject(resp, ScanResp.class);

        if (scanResp.isScaned()) {
            log.info("scan ok...");
            return true;
        }
        return false;
    }

    /**
     * 监听扫码，登了
     */
    public boolean scanAndLoginOk(String qrId) {
        String url = url_scan_login
            .replace("QRID", qrId)
            .replace("TIMESTAMP", String.valueOf(System.currentTimeMillis()));
        String resp = HttpUtil.doGet(url);
        ScanResp scanResp = JSON.parseObject(resp, ScanResp.class);
        System.out.println(resp);

        if (scanResp.isLogin()) {
            log.info("login ok...");
            return true;
        }
        return false;
    }

    /**
     * 扫码后获取头像
     */
    public String getHeadImg(String qrId) {
        String url = url_get_head_img
            .replace("QRID", qrId)
            .replace("TIMESTAMP", String.valueOf(System.currentTimeMillis()));
        String resp = HttpUtil.doGet(url);
        BossResp<Avatar> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
        }
        return bossResp.getZpData(Avatar.class).getLarge();
    }

    /**
     * 登录后获取路由，返回cookie
     */
    public Cookie getDispatcher(String qrId) {
        String url = url_get_dispatcher
            .replace("QRID", qrId)
            .replace("TIMESTAMP", String.valueOf(System.currentTimeMillis()));
        ResponseEntity<String> resp = HttpUtil.doGetReturnEntity(url);
        BossResp<Dispatcher> bossResp = JSON.parseObject(resp.getBody(), BossResp.class);
        if (bossResp == null || !bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
        }

        //get cookie
        return HttpUtil.getCookie(Cookie.class, resp);
    }

    /**
     * 添加日志
     */
    public void addLog(String cookie, String logContent) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("ba", logContent);
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        headers.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        String resp = HttpUtil.doPost(url_add_login_log, headers, params, String.class);
        BossResp<Boolean> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk() || Boolean.FALSE.equals(bossResp.getZpData())) {
            log.error(String.valueOf(bossResp));
            return;
        }
        log.info("add log ok... {}", logContent);
    }

    /**
     * 获取新的token
     */
    public String getZpToken(String cookie) {
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        String resp = HttpUtil.doGet(url_get_zp_token, headers);
        BossResp<Token> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return null;
        }
        return bossResp.getZpData(Token.class).getToken();
    }

    /**
     * 获取朋友1
     */
    public List<ResultBean> getFriendSimpleList(String cookie) {
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        headers.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, Object> params = Maps.newHashMap();
//        jobId=-1&workflow=%E5%85%A8%E9%83%A8&label=&conversationType=
        params.put("jobId", -1);
        params.put("workflow", "全部");
        params.put("label", null);
        params.put("conversationType", null);
        String resp = HttpUtil.doPost(url_get_friend_simple_list, headers, params, String.class);
        BossResp<Friend1> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return Lists.newArrayList();
        }
        return bossResp.getZpData(Friend1.class).getResult();
    }

    /**
     * 获取朋友2
     */
    public List<FriendListBean> getFriendFullList(String cookie, List<String> friendList) {
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        headers.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, Object> params = Maps.newHashMap();
//        page=1&friendIds=44429488,530106854,27744241&dzFriendIds=
        params.put("page", 1);
        params.put("friendIds", String.join(",", friendList));
        params.put("dzFriendIds", null);
        String resp = HttpUtil.doPost(url_get_friend_full_list, headers, params, String.class);
        BossResp<Friend2> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return Lists.newArrayList();
        }
        return bossResp.getZpData(Friend2.class).getFriendList();
    }

    /**
     * 获取朋友最后一条聊天信息
     */
    public List<LastMsg> getLastMsg(String cookie, List<String> friendIdList) {
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        headers.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, Object> params = Maps.newHashMap();
//        page=1&friendIds=44429488,530106854,27744241&dzFriendIds=
        params.put("page", 1);
        params.put("friendIds", String.join(",", friendIdList));
        params.put("dzFriendIds", null);
        String resp = HttpUtil.doPost(url_get_last_msg, headers, params, String.class);
        BossResp<List> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return Lists.newArrayList();
        }
        return bossResp.getZpData(List.class);
    }

    /**
     * 获取朋友历史聊天记录
     */
    public List<MessagesBean> getHistoryMsg(String cookie, String friendId) {
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        String resp = HttpUtil.doGet(url_get_history_msg.replace("GID", friendId), headers);
        BossResp<HistoryMsg> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return Lists.newArrayList();
        }
        return bossResp.getZpData(HistoryMsg.class).getMessages();
    }

    /**
     * 接收简历
     * <p>
     * <b>http2.0！<b/>
     * <b>mid=msgId!<b/>
     *
     * @param securityId friendV2
     */
    public void exchangeAccept(String cookie, String msgId, String securityId) {

        Map<String, Object> headers = Maps.newHashMap();
        headers.put(":authority", "www.zhipin.com");
        headers.put(":method", "POST");
        headers.put(":path", "/wapi/zpchat/exchange/accept");
        headers.put(":scheme", "https");
        headers.put("pragma", "no-cache");
        String zpToken = getZpToken(cookie);
        headers.put("zp_token", zpToken);
        headers.put("cookie", cookie + "zpToken:" + zpToken);
        headers.put("referer", "https://www.zhipin.com/web/boss/index");
        headers.put("origin", "https://www.zhipin.com");
        headers.put("content-type", "application/x-www-form-urlencoded");
        headers.put("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"90\", \"Google Chrome\";v=\"90\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-origin");
        headers.put("user-agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        Map<String, Object> params = Maps.newHashMap();
//        mid=162378990781&type=3&securityId=nJB5gbMv3c-ui-61fRfgfAyhPTDEMxk8Uq2Ox0_kgmskHgUJjDBaNXF6GAMzYullnwjQkO60Z6ZObDqdAIHInspVWCz6KUpujurz85XB5BwpI-PckHPBDMIjh4mHBp1s9k7sf3w76UkaHTC69X-HZ0ZSkHgmZRaKN-GFu3vS2tFDmT3f39RxEttlqmZd3O8_raiLbwtSjOPHUZoN9by183coiFgga65BMI_NUKPqjLYx6Na-T4qHdRtjacm8mYDlIAaA0Nr1imwjVsgxEuBnyuBo2VB91xc1gI6n7ql5TvnpyTm2goigc3rgEqzUJ7MUm9FZ6H53iEtDgz47Br98trtD3JpsOs17UAaXgUJlbnyyIBoXX73YqTikDT0kkbIlrp9U3rmpVMp6PPLBZ0V4ED1c2NVc
        params.put("mid", msgId);
        params.put("type", 3);
        params.put("securityId", securityId);
        String resp = HttpUtil.doPostByHttp2(url_exchange_accept, headers, params, String.class);
        BossResp<Exchange> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return;
        }

        log.info("exchange accept ok... {}", bossResp.getZpData());
    }

    /**
     * 请求简历1
     * <p>
     * <b>http2.0！<b/>
     *
     * @param securityId friendV2
     */
    public void exchangeTest(String cookie, String securityId) {

        Map<String, Object> headers = Maps.newHashMap();
        headers.put(":authority", "www.zhipin.com");
        headers.put(":method", "POST");
        headers.put(":path", "/wapi/zpchat/exchange/test");
        headers.put(":scheme", "https");
        headers.put("pragma", "no-cache");
        String zpToken = getZpToken(cookie);
        headers.put("zp_token", zpToken);
        headers.put("cookie", cookie + "zpToken:" + zpToken);
        headers.put("referer", "https://www.zhipin.com/web/boss/geek-manage/geek");
        headers.put("origin", "https://www.zhipin.com");
        headers.put("content-type", "application/x-www-form-urlencoded");
        headers.put("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"90\", \"Google Chrome\";v=\"90\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-origin");
        headers.put("user-agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        Map<String, Object> params = Maps.newHashMap();
//        mid=162378990781&type=3&securityId=nJB5gbMv3c-ui-61fRfgfAyhPTDEMxk8Uq2Ox0_kgmskHgUJjDBaNXF6GAMzYullnwjQkO60Z6ZObDqdAIHInspVWCz6KUpujurz85XB5BwpI-PckHPBDMIjh4mHBp1s9k7sf3w76UkaHTC69X-HZ0ZSkHgmZRaKN-GFu3vS2tFDmT3f39RxEttlqmZd3O8_raiLbwtSjOPHUZoN9by183coiFgga65BMI_NUKPqjLYx6Na-T4qHdRtjacm8mYDlIAaA0Nr1imwjVsgxEuBnyuBo2VB91xc1gI6n7ql5TvnpyTm2goigc3rgEqzUJ7MUm9FZ6H53iEtDgz47Br98trtD3JpsOs17UAaXgUJlbnyyIBoXX73YqTikDT0kkbIlrp9U3rmpVMp6PPLBZ0V4ED1c2NVc
        params.put("check", true);
        params.put("type", 4);
        params.put("securityId", securityId);
        String resp = HttpUtil.doPostByHttp2(url_exchange_test, headers, params, String.class);
        BossResp<Exchange> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return;
        }

        log.info("exchange test ok... {}", bossResp.getZpData());
    }

    /**
     * 请求简历2
     * <p>
     * <b>http2.0！<b/>
     *
     * @param securityId friendV2
     */
    public void exchangeRequest(String cookie, String securityId) {

        Map<String, Object> headers = Maps.newHashMap();
        headers.put(":authority", "www.zhipin.com");
        headers.put(":method", "POST");
        headers.put(":path", "/wapi/zpchat/exchange/request");
        headers.put(":scheme", "https");
        headers.put("pragma", "no-cache");
        String zpToken = getZpToken(cookie);
        headers.put("zp_token", zpToken);
        headers.put("cookie", cookie + "zpToken:" + zpToken);
        headers.put("referer", "https://www.zhipin.com/web/boss/index");
        headers.put("origin", "https://www.zhipin.com");
        headers.put("content-type", "application/x-www-form-urlencoded");
        headers.put("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"90\", \"Google Chrome\";v=\"90\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-origin");
        headers.put("user-agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        Map<String, Object> params = Maps.newHashMap();
//        mid=162378990781&type=3&securityId=nJB5gbMv3c-ui-61fRfgfAyhPTDEMxk8Uq2Ox0_kgmskHgUJjDBaNXF6GAMzYullnwjQkO60Z6ZObDqdAIHInspVWCz6KUpujurz85XB5BwpI-PckHPBDMIjh4mHBp1s9k7sf3w76UkaHTC69X-HZ0ZSkHgmZRaKN-GFu3vS2tFDmT3f39RxEttlqmZd3O8_raiLbwtSjOPHUZoN9by183coiFgga65BMI_NUKPqjLYx6Na-T4qHdRtjacm8mYDlIAaA0Nr1imwjVsgxEuBnyuBo2VB91xc1gI6n7ql5TvnpyTm2goigc3rgEqzUJ7MUm9FZ6H53iEtDgz47Br98trtD3JpsOs17UAaXgUJlbnyyIBoXX73YqTikDT0kkbIlrp9U3rmpVMp6PPLBZ0V4ED1c2NVc
        params.put("type", 4);
        params.put("securityId", securityId);
        String resp = HttpUtil.doPostByHttp2(url_exchange_request, headers, params, String.class);
        BossResp<Exchange> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return;
        }

        log.info("exchange request ok... {}", bossResp.getZpData());
    }

    /**
     * 获取我的招聘岗位 rec
     */
    public RecJobList getRecJobList(String cookie) {
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        String resp = HttpUtil.doGet(url_get_rec_job_list, headers);
        BossResp<RecJobList> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
        }
        return bossResp.getZpData(RecJobList.class);
    }

    /**
     * 获取我的招聘岗位 2
     */
    public List<JobList> getJobList(String cookie) {
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        String resp = HttpUtil.doGet(url_get_job_list, headers);
        BossResp<List<JobList>> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
        }
        return bossResp.getZpDataArray(JobList.class);
    }

    /**
     * 获取系统推荐牛人
     */
    public List<GeekListBean> getRecommendGeek(String cookie, String jobId) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String url = url_get_recommend_geek
            .replaceAll("JOBID", jobId)
            .replace("TIMESTAMP", timestamp)
            .replace("REFRESH_TIMESTAMP", timestamp);
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        String resp = HttpUtil.doGet(url, headers);
        BossResp<Geek1> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return Lists.newArrayList();
        }

        return bossResp.getZpData(Geek1.class).getGeekList();
    }

    /**
     * 获取新牛人
     */
    public List<NewGeekItemBean> getNewGeekList(String cookie, String jobId) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String url = url_get_boss_geek
            .replace("STATUS", "1")
            .replace("TAG", "1")
            .replace("TIMESTAMP", timestamp)
            .replace("REFRESH_TIMESTAMP", timestamp);
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        String resp = HttpUtil.doGet(url, headers);
        BossResp<NewGeek> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return Lists.newArrayList();
        }

        return bossResp.getZpData(NewGeek.class).getGeekList();
    }

    /**
     * 检查我的岗位状态
     */
    public void checkJob(String cookie, String qId, String jId, String expectId) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String url = url_check_job
            .replace("QID", qId)
            .replace("JID", jId)
            .replace("EXPECTID", expectId)
            .replace("TIMESTAMP", timestamp);
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        String resp = HttpUtil.doGet(url, headers);
        BossResp<CheckJob> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return;
        }

        CheckJob zpData = bossResp.getZpData(CheckJob.class);
        if (!zpData.getCheckJobOpenResult().getNeedOpen()) {
            log.error("needOpen job... {}", zpData.getCheckJobOpenResult());
        }
    }

    /**
     * 开启沟通
     * <p>
     * getRecommendGeek
     * <b>gid>encryptGeekId</b>
     * <b>jid>encryptJobId</b>
     * <b>expectId>expectId</b>
     * <b>lid>lid</b>
     * <b>securityId>securityId</b>
     */
    public void startChat(String cookie, String gId, String jId, String lId, String expectId, String securityId) {
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        headers.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, Object> params = Maps.newHashMap();
        params.put("gid", gId);
        params.put("suid", null);
        params.put("jid", jId);
        params.put("expectId", expectId);
        //YFH2WL9l8D.notify_refresh.59
        //YucKZHWuvP.notify_seen.19&greet%5BisTrusted%5D=true
        params.put("lid", lId);
        params.put("from", null);
        params.put("securityId", securityId);
        String url = url_start_chat.replace("TIMESTAMP", String.valueOf(System.currentTimeMillis()));
        String resp = HttpUtil.doPost(url, headers, params, String.class);
        BossResp<StartChat> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return;
        }

        StartChat zpData = bossResp.getZpData(StartChat.class);
        if (zpData.getNewfriend() == 1 && zpData.getStatus() == 1) {
            log.info("start chat ok... {}", zpData.getGreeting());
        }
    }

    /**
     * 同步朋友状态
     */
    public void syncFriendStatusInfo(String cookie, String friendId) {
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        String resp = HttpUtil.doGet(url_sync_friend_status_info.replace("FRIENDID", friendId), headers);
        BossResp<SyncFriendStatus> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
        }

        log.info("sync friend status ok... {}", bossResp.getZpData(SyncFriendStatus.class));
    }

    /**
     * 获取牛人详情
     */
    public GeekInfo getGeekInfo(String cookie, String friendId, String securityId) {
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        String url = url_get_geek_info
            .replace("FRIENDID", friendId)
            .replace("SECURITYID", securityId);
        String resp = HttpUtil.doGet(url, headers);
        BossResp<GeekInfo> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
        }

        return bossResp.getZpData(GeekInfo.class);
    }

    /**
     * boosEnter
     * <p>
     * <b>geekId>geek.json>encryptUid</b>
     * <p>
     * <b>expectId>geek.json>encryptExpectId</b>
     * <p>
     * <b>jobId>geek.json>toPositionId</b>
     * <p>
     * <b>securityId>geek.json>securityId</b>
     */
    public void bossEnter(String cookie, String geekId, String expectId, String jobId, String securityId) {
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        headers.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, Object> params = Maps.newHashMap();
        params.put("geekId", geekId);
        params.put("expectId", expectId);
        params.put("jobId", jobId);
        params.put("securityId", securityId);
        String resp = HttpUtil.doPost(url_boss_enter, headers, params, String.class);
        BossResp<StartChat> bossResp = JSON.parseObject(resp, BossResp.class);
        if (!bossResp.isDataOk()) {
            log.error(String.valueOf(bossResp));
            return;
        }

        log.info("bossEnter ok...");
    }


}
