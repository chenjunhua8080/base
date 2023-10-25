package com.cjh.common.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
@Slf4j
class JuHeApiTest {

    @Autowired
    private JuHeApi juHeApi;

    @Test
    void getTodayHistory() {
        log.info(juHeApi.getTodayHistory());
    }

    @Test
    void getRandJoke() {
        log.info(juHeApi.getRandJoke());
    }

    @Test
    void getSimpleWeadther() {
        log.info(juHeApi.getSimpleWeadther("广州"));
    }

    @Test
    void getConstellation() {
        log.info(juHeApi.getConstellation("摩羯座"));
    }

    @Test
    void getQuestionBank() {
        log.info(String.valueOf(juHeApi.getQuestionBank()));
    }

    @Test
    void getQuestionAnswers() {
        log.info(String.valueOf(juHeApi.getQuestionAnswers()));
    }
}