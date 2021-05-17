package com.cjh.common.boss.resp;

import com.alibaba.fastjson.JSON;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@NoArgsConstructor
@Data
public class BossResp<T> {

    /**
     * code : 0
     * message : Success
     */

    private Integer code;
    private String message;

    private Object zpData;

    public T getZpData(Class<T> clazz) {
        return JSON.parseObject(String.valueOf(zpData), clazz);
    }

    public List getZpDataArray(Class clazz) {
        return JSON.parseArray(String.valueOf(zpData), clazz);
    }

    public boolean isSendOk() {
        return (code == null || code == 0) && "Success".equals(message);
    }

    public boolean isDataOk() {
        return isSendOk() && !ObjectUtils.isEmpty(zpData);
    }

}
