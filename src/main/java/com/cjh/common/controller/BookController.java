package com.cjh.common.controller;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/book")
@RestController
public class BookController {

    @GetMapping("/qq")
    public String find(String url, Integer start, Integer end) {
        start = start == null ? 1 : start;
        end = end == null ? 1 : end;
        if (url == null) {
            return "url 不能为空";
        }
        if (!url.startsWith("https://book.qq.com/book-detail/")) {
            return "url 格式不对，例：https://book.qq.com/book-detail/xxx";
        }
        String bookId = url.substring(url.lastIndexOf("/") + 1);
        List<String> list = Lists.newArrayList();
        int i = start;
        while (true) {
            if (i > end || !getDetail(bookId, i, list)) {
                return String.join("\n", list);
            }
            i++;
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
