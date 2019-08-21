package com.fengxuechao.seed.security.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fengxuechao
 * @date 2019-08-01
 */
@Slf4j
@Component
public class TimeInterceptor implements HandlerInterceptor {

    /**
     * 这个方法在控制器某个方法调用之前会被调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("preHandle");

//        log.info("处理器类名 {}", ((HandlerMethod) handler).getBean().getClass().getName());
//        log.info("方法名 {}", ((HandlerMethod) handler).getMethod().getName());

        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    /**
     * 在控制器某个方法调用之后会被调用
     * 如果控制器方法调用过程中产生异常，这个方法不会被调用
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
        Long start = (Long) request.getAttribute("startTime");
        log.info("time interceptor 耗时:" + (System.currentTimeMillis() - start));

    }

    /**
     * 不管控制器方法正常调用或者抛出异常，这个方法都会被调用
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("afterCompletion");
        Long start = (Long) request.getAttribute("startTime");
        log.info("time interceptor 耗时:" + (System.currentTimeMillis() - start));
        log.info("ex is " + ex);

    }

}
