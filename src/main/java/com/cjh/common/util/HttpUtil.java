package com.cjh.common.util;

import com.cjh.common.boss.BossException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class HttpUtil {

    @SneakyThrows
    public static <T> T getCookie(Class<T> t, ResponseEntity responseEntity) {
        T instance = t.newInstance();
        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        Field[] fields = t.getDeclaredFields();
        String value;
        assert cookies != null;
        for (String cookie : cookies) {
            for (Field field : fields) {
                String fieldName = field.getName();
                if (cookie.contains(fieldName)) {
                    field.setAccessible(true);
                    value = cookie.substring(cookie.indexOf(fieldName) + fieldName.length() + 1, cookie.indexOf(";"));
                    field.set(instance, value);
                    break;
                }
            }
        }
        return instance;
    }

    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    public static ResponseEntity<String> doGetReturnEntity(String url) {
        return send(url, new HttpEntity<>(""), HttpMethod.GET, ResponseEntity.class, null);
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
            for (Entry<String, Object> entry : headers.entrySet()) {
                header.add(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
            }
            httpEntity = new HttpEntity(header);
        }
        if (params != null) {
            StringBuilder urlBuilder = new StringBuilder(url);
            urlBuilder.append("?");
            for (Entry<String, Object> entry : params.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=")
                    .append(entry.getValue() == null ? "" : entry.getValue().toString())
                    .append("&");
            }
            url = urlBuilder.toString();
            url = url.substring(0, url.lastIndexOf("&"));
        }
        return send(url, httpEntity, HttpMethod.GET, String.class, null);
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
            for (Entry<String, Object> entry : headers.entrySet()) {
                header.add(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
            }
        }
        if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {
                body.add(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
            }
        }
        HttpEntity httpEntity = new HttpEntity(body, header);
        return send(url, httpEntity, HttpMethod.POST, clazz, null);
    }

    private static <T> T send(String url, HttpEntity httpEntity, HttpMethod method, Class<T> clazz,
        ClientHttpRequestFactory clientHttpRequestFactory) {
        if (httpEntity == null) {
            httpEntity = new HttpEntity(null);
        }
        log.info("url - {}", url);
        log.info("headers - {}", httpEntity.getHeaders());
        log.info("params - {}", httpEntity.getBody());
        log.info("method - {}", method);
        RestTemplate restTemplate;
        if (!ObjectUtils.isEmpty(clientHttpRequestFactory)) {
            //http2.0
            restTemplate = new RestTemplate(clientHttpRequestFactory);
        } else {
            restTemplate = new RestTemplate();
        }
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, httpEntity, String.class);
        log.info("respCode - {}", responseEntity.getStatusCode());
        log.info("respHeaders - {}", responseEntity.getHeaders());

        String body = responseEntity.getBody();
        if (body == null) {
            return null;
        }
        log.info("respBody - {}", body.contains("<html>") || body.contains("<dev>") ? body.length() : body);
        if (body.contains("/wapi/zpAntispam/verify")) {
            log.warn("respBody - \n{}", body);
            throw new BossException("命中风控验证...");
        }
        T t;
        if (clazz == String.class) {
            return (T) body;
        } else if (clazz == ResponseEntity.class) {
            return (T) responseEntity;
        } else {
            t = JsonUtil.json2java(body, clazz);
        }
        return t;
    }


    public static <T> T doPostByHttp2(String url, Map<String, Object> headers, Map<String, Object> params,
        Class<T> clazz) {
        HttpHeaders header = new HttpHeaders();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        if (headers != null) {
            for (Entry<String, Object> entry : headers.entrySet()) {
                header.add(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
            }
        }
        if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {
                body.add(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
            }
        }
        HttpEntity httpEntity = new HttpEntity(body, header);
        return send(url, httpEntity, HttpMethod.POST, clazz, new OkHttp3ClientHttpRequestFactory());
    }


}
