package com.cjh.common.controller.book;

import com.baomidou.mybatisplus.extension.api.R;
import com.cjh.common.dao.BookContentDto;
import com.cjh.common.dao.BookInfoDto;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/book/shyosun")
@RestController
public class BookShyosunController {

    private static final Pattern PATTERN_URL = Pattern.compile("shyosun\\.com/book/(.*?)/");

    @GetMapping("")
    public R<BookContentDto> content(String url, Integer start, Integer end) {
        start = start == null ? 1 : start;
        end = end == null ? 1 : end;
        end = end < start ? start : end;
        if (url == null) {
            return R.failed("url 不能为空");
        }
        String bookId = null;

        Matcher matcher = PATTERN_URL.matcher(url);
        if (matcher.find()) {
            bookId = matcher.group(1);
        }

        if (bookId == null) {
            return R.failed("url 格式不对，例：https://www.shyosun.com/176162/...");
        }
        System.out.println(bookId);

        BookInfoDto bookInfo = getBookInfo(bookId);

        List<String> list = Lists.newArrayList();
        int i = start;
        while (true) {
            if (i > end || !getDetail(bookInfo, i, list)) {
                break;
            }
            i++;
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        BookContentDto data = new BookContentDto();
        data.setBookId(bookId);
        data.setLink(url);
        data.setTitle(bookInfo.getBookName());
        data.setBody(String.join("\n", list));
        return R.ok(data);
    }

    private static BookInfoDto getBookInfo(String bookId) {
        String url = "https://www.shyosun.com/book/" + bookId + "/";
        System.out.println(url);
        try {
            Document document = Jsoup.connect(url).get();

            BookInfoDto bookInfoDto = new BookInfoDto();
            bookInfoDto.setBookId(bookId);
            bookInfoDto.setBookName(document.getElementsByTag("h1").get(0).text());
            bookInfoDto.setDescription(document.getElementsByClass("bookinfo_intro").get(0).text());
            bookInfoDto.setThumb(document
                .getElementsByClass("pic").get(0)
                .getElementsByTag("img").get(0)
                .attr("src"));

            Element list = document.getElementsByClass("book_list").get(0);
            Elements bookItems = list.getElementsByTag("a");

            List<BookContentDto> chapterList = Lists.newArrayList();
            for (Element a : bookItems) {
                String text = a.text();
                BookContentDto bookContentDto = new BookContentDto();
                bookContentDto.setTitle(text);
                bookContentDto.setLink(url + a.attr("href"));
                chapterList.add(bookContentDto);
            }
            bookInfoDto.setChapterList(chapterList);

            return bookInfoDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析异常：" + e.getMessage());
        }
    }

    private static boolean getDetail(BookInfoDto bookInfoDto, Integer index, List<String> list) {
        String indexStr = "<<< 第 " + index + " 章 >>>";
        list.add(indexStr);

        BookContentDto bookContentDto = bookInfoDto.getChapterList().get(index - 1);
        if (bookContentDto == null) {
            list.add("没有章节内容/需要付费阅读");
            return false;
        }

        try {
            getContent(bookContentDto.getLink(), list);
        } catch (Exception e) {
            e.printStackTrace();
            list.add("解析异常：" + e.getMessage());
            return false;
        }
        return true;
    }

    private static void getContent(String url, List<String> list) throws IOException {
        Document document = Jsoup.connect(url).get();
        Element booktxt = document.getElementById("htmlContent");
        String html = booktxt.html().replace("&nbsp;","").replace("\n<br>\n<br>","\n");
        String[] split = html.split("\n");
        list.addAll(Arrays.asList(split));

//        //next
//        Element nextUrl = document.getElementsByClass("pager_next").get(0);
//        if ("下一页".equals(nextUrl.text())) {
//            getContent("https://www.5200.info" + nextUrl.attr("href"), list);
//        }
    }

    public static void main(String[] args) {
        BookShyosunController controller = new BookShyosunController();
        R<BookContentDto> content = controller.content("https://www.shyosun.com/book/176162/30859501.html", 1, 5);
        System.out.println(content);
    }
}
