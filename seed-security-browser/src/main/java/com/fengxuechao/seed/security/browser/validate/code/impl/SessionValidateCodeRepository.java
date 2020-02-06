package com.fengxuechao.seed.security.browser.validate.code.impl;

import com.fengxuechao.seed.security.validate.code.ValidateCode;
import com.fengxuechao.seed.security.validate.code.ValidateCodeRepository;
import com.fengxuechao.seed.security.validate.code.ValidateCodeType;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 基于session的验证码存取器
 *
 * @author fengxuechao
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 保存验证码
     * <p>
     * 因为 BufferedImage 没有实现 Serializable 接口，所以无法序列化，
     * 同时也不需要序列化图片而只是给用户看的，所以在存储session时只用保存原始的 code 就行了。
     *
     * @param request
     * @param code
     * @param validateCodeType
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        ValidateCode validateCode = new ValidateCode(code.getCode(), code.getExpireTime());
        sessionStrategy.setAttribute(request, getSessionKey(request, validateCodeType), validateCode);
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request, validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        sessionStrategy.removeAttribute(request, getSessionKey(request, codeType));
    }

}