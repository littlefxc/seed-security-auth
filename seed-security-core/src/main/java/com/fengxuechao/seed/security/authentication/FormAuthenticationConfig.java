package com.fengxuechao.seed.security.authentication;

import com.fengxuechao.seed.security.properties.SecurityConstants;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 表单登录配置
 *
 * @author fengxuechao
 * @date 2020-02-02
 */
@Component
public class FormAuthenticationConfig {

    private final AuthenticationSuccessHandler seedAuthenticationSuccessHandler;

    private final AuthenticationFailureHandler seedAuthenticationFailureHandler;

    public FormAuthenticationConfig(
            AuthenticationSuccessHandler seedAuthenticationSuccessHandler,
            AuthenticationFailureHandler seedAuthenticationFailureHandler) {
        this.seedAuthenticationSuccessHandler = seedAuthenticationSuccessHandler;
        this.seedAuthenticationFailureHandler = seedAuthenticationFailureHandler;
    }

    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
//                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginPage(SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                .successHandler(seedAuthenticationSuccessHandler)
                .failureHandler(seedAuthenticationFailureHandler);
    }

}
