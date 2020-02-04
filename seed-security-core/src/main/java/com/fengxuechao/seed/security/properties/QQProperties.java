package com.fengxuechao.seed.security.properties;

import lombok.Data;

/**
 * QQ登录配置项
 *
 * @author fengxuechao
 * @date 2019-09-11
 */
@Data
public class QQProperties {

    /**
     * Application id.
     */
    private String appId;

    /**
     * Application secret.
     */
    private String appSecret;

    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 qq。
     */
    private String providerId = "qq";
}
