package com.fengxuechao.seed.security.app.social;

import com.fengxuechao.seed.security.properties.SecurityConstants;
import com.fengxuechao.seed.security.social.support.SeedSpringSocialConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author fengxuechao
 * @date 2020-02-09
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(StringUtils.equals(beanName, "seedSocialSecurityConfig")){
			SeedSpringSocialConfigurer config = (SeedSpringSocialConfigurer)bean;
			config.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
			return config;
		}
		return bean;
	}

}
