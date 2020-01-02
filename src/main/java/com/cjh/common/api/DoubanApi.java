package com.cjh.common.api;

import com.alibaba.fastjson.JSONObject;
import com.cjh.common.po.DoubanPO;
import com.cjh.common.po.NowPlayingPO;
import com.cjh.common.util.HttpUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DoubanApi {

    /**
     * @name 登录
     * @params ck=&name=13413527259&password=dbcjh121233&remember=true&ticket=
     * @result
     */
    private final String url_login = "https://accounts.douban.com/j/mobile/login/basic";

    /**
     * @name 热映
     * @method GET
     * @result html
     */
    private final String url_nowplaying = "https://movie.douban.com/cinema/nowplaying/guangzhou";

    /**
     * @name 电影详情
     * @method GET
     * @result html
     */
    private final String url_playing_poster = "https://movie.douban.com/subject/ID/?from=playing_poster";

    /**
     * @name 影评
     * @method GET
     * @result html
     */
    private final String url_get_comments = "https://movie.douban.com/subject/ID/comments?start=pageNum&limit=pageSize";

    /**
     * 登录
     */
    public boolean login() {
        Map<String, Object> params = new HashMap<>();
        params.put("ck", "");
        params.put("name", "13413527259");
        params.put("password", "dbcjh2018");
        params.put("remember", "false");
        params.put("ticket", "");

        Map<String, Object> headers = new HashMap<>();
        ResponseEntity responseEntity = HttpUtil.doPost(url_login, headers, params);
        if (responseEntity.getStatusCodeValue() != 200) {
            throw new RuntimeException("登录失败:" + responseEntity.getBody());
        }
        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        String body = responseEntity.getBody().toString();
        JSONObject jsonObject = JSONObject.parseObject(body).getJSONObject("payload")
            .getJSONObject("account_info");
        DoubanPO doubanPO = jsonObject.toJavaObject(DoubanPO.class);
        String key1 = "dbcl2=";
        String key2 = "bid=";
        String value;
        for (String cookie : cookies) {
            if (cookie.contains(key1)) {
                value = cookie.substring(cookie.indexOf(key1) + key1.length(), cookie.indexOf(";"));
                value = value.replaceAll("\"", "");
                doubanPO.setDbcl2(value);
            } else if (cookie.contains(key2)) {
                value = cookie.substring(cookie.indexOf(key2) + key2.length(), cookie.indexOf(";"));
                doubanPO.setBid(value);
            }
        }
        return true;
    }

    /**
     * 热映
     */
    public List<NowPlayingPO> getNowPlaying() {
        List<NowPlayingPO> list = new ArrayList<>();
        String resp = HttpUtil.doGet(url_nowplaying);
        Document document = Jsoup.parse(resp);
        Element domById = document.getElementById("nowplaying");
        Elements domByClass = domById.getElementsByClass("list-item");
        for (Element element : domByClass) {
            String id = element.attr("id");
            String title = element.attr("data-title");
            String score = element.attr("data-score");
            String actors = element.attr("data-actors");
            Elements img = element.getElementsByTag("img");
            Elements url = element.getElementsByTag("a");
            String imgSrc = img.get(0).attr("src");
            String urlHref = url.get(0).attr("href");
            NowPlayingPO nowPlayingPO = new NowPlayingPO();
            nowPlayingPO.setId(id);
            nowPlayingPO.setName(title);
            nowPlayingPO.setImg(imgSrc);
            nowPlayingPO.setActors(actors);
            nowPlayingPO.setScore(Double.parseDouble(score));
            nowPlayingPO.setUrl(urlHref);
            list.add(nowPlayingPO);
        }
        return list;
    }

    /**
     * 获取简述
     */
    public String getMovieDesc(String id) {
        String resp = HttpUtil.doGet(url_playing_poster.replace("ID", id));
        Document document = Jsoup.parse(resp);
        Elements desc = document.getElementsByAttributeValue("property", "v:summary");
        Element element = desc.get(0);
        return element.html();
    }

    /**
     * 获取影评
     */
    public List<String> getComments(String id, int pageNum, int pageSize) {
        List<String> comments = new ArrayList<>();
        String url = url_get_comments.replace("ID", id)
            .replace("pageNum", String.valueOf((pageNum - 1) * pageSize))
            .replace("pageSize", String.valueOf(pageSize));
        String resp = HttpUtil.doGet(url);
        Document document = Jsoup.parse(resp);
        Elements shortComments = document.getElementsByClass("short");
        for (Element comment : shortComments) {
            comments.add(comment.html());
        }
        return comments;
    }


}
