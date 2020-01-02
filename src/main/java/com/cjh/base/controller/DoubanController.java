package com.cjh.base.controller;

import com.cjh.base.po.NowPlayingPO;
import com.cjh.base.api.DoubanApi;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class DoubanController {

    private DoubanApi doubanApi;

    @GetMapping("/getNowPlaying")
    public List<NowPlayingPO> getNowPlaying() {
        return doubanApi.getNowPlaying();
    }

    @GetMapping("/getMovieDesc")
    public String getMovieDesc(String id) {
        return doubanApi.getMovieDesc(id);
    }

    @GetMapping("/getComments")
    public List<String> getComments(@RequestParam("id") String id,
        @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return doubanApi.getComments(id, pageNum, pageSize);
    }

}
