package com.fengxuechao.seed.security.validate.code.sms;

/**
 * @author fengxuechao
 * @date 2020-01-14
 */
public interface SmsCodeSender {

    /**
     * @param mobile 手机号
     * @param code   短信验证码
     */
    void send(String mobile, String code);

}
