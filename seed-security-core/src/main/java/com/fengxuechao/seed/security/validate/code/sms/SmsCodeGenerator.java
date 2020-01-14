package com.fengxuechao.seed.security.validate.code.sms;

import com.fengxuechao.seed.security.properties.SecurityProperties;
import com.fengxuechao.seed.security.validate.code.ValidateCode;
import com.fengxuechao.seed.security.validate.code.ValidateCodeGenerator;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码生成器
 *
 * @author fengxuechao
 * @date 2020-01-14
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Getter
    private final SecurityProperties securityProperties;

    public SmsCodeGenerator(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
    }

}
