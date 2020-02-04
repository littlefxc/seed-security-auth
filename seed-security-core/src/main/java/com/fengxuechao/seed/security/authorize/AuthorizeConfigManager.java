package com.fengxuechao.seed.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权信息管理器
 * 
 * 用于收集系统中所有 AuthorizeConfigProvider 并加载其配置
 *
 * @author fengxuechao
 * @date 2020-02-02
 */
public interface AuthorizeConfigManager {
	
	/**
	 * @param config
	 */
	void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
