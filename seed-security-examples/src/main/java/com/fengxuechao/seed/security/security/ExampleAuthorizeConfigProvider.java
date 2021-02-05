package com.fengxuechao.seed.security.security;

import com.fengxuechao.seed.security.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author fengxuechao
 * @date 2020-02-05
 */
@Component
public class ExampleAuthorizeConfigProvider implements AuthorizeConfigProvider {

	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		//demo项目授权配置
		config.antMatchers("/order/**", "/file/**").permitAll();
		return false;
	}

}
