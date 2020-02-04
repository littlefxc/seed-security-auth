package com.fengxuechao.seed.security.social.weixin.config;

import com.fengxuechao.seed.security.properties.SecurityProperties;
import com.fengxuechao.seed.security.properties.WeixinProperties;
import com.fengxuechao.seed.security.social.view.ImoocConnectView;
import com.fengxuechao.seed.security.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.servlet.View;

/**
 * 微信登录配置
 *
 * @author fengxuechao
 * @date 2020-02-04
 */
@Configuration
@ConditionalOnProperty(prefix = "imooc.security.social.weixin", name = "app-id")
public class WeixinAutoConfiguration extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
                                       Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    protected ConnectionFactory<?> createConnectionFactory() {
        WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
        return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
                weixinConfig.getAppSecret());
    }

    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView() {
        return new ImoocConnectView();
    }

    /**
     * 在 {@link com.fengxuechao.seed.security.social.SocialConfig} 中已有实现，此处无需再次实现。
     *
     * @param connectionFactoryLocator
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }

}
