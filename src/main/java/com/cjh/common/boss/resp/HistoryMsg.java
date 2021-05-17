package com.cjh.common.boss.resp;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HistoryMsg  {

    /**
     * messages
     */
    private List<MessagesBean> messages;
    /**
     * type
     */
    private Integer type;

    /**
     * MessagesBean
     */
    @NoArgsConstructor
    @Data
    public static class MessagesBean {

        /**
         * uncount
         */
        private Integer uncount;
        /**
         * flag
         */
        private Integer flag;
        /**
         * mid
         */
        private Long mid;
        /**
         * received
         */
        private Boolean received;
        /**
         * securityId
         */
        private String securityId;
        /**
         * cmid
         */
        private Long cmid;
        /**
         * type
         */
        private Integer type;
        /**
         * body
         */
        private BodyBean body;
        /**
         * offline
         */
        private Boolean offline;
        /**
         * pushSound
         */
        private Integer pushSound;
        /**
         * from
         */
        private FromBean from;
        /**
         * to
         */
        private ToBean to;
        /**
         * time
         */
        private Long time;
        /**
         * taskId
         */
        private Long taskId;
        /**
         * status
         */
        private Integer status;
        /**
         * bizType
         */
        private Integer bizType;
        /**
         * pushText
         */
        private String pushText;
        /**
         * bizId
         */
        private String bizId;

        /**
         * BodyBean
         */
        @NoArgsConstructor
        @Data
        public static class BodyBean {

            /**
             * action
             */
            private ActionBean action;
            /**
             * type
             */
            private Integer type;
            /**
             * templateId
             */
            private Long templateId;
            /**
             * headTitle
             */
            private String headTitle;
            /**
             * extend
             */
            private String extend;
            /**
             * text
             */
            private String text;
            /**
             * dialog
             */
            private DialogBean dialog;
            /**
             * hyperLink
             */
            private HyperLinkBean hyperLink;
            /**
             * resume
             */
            private ResumeBean resume;

            /**
             * ActionBean
             */
            @NoArgsConstructor
            @Data
            public static class ActionBean {

                /**
                 * extend
                 */
                private String extend;
                /**
                 * aid
                 */
                private Long aid;
            }

            /**
             * DialogBean
             */
            @NoArgsConstructor
            @Data
            public static class DialogBean {

                /**
                 * backgroundUrl
                 */
                private String backgroundUrl;
                /**
                 * clickMore
                 */
                private Boolean clickMore;
                /**
                 * buttons
                 */
                private List<ButtonsBean> buttons;
                /**
                 * operated
                 */
                private Boolean operated;
                /**
                 * statisticParameters
                 */
                private String statisticParameters;
                /**
                 * type
                 */
                private Integer type;
                /**
                 * title
                 */
                private String title;
                /**
                 * timeout
                 */
                private Integer timeout;
                /**
                 * url
                 */
                private String url;
                /**
                 * content
                 */
                private String content;
                /**
                 * selectedIndex
                 */
                private Integer selectedIndex;
                /**
                 * extend
                 */
                private String extend;
                /**
                 * text
                 */
                private String text;

                /**
                 * ButtonsBean
                 */
                @NoArgsConstructor
                @Data
                public static class ButtonsBean {

                    /**
                     * text
                     */
                    private String text;
                    /**
                     * templateId
                     */
                    private Integer templateId;
                    /**
                     * url
                     */
                    private String url;
                }
            }

            /**
             * HyperLinkBean
             */
            @NoArgsConstructor
            @Data
            public static class HyperLinkBean {

                /**
                 * extraJson
                 */
                private String extraJson;
                /**
                 * text
                 */
                private String text;
                /**
                 * hyperLinkType
                 */
                private Integer hyperLinkType;
                /**
                 * url
                 */
                private String url;
            }

            /**
             * ResumeBean
             */
            @NoArgsConstructor
            @Data
            public static class ResumeBean {

                /**
                 * jobSalary
                 */
                private String jobSalary;
                /**
                 * brandName
                 */
                private String brandName;
                /**
                 * education
                 */
                private String education;
                /**
                 * gender
                 */
                private Integer gender;
                /**
                 * city
                 */
                private String city;
                /**
                 * lid
                 */
                private String lid;
                /**
                 * description
                 */
                private String description;
                /**
                 * securityId
                 */
                private String securityId;
                /**
                 * expectId
                 */
                private Integer expectId;
                /**
                 * salary
                 */
                private String salary;
                /**
                 * experiences
                 */
                private List<ExperiencesBean> experiences;
                /**
                 * bottomText
                 */
                private String bottomText;
                /**
                 * labels
                 */
                private List<String> labels;
                /**
                 * jobId
                 */
                private Integer jobId;
                /**
                 * content3
                 */
                private String content3;
                /**
                 * content2
                 */
                private String content2;
                /**
                 * content1
                 */
                private String content1;
                /**
                 * position
                 */
                private String position;
                /**
                 * workYear
                 */
                private String workYear;
                /**
                 * positionCategory
                 */
                private String positionCategory;
                /**
                 * user
                 */
                private UserBean user;
                /**
                 * applyStatus
                 */
                private String applyStatus;
                /**
                 * age
                 */
                private String age;

                /**
                 * UserBean
                 */
                @NoArgsConstructor
                @Data
                public static class UserBean {

                    /**
                     * uid
                     */
                    private Integer uid;
                    /**
                     * headImg
                     */
                    private Integer headImg;
                    /**
                     * name
                     */
                    private String name;
                    /**
                     * company
                     */
                    private String company;
                    /**
                     * avatar
                     */
                    private String avatar;
                    /**
                     * source
                     */
                    private Integer source;
                    /**
                     * certification
                     */
                    private Integer certification;
                }

                /**
                 * ExperiencesBean
                 */
                @NoArgsConstructor
                @Data
                public static class ExperiencesBean {

                    /**
                     * occupation
                     */
                    private String occupation;
                    /**
                     * endDate
                     */
                    private String endDate;
                    /**
                     * organization
                     */
                    private String organization;
                    /**
                     * type
                     */
                    private Integer type;
                    /**
                     * startDate
                     */
                    private String startDate;
                }
            }
        }

        /**
         * FromBean
         */
        @NoArgsConstructor
        @Data
        public static class FromBean {

            /**
             * uid
             */
            private Integer uid;
            /**
             * headImg
             */
            private Integer headImg;
            /**
             * name
             */
            private String name;
            /**
             * company
             */
            private String company;
            /**
             * avatar
             */
            private String avatar;
            /**
             * source
             */
            private Integer source;
            /**
             * certification
             */
            private Integer certification;
        }

        /**
         * ToBean
         */
        @NoArgsConstructor
        @Data
        public static class ToBean {

            /**
             * uid
             */
            private Integer uid;
            /**
             * headImg
             */
            private Integer headImg;
            /**
             * name
             */
            private String name;
            /**
             * company
             */
            private String company;
            /**
             * avatar
             */
            private String avatar;
            /**
             * source
             */
            private Integer source;
            /**
             * certification
             */
            private Integer certification;
        }
    }
}
