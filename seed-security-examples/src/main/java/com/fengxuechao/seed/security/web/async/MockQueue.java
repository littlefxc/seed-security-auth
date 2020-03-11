package com.fengxuechao.seed.security.web.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 模拟消息队列
 *
 * @author fengxuechao
 * @date 2019-09-02
 */
@Slf4j
@Data
@Component
public class MockQueue {

    /**
     * 下单消息
     */
    private String placeOrder;

    /**
     * 订单完成消息
     */
    private String completeOrder;

    public void setPlaceOrder(String placeOrder) {
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024),
                new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(() -> {
            log.info("接到下单请求, " + placeOrder);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.completeOrder = placeOrder;
            log.info("下单请求处理完毕," + placeOrder);
        });
        singleThreadPool.shutdown();
    }

}
