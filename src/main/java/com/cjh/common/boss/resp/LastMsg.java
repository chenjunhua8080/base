package com.cjh.common.boss.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LastMsg {


    /**
     * lastTime : 18:02
     * uid : 84191709
     * lastMsgInfo : {"msgId":162718684674,"encryptMsgId":"cf749f7ba0e2e6511nJ43Ny1FlpUxIu7","showText":"对方想发送附件简历到您邮箱，您是否同意","fromId":84191709,"toId":85014932,"status":0,"msgTime":1620468141463}
     * encryptUid : 249d4c5b2ffddb0833B70ty6EFs~
     * lastTS : 1620468141463
     */

    private String lastTime;
    private Integer uid;
    private LastMsgInfoBean lastMsgInfo;
    private String encryptUid;
    private Long lastTS;

    @NoArgsConstructor
    @Data
    public static class LastMsgInfoBean {

        /**
         * msgId : 162718684674
         * encryptMsgId : cf749f7ba0e2e6511nJ43Ny1FlpUxIu7
         * showText : 对方想发送附件简历到您邮箱，您是否同意
         * fromId : 84191709
         * toId : 85014932
         * status : 0
         * msgTime : 1620468141463
         */

        private Long msgId;
        private String encryptMsgId;
        private String showText;
        private Integer fromId;
        private Integer toId;
        private Integer status;
        private Long msgTime;
    }

}
