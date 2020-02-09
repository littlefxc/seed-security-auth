package com.fengxuechao.seed.security.browser;

import com.fengxuechao.seed.security.properties.SecurityConstants;
import com.fengxuechao.seed.security.properties.SecurityProperties;
import com.fengxuechao.seed.security.social.SocialController;
import com.fengxuechao.seed.security.social.support.SocialUserInfo;
import com.fengxuechao.seed.security.support.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 浏览器环境下与安全相关的服务
 *
 * @author fengxuechao
 * @date 2019-09-11
 */
@Slf4j
@RestController
public class BrowserSecurityController extends SocialController {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 当需要身份认证时，跳转到这里
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResultBean requireAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // 从缓存中获取SpringSecurity缓存的请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是:" + targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getSignInPage());
            }
        }
        return new ResultBean(HttpStatus.UNAUTHORIZED.value(), "访问的服务需要身份认证，请引导用户到登录页");
    }

    /**
     * 用户第一次社交登录时，会引导用户进行用户注册或绑定，此服务用于在注册或绑定页面获取社交网站用户信息
     * <p>
     * session 在哪儿设置的？社交登录成功后没有找到对应的业务系统用户从而跳转到注册页面前设置的。
     * {@link org.springframework.social.security.SocialAuthenticationFilter#doAuthentication(org.springframework.social.security.provider.SocialAuthenticationService, javax.servlet.http.HttpServletRequest, org.springframework.social.security.SocialAuthenticationToken)}
     *
     * @param request
     * @return
     */
    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        return buildSocialUserInfo(connection);
    }
}
