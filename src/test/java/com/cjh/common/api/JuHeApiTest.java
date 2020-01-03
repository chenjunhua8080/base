package com.cjh.common.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class JuHeApiTest {

    @Autowired
    private JuHeApi juHeApi;

    @Test
    public void getTodayHistory() {
        log.info(juHeApi.getTodayHistory());
    }

    @Test
    public void getRandJoke() {
        log.info(juHeApi.getRandJoke());
    }

    @Test
    public void getSimpleWeadther() {
        log.info(juHeApi.getSimpleWeadther("广州"));
    }

    @Test
    public void getConstellation() {
        log.info(juHeApi.getConstellation("摩羯座"));
    }

    @Test
    public void getQuestionBank() {
        log.info(String.valueOf(juHeApi.getQuestionBank()));
    }

    @Test
    public void getQuestionAnswers() {
        log.info(String.valueOf(juHeApi.getQuestionAnswers()));
    }
}