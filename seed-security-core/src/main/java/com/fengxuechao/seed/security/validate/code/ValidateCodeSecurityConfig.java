package com.fengxuechao.seed.security.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

/**
 * 校验码相关安全配置
 *
 * @author fengxuechao
 * @date 2020-02-02
 */
@Component("validateCodeSecurityConfig")
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }

}
