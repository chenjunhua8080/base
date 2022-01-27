package com.cjh.common.req.coffee;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FanclubPostParam {

    /**
     * unionid
     */
    private String unionid;
    /**
     * imgList
     */
    @JSONField(name = "img_list")
    private String imgList;
    /**
     * context
     */
    private String context;
    /**
     * topicId
     */
    @JSONField(name = "topic_id")
    private Integer topicId;
    /**
     * seriesId
     */
    @JSONField(name = "series_id")
    private Integer seriesId;
    /**
     * videoList
     */
    @JSONField(name = "video_list")
    private String videoList;
    /**
     * videoCover
     */
    @JSONField(name = "video_cover")
    private String videoCover;
}
