package com.fengxuechao.seed.security.properties;

import lombok.Data;

/**
 * @author fengxuechao
 * @date 2019-09-11
 */
@Data
public class OAuth2Properties {
	
	/**
	 * 使用jwt时为token签名的秘钥
	 */
	private String jwtSigningKey = "seed";

	/**
	 * 客户端配置
	 */
	private OAuth2ClientProperties[] clients = {};
	
}
