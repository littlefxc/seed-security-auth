package com.fengxuechao.seed.security.social.qq.connect;

import com.fengxuechao.seed.security.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * QQ 服务提供商的连接工厂类
 *
 * @author fengxuechao
 * @date 2020-02-04
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     * @param providerId 服务提供商的唯一标识
     * @param appId
     * @param appSecret
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }

}
