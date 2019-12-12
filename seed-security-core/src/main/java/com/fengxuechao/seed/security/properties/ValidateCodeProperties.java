/**
 * 
 */
package com.fengxuechao.seed.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 验证码配置
 * @author fengxuechao
 * @date 2019-09-11
 */
@Data
public class ValidateCodeProperties {
	
	/**
	 * 图片验证码配置
	 */
	private ImageCodeProperties image = new ImageCodeProperties();

	/**
	 * 短信验证码配置
	 */
	private SmsCodeProperties sms = new SmsCodeProperties();

	@ConfigurationProperties(prefix = "seed.security.code.image")
	public ImageCodeProperties getImage() {
		return image;
	}

	@ConfigurationProperties(prefix = "seed.security.code.sms")
	public SmsCodeProperties getSms() {
		return sms;
	}

}
