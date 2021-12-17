package com.cjh.common.job;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TongchengJobTest {

    @Autowired
    private TongchengJob job;

    @Test
    void waterTree() {
        job.waterTree();
    }
}