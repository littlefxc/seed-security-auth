package com.fengxuechao.seed.security.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * {@link AuthenticationException} 是 Spring Security 在身份认证过程中所有抛出异常的基类
 *
 * @author fengxuechao
 * @date 2019-12-11
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 3527502858901055265L;

    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
