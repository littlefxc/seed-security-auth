package com.fengxuechao.seed.security.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author fengxuechao
 * @date 2019-08-01
 */
@Slf4j
public class TimeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("time filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("time filter start");
        long start = System.currentTimeMillis();
        chain.doFilter(request,response);
        log.info("time filter:{}ms", System.currentTimeMillis() - start);
        log.info("time filter finish");
    }

    @Override
    public void destroy() {
        log.info("time filter destroy");
    }
}
