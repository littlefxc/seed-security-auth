package com.fengxuechao.seed.security.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认短信验证码发送器
 *
 * @author fengxuechao
 * @date 2020-01-14
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
        log.info("向手机{}发送短信验证码{}", mobile, code);
    }

}
