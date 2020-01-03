package com.cjh.common.api;

import com.cjh.common.po.AvatarPO;
import com.cjh.common.util.HttpUtil;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class AvatarApi {

    /**
     * 主机
     */
    private final String host = "https://m.woyaogexing.com";
    /**
     * 最新头像
     */
    private final String url_avatar_new = host + "/touxiang/new/";

    /**
     * 最新头像 - 列表
     */
    public List<AvatarPO> getAvatarByNew(Integer pageNum) {
        List<AvatarPO> list = new ArrayList<>();
        String url = this.url_avatar_new;
        if (pageNum != null && pageNum > 1) {
            url += String.format("index_%s.html", pageNum);
        }
        String resp = HttpUtil.doGet(url);
        Document document = Jsoup.parse(resp);
        Elements domByClass = document.getElementsByClass("m-pic-list");
        Element imgTag;
        Element aTag;
        String title;
        String href;
        String imgSrc;
        String id;
        AvatarPO avatarPO;
        for (Element element : domByClass) {
            try {
                aTag = element.getElementsByTag("a").get(0);
                imgTag = aTag.getElementsByTag("img").get(0);
                title = aTag.attr("title");
                href = host + aTag.attr("href");
                byte[] bytes = title.getBytes(StandardCharsets.ISO_8859_1);
                title = new String(bytes, StandardCharsets.UTF_8);
                id = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));
                imgSrc = "https:" + imgTag.attr("data-src");
                avatarPO = new AvatarPO();
                avatarPO.setId(id);
                avatarPO.setTitle(title);
                avatarPO.setHref(href);
                avatarPO.setImg(imgSrc);
                list.add(avatarPO);
            } catch (Exception e) {
                log.info("解析html错误");
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 最新头像 - 单个
     */
    public AvatarPO getAvatar() {
        List<AvatarPO> list = getAvatarByNew(null);
        if (list.isEmpty()) {
            return null;
        }
        return list.get((int) (Math.random() * list.size()));
    }

}