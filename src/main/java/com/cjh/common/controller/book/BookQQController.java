package com.cjh.common.controller.book;

import com.baomidou.mybatisplus.extension.api.R;
import com.cjh.common.dao.BookContentDto;
import com.google.common.collect.Lists;
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

@RequestMapping("/book/qq")
@RestController
public class BookQQController {

    private static final Pattern PATTERN_URL = Pattern.compile("book-detail/(\\d+)");

    @GetMapping("")
    public R<BookContentDto> content(String url, Integer start, Integer end, String bid) {
        start = start == null ? 1 : start;
        end = end == null ? 1 : end;
        end = end < start ? start : end;
        if (url == null) {
            return R.failed("url 不能为空");
        }
        String bookId;

        //https://changdunovel.com/wap/share-v2.html?aid=1967&book_id=7134523177480227854&share_type=0&share_code=FzqMwm8UgJG9FDsjZiThLBPWZIgUZVvrm5lcIGV-E8g%3D&uid=bc8269134f85dbad02778b3719d8ea3d&share_id=9k7Qj6eOmXh0KtVG7W8hR3MJUsHiIehn4i5gTQsR50g%3D&source_channel=system&type=book&share_token=0ed054c3-ab87-4429-bfc9-f09bbd83c615
        Matcher matcher = PATTERN_URL.matcher(url);
        if (matcher.find()) {
            bookId = matcher.group(1);
        } else {
            bookId = bid;
        }

        if (bookId == null) {
            return R.failed(
                "url 格式不对，例：https://book.qq.com/book-detail/40619508 或者 https://changdunovel.com/wap/share-v2.html...");
        }
        System.out.println(bookId);

        List<String> list = Lists.newArrayList();
        int i = start;
        while (true) {
            if (i > end || !getDetail(bookId, i, list)) {
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
        data.setTitle(getTitle(bookId));
        data.setBody(String.join("\n", list));
        return R.ok(data);
    }

    private static String getTitle(String bookId) {
        String url = "https://book.qq.com/book-detail/" + bookId;
        System.out.println(url);
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("book-title");
            Element e = elements.get(0);
            return e.text();
        } catch (Exception e) {
            return "解析异常：" + e.getMessage();
        }
    }

    private static boolean getDetail(String bookId, Integer index, List<String> list) {
        String url = "https://book.qq.com/book-read/" + bookId + "/" + index;
        System.out.println(url);
        String indexStr = "<<< 第 " + index + " 章 >>>";
        list.add(indexStr);
        try {
            Document document = Jsoup.connect(url).get();
            Elements btnLogin = document.getElementsByClass("btn-login");
            if (!btnLogin.isEmpty()) {
                list.add(indexStr + "，需要登录");
                return false;
            }
            Element article = document.getElementById("article");
            Elements pList = article.getElementsByTag("p");
            for (Element p : pList) {
                list.add(p.text());
            }
        } catch (Exception e) {
            e.printStackTrace();
            list.add(indexStr + "， 解析异常：" + e.getMessage());
            return false;
        }
        return true;
    }

}
