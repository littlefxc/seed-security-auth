package com.fengxuechao.seed.security.social.support;

import lombok.Getter;
import lombok.Setter;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 继承默认的社交登录配置，加入自定义的后处理逻辑
 *
 * @author fengxuechao
 * @date 2020-02-04
 */
public class SeedSpringSocialConfigurer extends SpringSocialConfigurer {

    // @formatter:off

    @Setter @Getter
    private String filterProcessesUrl;

    @Setter @Getter
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    // @formatter:on

    public SeedSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        if (socialAuthenticationFilterPostProcessor != null) {
            socialAuthenticationFilterPostProcessor.process(filter);
        }
        return (T) filter;
    }

}
