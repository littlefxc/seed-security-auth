package com.fengxuechao.seed.security.social.qq.connect;

import com.fengxuechao.seed.security.social.qq.api.QQ;
import com.fengxuechao.seed.security.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * QQ 服务提供商的实现
 *
 * @author fengxuechao
 * @date 2020-02-04
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
    private String appId;

    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    /**
     * 因为 AbstractOAuth2ApiBinding 中的 accessToken 属性是全局变量，所以 QQImpl 在系统中不能是单例，防止引起线程安全问题。
     *
     * @param accessToken
     * @return
     */
    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }

}
