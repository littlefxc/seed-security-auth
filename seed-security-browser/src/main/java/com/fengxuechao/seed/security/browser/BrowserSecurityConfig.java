package com.fengxuechao.seed.security.browser;

import com.fengxuechao.seed.security.authentication.FormAuthenticationConfig;
import com.fengxuechao.seed.security.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.fengxuechao.seed.security.authorize.AuthorizeConfigManager;
import com.fengxuechao.seed.security.properties.SecurityProperties;
import com.fengxuechao.seed.security.validate.code.ValidateCodeSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
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
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

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
                    .sessionManagement()
                    .invalidSessionStrategy(invalidSessionStrategy)
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)
                    .and()
                .and()
                    .logout()
                    .logoutUrl("/signOut")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .deleteCookies("JSESSIONID")
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

    /**
     * Spring Boot 2.0 升级带来的问题，需要显式注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
