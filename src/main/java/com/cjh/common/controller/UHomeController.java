package com.cjh.common.controller;

import com.cjh.common.api.UHomeApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class UHomeController {

    private UHomeApi uHomeApi;
    private BindFarmDao bindFarmDao;

    @GetMapping("/uhome/sign")
    public String getBankChinaInfo(String openId) {
        BindFarmPO bindFarmPO = bindFarmDao.selectByOpenId(openId, PlatformEnum.UHOME.getCode());
        if (bindFarmPO == null) {
            return "未绑定";
        }
        return uHomeApi.sign(openId, bindFarmPO.getCookie());
    }

}
