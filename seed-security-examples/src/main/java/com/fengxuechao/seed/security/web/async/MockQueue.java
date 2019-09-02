package com.fengxuechao.seed.security.web.async;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
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
        singleThreadPool.execute(()-> {
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
