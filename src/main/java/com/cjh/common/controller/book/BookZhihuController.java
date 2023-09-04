package com.cjh.common.controller.book;

import com.baomidou.mybatisplus.extension.api.R;
import com.cjh.common.dao.BookContentDto;
import com.cjh.common.dao.BookInfoDto;
import com.google.common.collect.Lists;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/book/zhihu")
@RestController
public class BookZhihuController {

    private static final Pattern PATTERN_URL = Pattern.compile("mst=(\\w+)");

    @GetMapping("")
    public R<BookContentDto> content(String url, String mst) {
        if (url == null) {
            return R.failed("url 不能为空");
        }
        String bookId;

        //https://soia.zhihu.com/tab/home?is_delivery=true&source=e9f03bea58b4524092f6cb42207b6a5f&package=zhihushare0812&channel_id=67154024128158&appkey=2400&ustkn=1&is_share_data=true&mst=WJ9jxYKaXmVlqCM8PXlDKoLJpNIz0WmZ6
        Matcher matcher = PATTERN_URL.matcher(url);
        if (matcher.find()) {
            bookId = matcher.group(1);
        } else {
            bookId = mst;
        }

        if (bookId == null) {
            return R.failed(
                "url 格式不对，例：https://soia.zhihu.com/tab/home?is_delivery=true&source=e9f03bea58b4524092f6cb42207b6a5f&package=zhihushare0812&channel_id=67154024128158&appkey=2400&ustkn=1&is_share_data=true&mst=WJ9jxYKaXmVlqCM8PXlDKoLJpNIz0WmZ6...");
        }
        System.out.println(bookId);

        BookInfoDto bookInfo = getBookInfo(bookId);

        BookContentDto data = new BookContentDto();
        data.setBookId(bookId);
        data.setTitle(bookInfo.getBookName());
        data.setLink(bookInfo.getChapterList().get(0).getLink());
        data.setBody(bookInfo.getChapterList().get(0).getBody());

        return R.ok(data);
    }

    private static BookInfoDto getBookInfo(String bookId) {
        String url = "https://story.zhihu.com/blogger/next-manuscript/paid_column/" + bookId;
        System.out.println(url);
        try {
            Document document = Jsoup.connect(url).get();
            Element app = document.getElementById("app");
            Elements pList = app.getElementsByTag("p");

            BookInfoDto bookInfoDto = new BookInfoDto();
            bookInfoDto.setBookId(bookId);
            bookInfoDto.setBookName("知乎盐选内容");
            bookInfoDto.setDescription(null);
            bookInfoDto.setThumb(null);

            BookContentDto bookContentDto = new BookContentDto();
            bookContentDto.setTitle("知乎盐选内容");
            bookContentDto.setLink(url);

            StringBuilder stringBuilder = new StringBuilder();
            for (Element p : pList) {
                stringBuilder.append(p.text()).append("\n");
            }
            bookContentDto.setBody(stringBuilder.toString());

            bookInfoDto.setChapterList(Lists.newArrayList(bookContentDto));

            return bookInfoDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析异常：" + e.getMessage());
        }
    }

}
