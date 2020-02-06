package com.fengxuechao.seed.security.browser.session;

import com.fengxuechao.seed.security.properties.SecurityProperties;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的session失效处理策略
 *
 * @author fengxuechao
 * @date 2019-02-06
 */
public class SeedInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    public SeedInvalidSessionStrategy(SecurityProperties securityProperties) {
        super(securityProperties);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        onSessionInvalid(request, response);
    }

}
