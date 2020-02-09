package com.fengxuechao.seed.security.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author fengxuechao
 * @date 2020/2/9
 */
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

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
