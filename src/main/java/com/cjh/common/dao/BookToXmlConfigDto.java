package com.cjh.common.dao;

import java.util.List;
import lombok.Data;

@Data
public class BookToXmlConfigDto {

    private List<String> voiceList;
    private List<String> styleList;

}
