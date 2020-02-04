package com.fengxuechao.seed.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fengxuechao.seed.security.support.ResultBean;
import com.fengxuechao.seed.security.properties.LoginResponseType;
import com.fengxuechao.seed.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fengxuechao
 * @date 2019-10-12
 */
@Slf4j
@Component("seedAuthenticationSuccessHandler")
//public class SeedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class SeedAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        log.info("{}", authentication);
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getSignInResponseType())) {
            log.info("返回 json");
            response.setContentType("application/json;charset=UTF-8");
            String type = authentication.getClass().getSimpleName();
            response.getWriter().write(objectMapper.writeValueAsString(ResultBean.ok(type)));
        } else {
            log.info("跳转");
            // 如果设置了 seed.security.browser.singInSuccessUrl，总是跳到设置的地址上
            // 如果没设置，则尝试跳转到登录之前访问的地址上，如果登录前访问地址为空，则跳到网站根路径上
            if (StringUtils.isNotBlank(securityProperties.getBrowser().getSingInSuccessUrl())) {
                requestCache.removeRequest(request, response);
                setAlwaysUseDefaultTargetUrl(true);
                setDefaultTargetUrl(securityProperties.getBrowser().getSingInSuccessUrl());
            }
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

}
