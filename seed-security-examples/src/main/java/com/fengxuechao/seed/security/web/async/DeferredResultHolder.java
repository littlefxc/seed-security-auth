package com.fengxuechao.seed.security.web.async;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fengxuechao
 * @date 2019-09-02
 */
@Data
@Component
public class DeferredResultHolder {

    private Map<String, DeferredResult<String>> map = new HashMap<>();

}
