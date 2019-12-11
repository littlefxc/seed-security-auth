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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    private SeedAuthenticationSuccessHandler seedAuthenticationSuccessHandler;

    @Autowired
    private SeedAuthenticationFailureHandler seedAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

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
