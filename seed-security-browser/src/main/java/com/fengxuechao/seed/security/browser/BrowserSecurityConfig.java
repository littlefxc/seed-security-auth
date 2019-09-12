package com.fengxuechao.seed.security.browser;

import com.fengxuechao.seed.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        //http.httpBasic()
        http.formLogin()
                .loginPage("/seed-signIn.html")
                .loginProcessingUrl("/authentication/form")
            .and()
                .authorizeRequests()
                .antMatchers("/authentication/require", securityProperties.getBrowser().getSignInPage()).permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .csrf().disable()
        ;
        // @formatter:on
    }
}
