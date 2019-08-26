package com.cjh.douban.controller;

import com.cjh.douban.po.NowPlayingPO;
import com.cjh.douban.service.DoubanService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class DoubanController {

    private DoubanService doubanService;

    @GetMapping("/getNowPlaying")
    public List<NowPlayingPO> getNowPlaying() {
        return doubanService.getNowPlaying();
    }

    @GetMapping("/getMovieDesc")
    public String getMovieDesc(String id) {
        return doubanService.getMovieDesc(id);
    }

    @GetMapping("/getComments")
    public List<String> getComments(@RequestParam("id") String id,
        @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return doubanService.getComments(id, pageNum, pageSize);
    }

}
