package com.cjh.common.util;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class HttpUtil {

    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    public static String doGet(String url, String cookie) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("cookie", cookie);
        return doGet(url, headers, null);
    }

    public static String doGet(String url, Map<String, Object> headers) {
        return doGet(url, headers, null);
    }

    public static String doGet(String url, Map<String, Object> headers, Map<String, Object> params) {
        HttpEntity httpEntity = null;
        if (headers != null) {
            HttpHeaders header = new HttpHeaders();
            for (String item : headers.keySet()) {
                header.add(item, headers.get(item).toString());
            }
            httpEntity = new HttpEntity(header);
        }
        if (params != null) {
            StringBuilder urlBuilder = new StringBuilder(url);
            urlBuilder.append("?");
            for (String item : params.keySet()) {
                urlBuilder.append(item).append("=").append(params.get(item)).append("&");
            }
            url = urlBuilder.toString();
            url = url.substring(0, url.lastIndexOf("&"));
        }
        return send(url, httpEntity, HttpMethod.GET, String.class);
    }

    public static String doPost(String url, Map<String, Object> params) {
        return doPost(url, null, params, String.class);
    }

    public static ResponseEntity doPost(String url, Map<String, Object> headers, Map<String, Object> params) {
        return doPost(url, null, params, ResponseEntity.class);
    }

    public static <T> T doPost(String url, Map<String, Object> headers, Map<String, Object> params, Class<T> clazz) {
        HttpHeaders header = new HttpHeaders();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        if (headers != null) {
            for (String item : headers.keySet()) {
                header.add(item, headers.get(item).toString());
            }
        }
        if (params != null) {
            for (String item : params.keySet()) {
                body.add(item, params.get(item).toString());
            }
        }
        HttpEntity httpEntity = new HttpEntity(body, header);
        return send(url, httpEntity, HttpMethod.POST, clazz);
    }

    private static <T> T send(String url, HttpEntity httpEntity, HttpMethod method, Class<T> clazz) {
        if (httpEntity == null) {
            httpEntity = new HttpEntity(null);
        }
        log.debug("url - {}", url);
        log.debug("headers - {}", httpEntity.getHeaders());
        log.debug("params - {}", httpEntity.getBody());
        log.debug("method - {}", method);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, httpEntity, String.class);
        log.debug("respCode - {}", responseEntity.getStatusCode());
        log.debug("respHeaders - {}", responseEntity.getHeaders());
        String body = responseEntity.getBody();
        if (body == null) {
            return null;
        }
        log.debug("respBody - {}", body.length() >= 200 ? body.substring(200) : body);
        T t;
        if (clazz == String.class) {
            t = clazz.cast(responseEntity.getBody());
        } else {
            t = clazz.cast(responseEntity);
        }
        return t;
    }

}
