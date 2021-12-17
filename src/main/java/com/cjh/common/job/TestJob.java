package com.cjh.common.job;

import com.cjh.common.util.XxlJobUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * test job
 *
 * @author cjh
 * @date 2020/4/3
 */
@Component
@Slf4j
public class TestJob {

    @XxlJob("job.test.test")
    public void test() {
        XxlJobUtil.showLog("[----- test job ----]");
    }

}
