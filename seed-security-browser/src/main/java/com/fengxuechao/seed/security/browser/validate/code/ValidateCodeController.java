package com.fengxuechao.seed.security.browser.validate.code;

import com.fengxuechao.seed.security.browser.validate.code.image.ImageCode;
import com.fengxuechao.seed.security.browser.validate.code.image.ImageCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fengxuechao
 * @date 2019-12-09
 */
@Slf4j
@RestController
public class ValidateCodeController {

    private static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private ImageCodeGenerator imageCodeGenerator;

    /**
     * 生成图形验证码
     *
     * @param request
     * @param response
     */
    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("根据随机数生成图片");
        ImageCode imageCode = createImageCode(request);
        log.debug("将随机数存到 Session 中");
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        log.debug("将生成的图片写到接口的响应中");
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    /**
     *
     * @param request
     * @return
     */
    private ImageCode createImageCode(HttpServletRequest request) {
        return imageCodeGenerator.generate(new ServletWebRequest(request));
    }

    @Bean
    public ImageCodeGenerator imageCodeGenerator() {
        imageCodeGenerator = new ImageCodeGenerator();
        return imageCodeGenerator;
    }
}
