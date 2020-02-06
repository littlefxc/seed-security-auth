package com.fengxuechao.seed.security.social;

import com.fengxuechao.seed.security.properties.SecurityProperties;
import com.fengxuechao.seed.security.social.connect.jdbc.SeedJdbcUsersConnectionRepository;
import com.fengxuechao.seed.security.social.support.SeedSpringSocialConfigurer;
import com.fengxuechao.seed.security.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 社交登录配置主类
 *
 * @author fengxuechao
 * @date 2020-02-04
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    @Qualifier("textEncryptor")
    private TextEncryptor encryptor;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    /**
     * sql 建表语句在 {@link JdbcUsersConnectionRepository}同包下，名为 JdbcUsersConnectionRepository.sql
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        if (encryptor == null) {
            encryptor = Encryptors.noOpText();
        }
        SeedJdbcUsersConnectionRepository repository = new SeedJdbcUsersConnectionRepository(
                dataSource, connectionFactoryLocator, encryptor);
        repository.setTablePrefix("seed_");
        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 社交登录配置类，供浏览器或app模块引入设计登录配置用。
     *
     * @return
     */
    @Bean
    public SpringSocialConfigurer seedSocialSecurityConfig() {
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        SeedSpringSocialConfigurer configurer = new SeedSpringSocialConfigurer(filterProcessesUrl);
        // 配置社交登录时找不到用户时引导用户去注册页面
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    /**
     * 用来处理注册流程的工具类
     * 主要解决两个问题：
     * 1. 在注册过程中如何获取 Spring Social 信息
     * 2. 注册完成后如何把业务系统的用户ID再传给 Spring Social
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator)) {
        };
    }
}
