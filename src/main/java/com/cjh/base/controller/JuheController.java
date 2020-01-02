package com.cjh.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.cjh.base.api.JuHeApi;
import com.cjh.base.po.QuestionBankPO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class JuheController {

    private JuHeApi juHeApi;

    @GetMapping("/getTodayHistory")
    public String getTodayHistory() {
        return juHeApi.getTodayHistory();
    }

    @GetMapping("/getRandJoke")
    public String getRandJoke() {
        return juHeApi.getRandJoke();
    }

    @GetMapping("/getSimpleWeadther")
    public String getSimpleWeadther(String city) {
        return juHeApi.getSimpleWeadther(city);
    }

    @GetMapping("/getConstellation")
    public String getConstellation(String start) {
        return juHeApi.getConstellation(start);
    }

    @GetMapping("/getQuestionBank")
    public QuestionBankPO getQuestionBank() {
        return juHeApi.getQuestionBank();
    }

    @GetMapping("/getQuestionAnswers")
    public JSONObject getQuestionAnswers() {
        return juHeApi.getQuestionAnswers();
    }

}
