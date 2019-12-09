package com.fengxuechao.seed.security.browser.validate.code;

import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author fengxuechao
 * @date 2019-12-09
 */
@Data
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = -6565800553247879827L;

    /**
     * 随机数
     */
    private String code;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
}
