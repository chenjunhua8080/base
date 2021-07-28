package com.cjh.common.boss.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StartChat {


    /**
     * priceId : null
     * targetId : null
     * targetType : 0
     * payMode : 0
     * view : 0
     * chat : 0
     * securityId : null
     * configId : null
     * experiencePriceId : null
     * newfriend : 1
     * limitTitle : null
     * stateDesc : null
     * stateDes : null
     * status : 1
     * greeting : 你好，我们最近有在招PHP开发助理，您可以看一下职位信息，如果有兴趣，期待您的回复
     * greetingTip : 1
     * ba : null
     * business : 0
     * chatToast : null
     * blockPageData : null
     * rightsUseTip : null
     * notice : {"need":false,"msg":null,"usablePrivilegeMsg":null,"purchasableMsg":null}
     */

    private Object priceId;
    private Object targetId;
    private Integer targetType;
    private Integer payMode;
    private Integer view;
    private Integer chat;
    private Object securityId;
    private Object configId;
    private Object experiencePriceId;
    private Integer newfriend;
    private Object limitTitle;
    private Object stateDesc;
    private Object stateDes;
    private Integer status;
    private String greeting;
    private Integer greetingTip;
    private Object ba;
    private Integer business;
    private Object chatToast;
    private Object blockPageData;
    private Object rightsUseTip;
    private NoticeBean notice;

    @NoArgsConstructor
    @Data
    public static class NoticeBean {

        /**
         * need : false
         * msg : null
         * usablePrivilegeMsg : null
         * purchasableMsg : null
         */

        private Boolean need;
        private Object msg;
        private Object usablePrivilegeMsg;
        private Object purchasableMsg;
    }

}
