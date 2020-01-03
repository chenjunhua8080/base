package com.cjh.common.controller;

import com.cjh.common.api.AvatarApi;
import com.cjh.common.po.AvatarPO;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AvatarController {

    private AvatarApi avatarApi;

    @GetMapping("/getAvatarByNew")
    public List<AvatarPO> getAvatarByNew(@RequestParam(required = false) Integer pageNum) {
        return avatarApi.getAvatarByNew(pageNum);
    }

    @GetMapping("/getAvatar")
    public AvatarPO getAvatar() {
        return avatarApi.getAvatar();
    }

}
