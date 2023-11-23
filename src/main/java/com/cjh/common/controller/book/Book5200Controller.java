package com.cjh.common.controller.book;

import com.baomidou.mybatisplus.extension.api.R;
import com.cjh.common.dao.BookContentDto;
import com.cjh.common.dao.BookInfoDto;
import com.google.common.collect.Lists;
import java.io.IOException;
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

@RequestMapping("/book/5200")
@RestController
public class Book5200Controller {

    private static final Pattern PATTERN_URL = Pattern.compile("5200\\.info/(.*?)/");

    @GetMapping("")
    public R<BookContentDto> content(String url, Integer start, Integer end) {
        start = start == null ? 1 : start;
        end = end == null ? 1 : end;
        end = end < start ? start : end;
        if (url == null) {
            return R.failed("url 不能为空");
        }
        String bookId = null;

        //https://changdunovel.com/wap/share-v2.html?aid=1967&book_id=7134523177480227854&share_type=0&share_code=FzqMwm8UgJG9FDsjZiThLBPWZIgUZVvrm5lcIGV-E8g%3D&uid=bc8269134f85dbad02778b3719d8ea3d&share_id=9k7Qj6eOmXh0KtVG7W8hR3MJUsHiIehn4i5gTQsR50g%3D&source_channel=system&type=book&share_token=0ed054c3-ab87-4429-bfc9-f09bbd83c615
        Matcher matcher = PATTERN_URL.matcher(url);
        if (matcher.find()) {
            bookId = matcher.group(1);
        }

        if (bookId == null) {
            return R.failed("url 格式不对，例：https://www.5200.info/27555/...");
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
        String url = "https://www.5200.info/" + bookId + "/";
        System.out.println(url);
        try {
            Document document = Jsoup.connect(url).get();

            BookInfoDto bookInfoDto = new BookInfoDto();
            bookInfoDto.setBookId(bookId);
            bookInfoDto.setBookName(document.getElementsByTag("h1").get(0).text());
            bookInfoDto.setDescription(document.getElementById("intro").text());
            bookInfoDto.setThumb(document
                .getElementById("fmimg")
                .getElementsByTag("img").get(0)
                .attr("src"));

            Element list = document.getElementById("list");
            Elements bookItems = list.getElementsByTag("a");

            boolean add = false;
            List<BookContentDto> chapterList = Lists.newArrayList();
            for (Element a : bookItems) {
                String text = a.text();
                if (!add && text.matches(".*?共.*?章.*")) {
                    add = true;
                    continue;
                }
                if (add) {
                    BookContentDto bookContentDto = new BookContentDto();
                    bookContentDto.setTitle(text);
                    bookContentDto.setLink("https://www.5200.info" + a.attr("href"));
                    chapterList.add(bookContentDto);
                }
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
        Element booktxt = document.getElementById("booktxt");
        Elements pList = booktxt.getElementsByTag("p");
        for (int i = 1, pListSize = pList.size(); i < pListSize; i++) {
            Element p = pList.get(i);
            list.add(p.text());
        }

        //next
        Element nextUrl = document.getElementById("next_url");
        if ("下一页".equals(nextUrl.text())) {
            getContent("https://www.5200.info" + nextUrl.attr("href"), list);
        }
    }

    public static void main(String[] args) {
        Book5200Controller book5200Controller = new Book5200Controller();
        R<BookContentDto> content = book5200Controller.content("https://www.5200.info/160_160651/822252.html", 1, 1);
        System.out.println(content);
    }
}
