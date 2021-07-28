package com.cjh.common.boss.resp;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NewGeek {


    /**
     * needGuide
     */
    private Boolean needGuide;
    /**
     * startIndex
     */
    private Long startIndex;
    /**
     * hasMore
     */
    private Boolean hasMore;
    /**
     * page
     */
    private Long page;
    /**
     * tag
     */
    private Long tag;
    /**
     * itemCardIdx
     */
    private Long itemCardIdx;
    /**
     * geekList
     */
    private List<NewGeekItemBean> geekList;
    /**
     * tagDesc
     */
    private String tagDesc;

    /**
     * GeekListBean
     */
    @NoArgsConstructor
    @Data
    public static class NewGeekItemBean {

        /**
         * geekCard
         */
        private NewGeekCardBean geekCard;
        /**
         * activeTimeDesc
         */
        private String activeTimeDesc;
        /**
         * talkTimeDesc
         */
        private String talkTimeDesc;
        /**
         * positionName
         */
        private String positionName;
        /**
         * cooperate
         */
        private Long cooperate;
        /**
         * isFriend
         */
        private Long isFriend;
        /**
         * itemId
         */
        private Long itemId;
        /**
         * suid
         */
        private String suid;
        /**
         * geekCallStatus
         */
        private Long geekCallStatus;
        /**
         * blur
         */
        private Long blur;
        /**
         * haveChatted
         */
        private Long haveChatted;
        /**
         * tag
         */
        private Long tag;
        /**
         * mateShareId
         */
        private Long mateShareId;
        /**
         * mateName
         */
        private Object mateName;
        /**
         * shareMessage
         */
        private Long shareMessage;
        /**
         * shareNote
         */
        private Object shareNote;
        /**
         * encryptShareId
         */
        private String encryptShareId;
        /**
         * friendGeek
         */
        private Boolean friendGeek;
        /**
         * usingGeekCall
         */
        private Boolean usingGeekCall;
        /**
         * blurGeek
         */
        private Boolean blurGeek;
        /**
         * encryptGeekId
         */
        private String encryptGeekId;

        /**
         * GeekCardBean
         */
        @NoArgsConstructor
        @Data
        public static class NewGeekCardBean {

            /**
             * securityId
             */
            private String securityId;
            /**
             * geekId
             */
            private Long geekId;
            /**
             * geekName
             */
            private String geekName;
            /**
             * geekAvatar
             */
            private String geekAvatar;
            /**
             * geekGender
             */
            private Long geekGender;
            /**
             * geekWorkYear
             */
            private String geekWorkYear;
            /**
             * geekDegree
             */
            private String geekDegree;
            /**
             * geekDesc
             */
            private GeekDescBean geekDesc;
            /**
             * expectId
             */
            private String expectId;
            /**
             * expectType
             */
            private Long expectType;
            /**
             * salary
             */
            private String salary;
            /**
             * middleContent
             */
            private MiddleContentBean middleContent;
            /**
             * jobId
             */
            private String jobId;
            /**
             * encryptJobId
             */
            private String encryptJobId;
            private String talkTimeDesc;
            /**
             * lid
             */
            private String lid;
            /**
             * actionDateDesc
             */
            private String actionDateDesc;
            /**
             * actionDate
             */
            private Long actionDate;
            /**
             * geekWorks
             */
            private List<GeekWorksBean> geekWorks;
            /**
             * geekEdu
             */
            private GeekEduBean geekEdu;
            /**
             * geekEdus
             */
            private List<GeekEdusBean> geekEdus;
            /**
             * expectLocation
             */
            private Long expectLocation;
            /**
             * expectPosition
             */
            private Long expectPosition;
            /**
             * applyStatus
             */
            private Long applyStatus;
            /**
             * activeSec
             */
            private Long activeSec;
            /**
             * birthday
             */
            private String birthday;
            /**
             * expectLocationName
             */
            private String expectLocationName;
            /**
             * expectPositionName
             */
            private String expectPositionName;
            /**
             * applyStatusDesc
             */
            private String applyStatusDesc;
            /**
             * freshGraduate
             */
            private Long freshGraduate;
            /**
             * ageDesc
             */
            private String ageDesc;
            /**
             * geekSource
             */
            private Long geekSource;
            /**
             * encryptGeekId
             */
            private String encryptGeekId;
            /**
             * anonymous
             */
            private Boolean anonymous;
            /**
             * contactStatus
             */
            private Object contactStatus;
            /**
             * expectInfos
             */
            private List<?> expectInfos;
            /**
             * experiences
             */
            private List<?> experiences;
            /**
             * edus
             */
            private List<?> edus;
            /**
             * feedbackTitle
             */
            private String feedbackTitle;
            /**
             * feedback
             */
            private List<FeedbackBean> feedback;
            /**
             * interactDesc
             */
            private InteractDescBean interactDesc;
            /**
             * clicked
             */
            private Boolean clicked;
            /**
             * forHomePage
             */
            private Boolean forHomePage;
            /**
             * homePageAction
             */
            private Long homePageAction;
            /**
             * canUseDirectCall
             */
            private Boolean canUseDirectCall;
            /**
             * exposeEnhanced
             */
            private Object exposeEnhanced;
            /**
             * viewed
             */
            private Boolean viewed;
            /**
             * contacting
             */
            private Boolean contacting;

            /**
             * GeekDescBean
             */
            @NoArgsConstructor
            @Data
            public static class GeekDescBean {

                /**
                 * content
                 */
                private String content;
                /**
                 * indexList
                 */
                private List<?> indexList;
            }

            /**
             * MiddleContentBean
             */
            @NoArgsConstructor
            @Data
            public static class MiddleContentBean {

                /**
                 * content
                 */
                private String content;
                /**
                 * indexList
                 */
                private List<?> indexList;
            }

            /**
             * GeekEduBean
             */
            @NoArgsConstructor
            @Data
            public static class GeekEduBean {

                /**
                 * id
                 */
                private Long id;
                /**
                 * userId
                 */
                private Long userId;
                /**
                 * school
                 */
                private String school;
                /**
                 * schoolId
                 */
                private Long schoolId;
                /**
                 * major
                 */
                private String major;
                /**
                 * degree
                 */
                private Long degree;
                /**
                 * degreeName
                 */
                private String degreeName;
                /**
                 * eduType
                 */
                private Long eduType;
                /**
                 * startDate
                 */
                private String startDate;
                /**
                 * endDate
                 */
                private String endDate;
                /**
                 * eduDescription
                 */
                private Object eduDescription;
                /**
                 * addTime
                 */
                private Object addTime;
                /**
                 * updateTime
                 */
                private Object updateTime;
                /**
                 * timeSlot
                 */
                private Object timeSlot;
                /**
                 * startYearStr
                 */
                private String startYearStr;
                /**
                 * endYearStr
                 */
                private String endYearStr;
            }

            /**
             * InteractDescBean
             */
            @NoArgsConstructor
            @Data
            public static class InteractDescBean {

                /**
                 * content
                 */
                private String content;
                /**
                 * indexList
                 */
                private List<IndexListBean> indexList;

                /**
                 * IndexListBean
                 */
                @NoArgsConstructor
                @Data
                public static class IndexListBean {

                    /**
                     * start
                     */
                    private Long start;
                    /**
                     * end
                     */
                    private Long end;
                }
            }

            /**
             * GeekWorksBean
             */
            @NoArgsConstructor
            @Data
            public static class GeekWorksBean {

                /**
                 * id
                 */
                private Long id;
                /**
                 * geekId
                 */
                private Long geekId;
                /**
                 * company
                 */
                private String company;
                /**
                 * industryCode
                 */
                private Long industryCode;
                /**
                 * industry
                 */
                private Object industry;
                /**
                 * industryCategory
                 */
                private Object industryCategory;
                /**
                 * position
                 */
                private Long position;
                /**
                 * positionCategory
                 */
                private Object positionCategory;
                /**
                 * blueCollarPosition
                 */
                private Boolean blueCollarPosition;
                /**
                 * positionName
                 */
                private String positionName;
                /**
                 * positionLv2
                 */
                private Long positionLv2;
                /**
                 * isPublic
                 */
                private Long isPublic;
                /**
                 * department
                 */
                private Object department;
                /**
                 * responsibility
                 */
                private Object responsibility;
                /**
                 * startDate
                 */
                private String startDate;
                /**
                 * endDate
                 */
                private String endDate;
                /**
                 * customPositionId
                 */
                private Long customPositionId;
                /**
                 * customIndustryId
                 */
                private Long customIndustryId;
                /**
                 * workPerformance
                 */
                private Object workPerformance;
                /**
                 * workEmphasisList
                 */
                private Object workEmphasisList;
                /**
                 * certStatus
                 */
                private Long certStatus;
                /**
                 * workType
                 */
                private Long workType;
                /**
                 * addTime
                 */
                private Object addTime;
                /**
                 * updateTime
                 */
                private Object updateTime;
                /**
                 * companyHighlight
                 */
                private Object companyHighlight;
                /**
                 * positionNameHighlight
                 */
                private Object positionNameHighlight;
                /**
                 * workTime
                 */
                private Object workTime;
                /**
                 * workMonths
                 */
                private Long workMonths;
                /**
                 * current
                 */
                private Boolean current;
                /**
                 * stillWork
                 */
                private Boolean stillWork;
                /**
                 * startYearMonStr
                 */
                private String startYearMonStr;
                /**
                 * endYearMonStr
                 */
                private String endYearMonStr;
                /**
                 * workTypeIntern
                 */
                private Boolean workTypeIntern;
            }

            /**
             * GeekEdusBean
             */
            @NoArgsConstructor
            @Data
            public static class GeekEdusBean {

                /**
                 * id
                 */
                private Long id;
                /**
                 * userId
                 */
                private Long userId;
                /**
                 * school
                 */
                private String school;
                /**
                 * schoolId
                 */
                private Long schoolId;
                /**
                 * major
                 */
                private String major;
                /**
                 * degree
                 */
                private Long degree;
                /**
                 * degreeName
                 */
                private String degreeName;
                /**
                 * eduType
                 */
                private Long eduType;
                /**
                 * startDate
                 */
                private String startDate;
                /**
                 * endDate
                 */
                private String endDate;
                /**
                 * eduDescription
                 */
                private Object eduDescription;
                /**
                 * addTime
                 */
                private Object addTime;
                /**
                 * updateTime
                 */
                private Object updateTime;
                /**
                 * timeSlot
                 */
                private Object timeSlot;
                /**
                 * startYearStr
                 */
                private String startYearStr;
                /**
                 * endYearStr
                 */
                private String endYearStr;
            }

            /**
             * FeedbackBean
             */
            @NoArgsConstructor
            @Data
            public static class FeedbackBean {

                /**
                 * code
                 */
                private Long code;
                /**
                 * memo
                 */
                private String memo;
                /**
                 * showType
                 */
                private Long showType;
                /**
                 * feedbackL2List
                 */
                private Object feedbackL2List;
                /**
                 * titleL2
                 */
                private Object titleL2;
            }
        }
    }

}
