package com.fengxuechao.seed.security.app.social;

import com.fengxuechao.seed.security.app.AppSecurityController;
import com.fengxuechao.seed.security.properties.SecurityConstants;
import com.fengxuechao.seed.security.social.SocialConfig;
import com.fengxuechao.seed.security.social.support.SeedSpringSocialConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * BeanPostProcessor的作用：在 Spring 容器初始化之前和之后都要经过这个接口定义的两个方法
 *
 * @author fengxuechao
 * @date 2020-02-09
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * Spring 容器初始化 SeedSpringSocialConfigurer 之后改变 signUp 地址
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     * @see SeedSpringSocialConfigurer
     * @see SocialConfig#seedSocialSecurityConfig()
     * @see AppSecurityController#getSocialUserInfo(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, "seedSocialSecurityConfig")) {
            SeedSpringSocialConfigurer config = (SeedSpringSocialConfigurer) bean;
            config.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
            return config;
        }
        return bean;
    }

}
