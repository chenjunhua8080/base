package com.cjh.common.job;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class BankChinaJobTest {

    @Autowired
    BankChinaJob bankChinaJob;

    @Test
    void sign() {
        bankChinaJob.sign();
    }
}