/**
 * 
 */
package com.fengxuechao.seed.security.properties;

import lombok.Data;

/**
 * @author fengxuechao
 * @date 2019-09-11
 */
@Data
public class SmsCodeProperties {
	
	/**
	 * 验证码长度
	 */
	private int length = 6;
	/**
	 * 过期时间
	 */
	private int expireIn = 60;
	/**
	 * 要拦截的url，多个url用逗号隔开，ant pattern
	 */
	private String url;
}
