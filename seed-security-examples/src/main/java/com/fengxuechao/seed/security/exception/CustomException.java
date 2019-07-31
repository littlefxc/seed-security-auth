package com.fengxuechao.seed.security.exception;

/**
 * 自定义异常处理
 *
 * @author fengxuechao
 * @date 2019-08-01
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 4067507408727172918L;

    private int code;

    public CustomException() {
        super();
    }

    public CustomException(int code, String message) {
        super(message);
        this.setCode(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
