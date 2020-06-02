package com.cjh.common.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DogBaseResp {

    private String code;
    private String resultCode;
    private String message;

    public boolean isSuccess() {
        return "0".equals(code) && "0".equals(resultCode);
    }

    public String getErrorMsg() {
        return String.format("%s:%s:%s", code, resultCode, message);
    }
}
