package com.fengxuechao.seed.security.validate.code.sms;

import com.fengxuechao.seed.security.properties.SecurityConstants;
import com.fengxuechao.seed.security.validate.code.AbstractValidateCodeProcessor;
import com.fengxuechao.seed.security.validate.code.ValidateCode;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码处理器
 *
 * @author fengxuechao
 * @date 2020-01-14
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    /**
     * 短信验证码发送器
     */
    @Getter
    private final SmsCodeSender smsCodeSender;

    public SmsCodeProcessor(SmsCodeSender smsCodeSender) {
        this.smsCodeSender = smsCodeSender;
    }

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile, validateCode.getCode());
    }

}
