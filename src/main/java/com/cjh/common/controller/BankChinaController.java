package com.cjh.common.controller;

import com.cjh.common.api.BankChinaApi;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class BankChinaController {

    private BankChinaApi bankChinaApi;
    private BindFarmDao bindFarmDao;

    @GetMapping("/getBankChinaInfo")
    public String getBankChinaInfo(String openId) {
        BindFarmPO bindFarmPO = bindFarmDao.selectByOpenId(openId, PlatformEnum.BANK_CHINA.getCode());
        if (bindFarmPO == null) {
            return "未绑定";
        }
        return bankChinaApi.index(openId, bindFarmPO.getCookie(), false);
    }

}
