package com.cjh.common.dao;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BookToXmlConfigV2Dto {

    private List<BookToXmlConfigV2Item> voiceList;
    private List<BookToXmlConfigV2Item> styleList;
    private List<BookToXmlConfigV2Item> roleList;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookToXmlConfigV2Item {

        private String name;
        private String desc;
    }
}
