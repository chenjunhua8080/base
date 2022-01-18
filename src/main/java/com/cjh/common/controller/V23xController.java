package com.cjh.common.controller;

import com.baomidou.mybatisplus.extension.api.R;
import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v23x")
public class V23xController {

    @GetMapping("/test/1")
    public R test1() {
        return R.ok(new Date());
    }

    @GetMapping("/test/2")
    public R test2(Date date) {
        return R.ok(date);
    }

}
