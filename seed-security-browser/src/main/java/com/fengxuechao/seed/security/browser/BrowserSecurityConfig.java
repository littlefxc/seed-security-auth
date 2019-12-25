package com.fengxuechao.seed.security.browser;

import com.fengxuechao.seed.security.browser.authentication.SeedAuthenticationFailureHandler;
import com.fengxuechao.seed.security.browser.authentication.SeedAuthenticationSuccessHandler;
import com.fengxuechao.seed.security.properties.SecurityConstants;
import com.fengxuechao.seed.security.properties.SecurityProperties;
import com.fengxuechao.seed.security.validate.code.ValidateCodeFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

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

    /**
     * 表单登录成功处理
     */
    @Autowired
    private SeedAuthenticationSuccessHandler seedAuthenticationSuccessHandler;

    /**
     * 表单登录失败处理
     */
    @Autowired
    private SeedAuthenticationFailureHandler seedAuthenticationFailureHandler;

    /**
     * 验证码过滤器
     */
    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    /**
     * 密码保护
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    /**
     * remember-me 数据持久化
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        // 系统启动时创建表
        // repository.setCreateTableOnStartup(true);
        return repository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        //http.httpBasic()
        http
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                    .loginPage("/seed-signIn.html")
                    .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                    .successHandler(seedAuthenticationSuccessHandler)
                    .failureHandler(seedAuthenticationFailureHandler)
                    .and()
                        // "记住我" 配置
                        .rememberMe()
                        // 数据持久
                        .tokenRepository(persistentTokenRepository())
                        // 过期时间
                        .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                        .userDetailsService(userDetailsService)
            .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        securityProperties.getBrowser().getSignInPage(),
                        "/code/image").permitAll()
                .anyRequest()
                 .authenticated()
            .and()
                .csrf().disable()
        ;
        // @formatter:on
    }
}
