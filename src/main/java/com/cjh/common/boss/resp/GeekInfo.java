package com.cjh.common.boss.resp;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GeekInfo {


    /**
     * data
     */
    private DataBean data;
    /**
     * status
     */
    private Integer status;

    /**
     * DataBean
     */
    @NoArgsConstructor
    @Data
    public static class DataBean {

        /**
         * uid
         */
        private Integer uid;
        /**
         * encryptUid
         */
        private String encryptUid;
        /**
         * lastTime
         */
        private String lastTime;
        /**
         * encryptExpectId
         */
        private String encryptExpectId;
        /**
         * addTime
         */
        private String addTime;
        /**
         * year
         */
        private String year;
        /**
         * lowSalary
         */
        private Integer lowSalary;
        /**
         * highSalary
         */
        private Integer highSalary;
        /**
         * salaryDesc
         */
        private String salaryDesc;
        /**
         * showCandidate
         */
        private Integer showCandidate;
        /**
         * firstTalk
         */
        private Boolean firstTalk;
        /**
         * major
         */
        private String major;
        /**
         * school
         */
        private String school;
        /**
         * price
         */
        private String price;
        /**
         * bothTalked
         */
        private Boolean bothTalked;
        /**
         * block
         */
        private Boolean block;
        /**
         * applyStatusDes
         */
        private String applyStatusDes;
        /**
         * applyStatusDes2
         */
        private Object applyStatusDes2;
        /**
         * ageDesc
         */
        private String ageDesc;
        /**
         * toPosition
         */
        private String toPosition;
        /**
         * weixinVisible
         */
        private Integer weixinVisible;
        /**
         * sourceTitle
         */
        private String sourceTitle;
        /**
         * labels
         */
        private List<?> labels;
        /**
         * newGeek
         */
        private Integer newGeek;
        /**
         * jobId
         */
        private Integer jobId;
        /**
         * weixin
         */
        private Object weixin;
        /**
         * toPositionId
         */
        private String toPositionId;
        /**
         * phone
         */
        private Object phone;
        /**
         * sourceType
         */
        private Integer sourceType;
        /**
         * sourceExtend
         */
        private Object sourceExtend;
        /**
         * isTop
         */
        private Integer isTop;
        /**
         * edu
         */
        private String edu;
        /**
         * name
         */
        private String name;
        /**
         * largeAvatar
         */
        private String largeAvatar;
        /**
         * position
         */
        private String position;
        /**
         * positionName
         */
        private String positionName;
        /**
         * applyStatus
         */
        private Integer applyStatus;
        /**
         * lastCompany2
         */
        private Object lastCompany2;
        /**
         * note
         */
        private String note;
        /**
         * gender
         */
        private Integer gender;
        /**
         * city
         */
        private String city;
        /**
         * resumeVisible
         */
        private Integer resumeVisible;
        /**
         * mobileVisible
         */
        private Integer mobileVisible;
        /**
         * cooperate
         */
        private Integer cooperate;
        /**
         * expectId
         */
        private Integer expectId;
        /**
         * expectType
         */
        private Integer expectType;
        /**
         * outCount
         */
        private Integer outCount;
        /**
         * inCount
         */
        private Integer inCount;
        /**
         * belongGroup
         */
        private Object belongGroup;
        /**
         * lastPosition
         */
        private String lastPosition;
        /**
         * avatar
         */
        private String avatar;
        /**
         * lastCompany
         */
        private String lastCompany;
        /**
         * positionStatus
         */
        private String positionStatus;
        /**
         * initTime
         */
        private String initTime;
        /**
         * interview
         */
        private Integer interview;
        /**
         * lastPosition2
         */
        private Object lastPosition2;
        /**
         * relationType
         */
        private Integer relationType;
        /**
         * securityId
         */
        private String securityId;
        /**
         * filterReasonList
         */
        private Object filterReasonList;
        /**
         * everWorkPositionNameList
         */
        private Object everWorkPositionNameList;
        /**
         * workExpList
         */
        private List<WorkExpListBean> workExpList;
        /**
         * eduExpList
         */
        private List<EduExpListBean> eduExpList;
        /**
         * activeTimeDesc
         */
        private String activeTimeDesc;
        /**
         * completeType
         */
        private Integer completeType;
        /**
         * resumeVideoInfo
         */
        private Object resumeVideoInfo;
        /**
         * multiGeekVideoResume4BossVO
         */
        private Object multiGeekVideoResume4BossVO;
        /**
         * highLightGeekResumeWords
         */
        private Object highLightGeekResumeWords;
        /**
         * enablePmq
         */
        private Boolean enablePmq;
        /**
         * closeInterview
         */
        private Boolean closeInterview;
        /**
         * filtered
         */
        private Boolean filtered;
        /**
         * systemJob
         */
        private Boolean systemJob;
        /**
         * isSystemJob
         */
        private Boolean isSystemJob;
        /**
         * isCloseInterview
         */
        private Boolean isCloseInterview;
        /**
         * isFiltered
         */
        private Boolean isFiltered;

        /**
         * WorkExpListBean
         */
        @NoArgsConstructor
        @Data
        public static class WorkExpListBean {

            /**
             * timeDesc
             */
            private String timeDesc;
            /**
             * company
             */
            private String company;
            /**
             * positionName
             */
            private String positionName;
        }

        /**
         * EduExpListBean
         */
        @NoArgsConstructor
        @Data
        public static class EduExpListBean {

            /**
             * timeDesc
             */
            private String timeDesc;
            /**
             * school
             */
            private String school;
            /**
             * major
             */
            private String major;
            /**
             * degree
             */
            private String degree;
            /**
             * degreeCode
             */
            private Integer degreeCode;
        }
    }

}
