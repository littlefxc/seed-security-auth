/**
 * 
 */
package com.fengxuechao.seed.security.properties;

/**
 * QQ登录配置项
 * 
 *
 * @author fengxuechao
 * @date 2019-09-11
 */
public class QQProperties extends SocialProperties {
	
	/**
	 * 第三方id，用来决定发起第三方登录的url，默认是 qq。
	 */
	private String providerId = "qq";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
}
