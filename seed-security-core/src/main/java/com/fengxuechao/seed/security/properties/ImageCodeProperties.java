/**
 *
 */
package com.fengxuechao.seed.security.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图片验证码配置项
 *
 * @author fengxuechao
 * @date 2019-09-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageCodeProperties extends SmsCodeProperties {

    /**
     * 图片宽
     */
    private int width = 67;
    /**
     * 图片高
     */
    private int height = 23;

    public ImageCodeProperties() {
        setLength(4);
    }

}
