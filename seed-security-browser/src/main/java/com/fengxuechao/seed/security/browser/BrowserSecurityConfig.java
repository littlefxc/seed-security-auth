package com.fengxuechao.seed.security.browser;

import com.fengxuechao.seed.security.authentication.FormAuthenticationConfig;
import com.fengxuechao.seed.security.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.fengxuechao.seed.security.authorize.AuthorizeConfigManager;
import com.fengxuechao.seed.security.properties.SecurityConstants;
import com.fengxuechao.seed.security.properties.SecurityProperties;
import com.fengxuechao.seed.security.validate.code.ValidateCodeSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 浏览器环境下安全配置主类
 *
 * @author fengxuechao
 * @date 2019-09-09
 */
@Slf4j
@EnableConfigurationProperties(SecurityProperties.class)
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer seedSpringSocialConfigurer;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    @Qualifier("myUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    /**
     * 密码保护
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        formAuthenticationConfig.configure(http);

        // @formatter:off

        // http.httpBasic()

        http
                .apply(validateCodeSecurityConfig)
                .and().apply(smsCodeAuthenticationSecurityConfig)
                .and().apply(seedSpringSocialConfigurer)
                .and()
                    // "记住我" 配置，如果想在'记住我'登录时记录日志，可以注册一个InteractiveAuthenticationSuccessEvent事件的监听器
                    .rememberMe()
                    // 数据持久
                    .tokenRepository(persistentTokenRepository)
                    // 过期时间
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                .and()
                    .csrf().disable()
        ;

        // @formatter:on

        authorizeConfigManager.config(http.authorizeRequests());
    }

    /**
     * remember-me 数据持久化
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        // 系统启动时创建表
        // repository.setCreateTableOnStartup(true);
        return repository;
    }
}
