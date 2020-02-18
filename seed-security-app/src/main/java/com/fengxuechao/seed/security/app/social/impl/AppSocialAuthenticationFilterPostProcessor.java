package com.fengxuechao.seed.security.app.social.impl;

import com.fengxuechao.seed.security.social.support.SocialAuthenticationFilterPostProcessor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author fengxuechao
 * @date 2020/2/18
 */
//@Component
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor {

    // @formatter:off

    @Setter @Getter
    private AuthenticationSuccessHandler successHandler;

    // @formatter:off

    public AppSocialAuthenticationFilterPostProcessor(AuthenticationSuccessHandler successHandler) {
        this. successHandler = successHandler;
    }


    /**
     * @param socialAuthenticationFilter
     */
    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
    }
}
