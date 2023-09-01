package com.cjh.common.dao;

import java.util.List;
import lombok.Data;

@Data
public class BookInfoDto {

    private String bookId;

    private String bookName;

    private String description;

    private String thumb;

    private List<BookContentDto> chapterList;

}
