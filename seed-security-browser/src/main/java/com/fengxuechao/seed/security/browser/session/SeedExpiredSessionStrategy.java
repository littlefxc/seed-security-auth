package com.fengxuechao.seed.security.browser.session;

import com.fengxuechao.seed.security.properties.SecurityProperties;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

/**
 * 并发登录导致session失效时，默认的处理策略
 *
 * @author fengxuechao
 * @date 2019-02-06
 */
public class SeedExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public SeedExpiredSessionStrategy(SecurityProperties securityPropertie) {
        super(securityPropertie);
    }

    /**
     * @param event 并发登录导致session失效事件
     * @throws IOException
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }

}
