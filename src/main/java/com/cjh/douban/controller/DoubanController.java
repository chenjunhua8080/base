package com.cjh.douban.controller;

import com.cjh.douban.po.NowPlayingPO;
import com.cjh.douban.service.DoubanService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class DoubanController {

    private DoubanService doubanService;

    @GetMapping("/getNowPlaying")
    public List<NowPlayingPO> getNowPlaying() {
        return doubanService.getNowPlaying();
    }

}
