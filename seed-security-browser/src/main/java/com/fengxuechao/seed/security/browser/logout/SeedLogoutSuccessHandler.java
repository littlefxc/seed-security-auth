package com.fengxuechao.seed.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fengxuechao.seed.security.support.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的退出成功处理器，如果设置了 seed.security.browser.signOutUrl，则跳到配置的地址上，
 * 如果没配置，则返回json格式的响应。
 *
 * @author fengxuechao
 * @date 2019-02-06
 */
@Slf4j
public class SeedLogoutSuccessHandler implements LogoutSuccessHandler {

	public SeedLogoutSuccessHandler(String signOutSuccessUrl) {
		this.signOutSuccessUrl = signOutSuccessUrl;
	}

	private String signOutSuccessUrl;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		log.info("退出成功");

		if (StringUtils.isBlank(signOutSuccessUrl)) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(new ResultBean<>(200, "退出成功")));
		} else {
			response.sendRedirect(signOutSuccessUrl);
		}

	}

}
