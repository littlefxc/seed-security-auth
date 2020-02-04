package com.fengxuechao.seed.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 社交登录配置项
 * @author fengxuechao
 * @date 2019-09-11
 */
@Data
public class SocialProperties {
	
	/**
	 * 社交登录功能拦截的url
	 */
	private String filterProcessesUrl = "/auth";

	private QQProperties qq = new QQProperties();
	
	private WeixinProperties weixin = new WeixinProperties();

	@ConfigurationProperties(prefix = "seed.security.social.qq")
	public QQProperties getQq() {
		return qq;
	}

	@ConfigurationProperties(prefix = "seed.security.social.weixin")
	public WeixinProperties getWeixin() {
		return weixin;
	}
}
