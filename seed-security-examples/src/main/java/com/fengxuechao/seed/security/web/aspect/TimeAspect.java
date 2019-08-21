package com.fengxuechao.seed.security.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author fengxuechao
 * @date 2019-08-21
 */
@Slf4j
@Aspect
@Component
public class TimeAspect {

    /**
     * 使用@Arount 完全覆盖了 @Before，@After @AfterThrowing，所以一般使用 @Around
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.fengxuechao.seed.security.web.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {

        log.info("time aspect start");

        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            log.info("arg is " + arg);
        }

        long start = System.currentTimeMillis();

        // 执行被被切(被拦截)的方法
        Object object = pjp.proceed();

        log.info("time aspect 耗时:" + (System.currentTimeMillis() - start));

        log.info("time aspect end");

        return object;
    }

}
