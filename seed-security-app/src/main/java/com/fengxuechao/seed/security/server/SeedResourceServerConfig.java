package com.fengxuechao.seed.security.server;

import com.fengxuechao.seed.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.fengxuechao.seed.security.authentication.FormAuthenticationConfig;
import com.fengxuechao.seed.security.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.fengxuechao.seed.security.authorize.AuthorizeConfigManager;
import com.fengxuechao.seed.security.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 资源服务器配置
 *
 * @author fengxuechao
 * @date 2020-02-09
 */
@Configuration
@EnableResourceServer
public class SeedResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler seedAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler seedAuthenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer seedSocialSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        formAuthenticationConfig.configure(http);

        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(seedSocialSecurityConfig)
                .and()
                .apply(openIdAuthenticationSecurityConfig)
                .and()
                .csrf().disable();

        authorizeConfigManager.config(http.authorizeRequests());
    }


}