package com.cjh.common.util;

import com.alibaba.fastjson.JSONObject;
import com.cjh.common.resp.DogSignResp;
import com.cjh.common.resp.DogSignResp.ResultBean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    /**
     * java2json
     */
    public static String java2Json(Object object) {
        return JSONObject.toJSONString(object);
    }

    /**
     * json2java
     */
    public static <T> T json2java(String json, Class<T> clazz) {
        return JSONObject.parseObject(json, clazz);
    }

    /**
     * str2json
     */
    public static JSONObject str2json(String object) {
        return JSONObject.parseObject(object);
    }


    public static void main(String[] args) {
        DogSignResp dogSignResp = new DogSignResp();
        dogSignResp.setCode("0");
        dogSignResp.setResultCode("0");
        ResultBean result = new ResultBean();
        result.setSignDay(1);
        dogSignResp.setResult(result);
        System.out.println(java2Json(dogSignResp));
    }
}
