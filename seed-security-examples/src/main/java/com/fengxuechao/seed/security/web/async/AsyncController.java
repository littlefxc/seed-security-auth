package com.fengxuechao.seed.security.web.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * @author fengxuechao
 * @date 2019-09-02
 */
@Slf4j
@RestController
public class AsyncController {

    /**
     * 模拟消息队列
     */
    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    /**
     * 模拟消息队列处理异步请求
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/order/queue")
    public DeferredResult<String> order() throws Exception {
        log.info("主线程开始");

        String orderNumber = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNumber);

        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, result);

        return result;
    }

    /**
     * 使用Callable 处理异步
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/order/callable")
    public Callable<String> orderCallable() throws Exception {
        log.info("主线程开始");
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("副线程开始");
                Thread.sleep(100);
                log.info("副线程返回");
                return "success";
            }
        };
    }

}
