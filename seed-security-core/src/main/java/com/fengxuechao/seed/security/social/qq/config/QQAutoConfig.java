package com.fengxuechao.seed.security.social.qq.config;

import com.fengxuechao.seed.security.properties.QQProperties;
import com.fengxuechao.seed.security.properties.SecurityProperties;
import com.fengxuechao.seed.security.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * @author fengxuechao
 * @date 2020-02-04
 */
@Configuration
@ConditionalOnProperty(prefix = "seed.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
                                       Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
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
