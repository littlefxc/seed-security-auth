package com.fengxuechao.seed.security.social.support;

import lombok.Data;

/**
 * @author fengxuechao
 * @date 2020-02-04
 */
@Data
public class SocialUserInfo {

	/**
	 * 第三方服务标识，如 QQ
	 */
	private String providerId;

	/**
	 * 第三方服务的userId，如 QQ 的 openid
	 */
	private String providerUserId;
	
	private String nickname;
	
	private String headimg;
	
}
