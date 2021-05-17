package com.cjh.common.boss;

import lombok.Getter;
import lombok.Setter;

/**
 * 功能：业务异常
 */
@Getter
@Setter
public class BossException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int code;
    private final String msg;

    public BossException(String msg) {
        this.msg = msg;
        code = 500;
    }

    public BossException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
