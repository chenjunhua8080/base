package com.cjh.common.resp;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HomeDataResp {

    /**
     * code : 0
     * data : {"bizCode":0,"bizMsg":"success","result":{"activityInfo":{"activityEndTime":1592668799000,"activityStartTime":1589903999000,"mainAwardStartTime":1592481600000,"mainWaitLotteryStartTime":1592467200000,"nowTime":1590291978570},"cakeBakerInfo":{"jdCipher":0,"raiseInfo":{"brandFlag":true,"buttonStatus":1,"curLevelStartScore":"600","firstStatus":0,"fullFlag":false,"levelImageList":["https://m.360buyimg.com/babel/jfs/t1/123756/31/152/48661/5eb3b25aE9bd0a75a/c6a314732a2a1d84.png","https://m.360buyimg.com/babel/jfs/t1/111636/5/5442/45217/5eb3b2afEfb9ba929/15cf8a90ae00dd0c.png","https://m.360buyimg.com/babel/jfs/t1/121050/11/155/44829/5eb3b2c3Ebb2a7a8d/708e52f055ddc5f9.png"],"maxLevelScore":"156850","nextLevelScore":"1400","pkButtonStatus":1,"raiseButtonShow":2,"remainScore":"633","scoreLevel":3,"signPopStatus":0,"totalScore":"1233","usedScore":"600","wxPayStatus":0},"secretp":"Vl4IStM3QydXJKE38Wr7YB6e33c","shareMiniprogramSwitch":0,"userType":1}},"success":true}
     * msg : 调用成功
     */

    private int code;
    private DataBean data;
    private String msg;

    @NoArgsConstructor
    @Data
    public static class DataBean {

        /**
         * bizCode : 0
         * bizMsg : success
         * result : {"activityInfo":{"activityEndTime":1592668799000,"activityStartTime":1589903999000,"mainAwardStartTime":1592481600000,"mainWaitLotteryStartTime":1592467200000,"nowTime":1590291978570},"cakeBakerInfo":{"jdCipher":0,"raiseInfo":{"brandFlag":true,"buttonStatus":1,"curLevelStartScore":"600","firstStatus":0,"fullFlag":false,"levelImageList":["https://m.360buyimg.com/babel/jfs/t1/123756/31/152/48661/5eb3b25aE9bd0a75a/c6a314732a2a1d84.png","https://m.360buyimg.com/babel/jfs/t1/111636/5/5442/45217/5eb3b2afEfb9ba929/15cf8a90ae00dd0c.png","https://m.360buyimg.com/babel/jfs/t1/121050/11/155/44829/5eb3b2c3Ebb2a7a8d/708e52f055ddc5f9.png"],"maxLevelScore":"156850","nextLevelScore":"1400","pkButtonStatus":1,"raiseButtonShow":2,"remainScore":"633","scoreLevel":3,"signPopStatus":0,"totalScore":"1233","usedScore":"600","wxPayStatus":0},"secretp":"Vl4IStM3QydXJKE38Wr7YB6e33c","shareMiniprogramSwitch":0,"userType":1}}
         * success : true
         */

        private int bizCode;
        private String bizMsg;
        private ResultBean result;
        private boolean success;

        @NoArgsConstructor
        @Data
        public static class ResultBean {

            /**
             * activityInfo : {"activityEndTime":1592668799000,"activityStartTime":1589903999000,"mainAwardStartTime":1592481600000,"mainWaitLotteryStartTime":1592467200000,"nowTime":1590291978570}
             * cakeBakerInfo : {"jdCipher":0,"raiseInfo":{"brandFlag":true,"buttonStatus":1,"curLevelStartScore":"600","firstStatus":0,"fullFlag":false,"levelImageList":["https://m.360buyimg.com/babel/jfs/t1/123756/31/152/48661/5eb3b25aE9bd0a75a/c6a314732a2a1d84.png","https://m.360buyimg.com/babel/jfs/t1/111636/5/5442/45217/5eb3b2afEfb9ba929/15cf8a90ae00dd0c.png","https://m.360buyimg.com/babel/jfs/t1/121050/11/155/44829/5eb3b2c3Ebb2a7a8d/708e52f055ddc5f9.png"],"maxLevelScore":"156850","nextLevelScore":"1400","pkButtonStatus":1,"raiseButtonShow":2,"remainScore":"633","scoreLevel":3,"signPopStatus":0,"totalScore":"1233","usedScore":"600","wxPayStatus":0},"secretp":"Vl4IStM3QydXJKE38Wr7YB6e33c","shareMiniprogramSwitch":0,"userType":1}
             */

            private ActivityInfoBean activityInfo;
            private CakeBakerInfoBean cakeBakerInfo;

            @NoArgsConstructor
            @Data
            public static class ActivityInfoBean {

                /**
                 * activityEndTime : 1592668799000
                 * activityStartTime : 1589903999000
                 * mainAwardStartTime : 1592481600000
                 * mainWaitLotteryStartTime : 1592467200000
                 * nowTime : 1590291978570
                 */

                private long activityEndTime;
                private long activityStartTime;
                private long mainAwardStartTime;
                private long mainWaitLotteryStartTime;
                private long nowTime;
            }

            @NoArgsConstructor
            @Data
            public static class CakeBakerInfoBean {

                /**
                 * jdCipher : 0
                 * raiseInfo : {"brandFlag":true,"buttonStatus":1,"curLevelStartScore":"600","firstStatus":0,"fullFlag":false,"levelImageList":["https://m.360buyimg.com/babel/jfs/t1/123756/31/152/48661/5eb3b25aE9bd0a75a/c6a314732a2a1d84.png","https://m.360buyimg.com/babel/jfs/t1/111636/5/5442/45217/5eb3b2afEfb9ba929/15cf8a90ae00dd0c.png","https://m.360buyimg.com/babel/jfs/t1/121050/11/155/44829/5eb3b2c3Ebb2a7a8d/708e52f055ddc5f9.png"],"maxLevelScore":"156850","nextLevelScore":"1400","pkButtonStatus":1,"raiseButtonShow":2,"remainScore":"633","scoreLevel":3,"signPopStatus":0,"totalScore":"1233","usedScore":"600","wxPayStatus":0}
                 * secretp : Vl4IStM3QydXJKE38Wr7YB6e33c
                 * shareMiniprogramSwitch : 0
                 * userType : 1
                 */

                private int jdCipher;
                private RaiseInfoBean raiseInfo;
                private String secretp;
                private int shareMiniprogramSwitch;
                private int userType;

                @NoArgsConstructor
                @Data
                public static class RaiseInfoBean {

                    /**
                     * brandFlag : true
                     * buttonStatus : 1
                     * curLevelStartScore : 600
                     * firstStatus : 0
                     * fullFlag : false
                     * levelImageList : ["https://m.360buyimg.com/babel/jfs/t1/123756/31/152/48661/5eb3b25aE9bd0a75a/c6a314732a2a1d84.png","https://m.360buyimg.com/babel/jfs/t1/111636/5/5442/45217/5eb3b2afEfb9ba929/15cf8a90ae00dd0c.png","https://m.360buyimg.com/babel/jfs/t1/121050/11/155/44829/5eb3b2c3Ebb2a7a8d/708e52f055ddc5f9.png"]
                     * maxLevelScore : 156850
                     * nextLevelScore : 1400
                     * pkButtonStatus : 1
                     * raiseButtonShow : 2
                     * remainScore : 633
                     * scoreLevel : 3
                     * signPopStatus : 0
                     * totalScore : 1233
                     * usedScore : 600
                     * wxPayStatus : 0
                     */

                    private boolean brandFlag;
                    private int buttonStatus;
                    private String curLevelStartScore;
                    private int firstStatus;
                    private boolean fullFlag;
                    private String maxLevelScore;
                    private int nextLevelScore;
                    private int pkButtonStatus;
                    private int raiseButtonShow;
                    private int remainScore;
                    private int scoreLevel;
                    private int signPopStatus;
                    private String totalScore;
                    private String usedScore;
                    private int wxPayStatus;
                    private List<String> levelImageList;
                }
            }
        }
    }
}
