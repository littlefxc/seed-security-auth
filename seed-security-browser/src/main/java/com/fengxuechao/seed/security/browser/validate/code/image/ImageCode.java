package com.fengxuechao.seed.security.browser.validate.code.image;

import com.fengxuechao.seed.security.browser.validate.code.ValidateCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图片验证码
 *
 * @author fengxuechao
 * @date 2019-12-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageCode extends ValidateCode {

    private static final long serialVersionUID = -169310896113977255L;

    /**
     * 图片
     */
    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn) {
        this.image = image;
        setCode(code);
       setExpireTime(LocalDateTime.now().plusSeconds(expireIn));
    }
}
