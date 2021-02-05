package com.fengxuechao.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * http://127.0.0.1:9999/server/oauth/authorize?client_id=seed1&redirect_uri=https://www.baidu.com&response_type=code&state=dXKsOs&scope=all
 * http://127.0.0.1:9999/server/oauth/token?grant_type=authorization_code&scope=all&client_id=seed1&client_secret=seedsecrect1&code=PMZqrk&redirectUri=https://www.baidu.com
 * @author fengxuechao
 * @date 2020-02-19
 */
@SpringBootApplication
public class SsoServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SsoServerApplication.class, args);
	}

}
