package com.fengxuechao.seed.security.web.config;

import com.fengxuechao.seed.security.web.filter.TimeFilter;
import com.fengxuechao.seed.security.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengxuechao
 * @date 2019-08-08
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TimeInterceptor timeInterceptor;

    /**
     * 添加 Sprint 拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }

    /**
     * 第三方 filter 加载方式
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean timeFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        TimeFilter timeFilter = new TimeFilter();
        registrationBean.setFilter(timeFilter);

        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);

        return registrationBean;
    }

    /**
     * Configure asynchronous request handling options.
     *
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

        // 配置异步请求的拦截器
        // 注册 Callable 的拦截器
        // configurer.registerCallableInterceptors()
        // 注册 DeferredResult 的拦截器
        // configurer.registerDeferredResultInterceptors()
        // 配置线程池
        // configurer.setTaskExecutor()
    }
}
