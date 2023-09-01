package com.cjh.common.controller.book;

import com.baomidou.mybatisplus.extension.api.R;
import com.cjh.common.dao.BookContentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/book/content")
@RestController
public class BookController {

    @Autowired
    private BookQQController bookQQController;
    @Autowired
    private BookFanqieController bookFanqieController;
    @Autowired
    private BookBXWXController bookBXWXController;

    @GetMapping("")
    public R<BookContentDto> content(String url, Integer start, Integer end, String book_id, String bid) {
        if (url.contains("https://changdunovel.com")) {
            return bookFanqieController.content(url, start, end, book_id);
        } else if (url.contains("http://www.xfuedu.org")) {
            return bookBXWXController.content(url, start, end);
        } else if (url.contains("https://ih5.reader.qq.com") || url.contains("https://book.qq.com")) {
            return bookQQController.content(url, start, end, bid);
        } else {
            return R.failed("仅支持QQ/番茄/笔下文学");
        }
    }
}
