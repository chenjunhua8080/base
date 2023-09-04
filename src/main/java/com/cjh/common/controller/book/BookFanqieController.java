package com.cjh.common.controller.book;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.cjh.common.dao.BookContentDto;
import com.cjh.common.dao.BookInfoDto;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/book/fanqie")
@RestController
public class BookFanqieController {

    private static final Pattern PATTERN_URL = Pattern.compile("book_id=(.*?)&");
    private static final Pattern PATTERN_BOOK_INFO_JSON = Pattern.compile("window.__INITIAL_STATE__=(.*?);");
    private static final Pattern PATTERN_BOOK_CONTENT = Pattern.compile("<p>(.*?)</p>");

    @GetMapping("")
    public R<BookContentDto> content(String url, Integer start, Integer end, String book_id) {
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
            bookId = book_id;
        }

        if (bookId == null) {
            return R.failed(
                "url 格式不对，例：https://changdunovel.com/wap/share-v2.html?aid=1967&book_id=7134523177480227854&share_type=0&...");
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
        String url = "https://fanqienovel.com/page/" + bookId;
        System.out.println(url);
        try {
            String body = HttpRequest.get(url).execute().body();
            Matcher matcher = PATTERN_BOOK_INFO_JSON.matcher(body);
            if (!matcher.find()) {
                throw new RuntimeException("没有匹配到JSON内容");
            }
            String json = matcher.group(1);
            System.out.println(json);
            JSONObject jsonObject = JSON.parseObject(json);
            JSONObject page = jsonObject.getJSONObject("page");

            BookInfoDto bookInfoDto = new BookInfoDto();
            bookInfoDto.setBookId(bookId);
            bookInfoDto.setBookName(page.getString("bookName"));
            bookInfoDto.setDescription(page.getString("abstract"));
            bookInfoDto.setThumb(page.getString("thumbUri"));

            List<BookContentDto> chapterList = Lists.newArrayList();
            JSONArray jsonArray = page.getJSONArray("chapterListWithVolume").getJSONArray(0);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject item = (JSONObject) jsonArray.get(i);
                if (i >= 100 || item.getBooleanValue("isChapterLock")) {
                    break;
                }
                BookContentDto bookContentDto = new BookContentDto();
                bookContentDto.setTitle(item.getString("title"));
                bookContentDto.setLink("https://fanqienovel.com/api/reader/full?itemId=" + item.getString("itemId"));
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
            String body = HttpRequest.get(bookContentDto.getLink()).execute().body();
            JSONObject jsonObject = JSON.parseObject(body);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject chapterData = data.getJSONObject("chapterData");
            String content = chapterData.getString("content");
            Matcher matcher = PATTERN_BOOK_CONTENT.matcher(content);
            while (matcher.find()) {
                String p = matcher.group(1);
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            list.add("解析异常：" + e.getMessage());
            return false;
        }
        return true;
    }

}
