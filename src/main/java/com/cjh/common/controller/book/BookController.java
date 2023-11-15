package com.cjh.common.controller.book;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.cjh.common.dao.BookContentDto;
import com.cjh.common.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/book/content")
@RestController
public class BookController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private BookQQController bookQQController;
    @Autowired
    private BookFanqieController bookFanqieController;
    @Autowired
    private BookBXWXController bookBXWXController;
    @Autowired
    private BookNFXSController bookNFXSController;
    @Autowired
    private BookZhihuController bookZhihuController;

    @GetMapping("")
    public R<BookContentDto> content(String url, Integer start, Integer end, String book_id, String bid, String mst) {
        if (!StringUtils.hasLength(url)) {
            return R.failed("url is null");
        }

        // cache
        String key = String.join(":", url, String.valueOf(start), String.valueOf(end), book_id, bid);
        String json = redisService.get(key);
        if (json != null) {
            return JSON.parseObject(json, R.class);
        }

        R<BookContentDto> r;
        if (url.contains("https://changdunovel.com")) {
            r = bookFanqieController.content(url, start, end, book_id);
            return r;
        } else if (url.contains("/bxwx/")) {
            r = bookBXWXController.content(url, start, end);
        }  else if (url.contains("https://www.nfxs.com")) {
            r = bookNFXSController.content(url, start, end);
        } else if (url.contains("https://ih5.reader.qq.com") || url.contains("https://book.qq.com")) {
            r = bookQQController.content(url, start, end, bid);
        } else if (url.contains("zhihu")) {
            r = bookZhihuController.content(url, mst);
        } else {
            return R.failed("仅支持QQ/番茄/笔下文学/农夫小说/知乎");
        }

        // cache
        if (r.getCode() == ApiErrorCode.FAILED.getCode()) {
            redisService.set(key, JSON.toJSONString(r));
        }

        return r;
    }
}
