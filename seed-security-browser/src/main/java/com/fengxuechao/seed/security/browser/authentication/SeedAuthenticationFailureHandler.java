package com.fengxuechao.seed.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fengxuechao.seed.security.browser.support.ResultBean;
import com.fengxuechao.seed.security.properties.LoginResponseType;
import com.fengxuechao.seed.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 浏览器环境下登录失败的处理器
 *
 * @author fengxuechao
 */
@Slf4j
@Component("seedAuthenticationFailureHandler")
//public class SeedAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class SeedAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        log.info("登录失败");
        log.info("", exception);
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getSignInResponseType())) {
			log.info("返回 json");
			int status = HttpStatus.UNAUTHORIZED.value();
            response.setStatus(status);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new ResultBean(status, exception.getMessage())));
        } else {
			log.info("跳转");
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}