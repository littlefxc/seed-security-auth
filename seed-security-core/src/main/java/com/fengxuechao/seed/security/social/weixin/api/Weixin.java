package com.fengxuechao.seed.security.social.weixin.api;

/**
 * 微信API调用接口
 *
 * @author fengxuechao
 * @date 2020-02-04
 */
public interface Weixin {

	WeixinUserInfo getUserInfo(String openId);
	
}
