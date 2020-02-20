package com.fengxuechao.seed.security.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.fengxuechao.seed.security.dto.User;
import com.fengxuechao.seed.security.dto.UserQueryCondition;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fengxuechao
 * @version 0.1
 * @date 2019/6/20
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

//	@Autowired
//	private AppSingUpUtils appSingUpUtils;

//	@Autowired
//	private SecurityProperties securityProperties;

    @PostMapping("/regist")
    public void regist(User user, HttpServletRequest request) {

        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
        String userId = user.getUsername();
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
//		appSingUpUtils.doPostSignUp(new ServletWebRequest(request), userId);
    }

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "用户列表查询服务")
    public List<User> query(UserQueryCondition condition) {
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    /**
     * {id:\d+}:正则表示只接受数字
     *
     * @param id
     * @return
     */
    @GetMapping("{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    @ApiOperation(value = "用户查询服务")
    public User getInfo(@ApiParam("用户Id") @PathVariable String id) {
        if (id.equals("2")) {
            throw new RuntimeException("user not exist");
        }
        log.info("进入 getInfo 服务");
        User user = new User();
        user.setId(id);
        user.setUsername("tom");
        user.setPassword("tom");
        return user;
    }

    @PostMapping
    @ApiOperation(value = "创建用户")
    public User createUser(@Validated @RequestBody User user) {
//        if (errors.hasErrors()) {
//            errors.getAllErrors().forEach(System.out::println);
//        }
        user.setId("1");
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        return user;
    }

    @PutMapping("{id:\\d+}")
    @ApiOperation(value = "更新用户")
    public User updateUser(@ApiParam("用户Id") @PathVariable String id, @Validated @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String message = MessageFormat.format("{0}:{1}", fieldError.getField(), fieldError.getDefaultMessage());
                System.out.println(message);
            });
        }
        user.setId("1");
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        return user;
    }

    @DeleteMapping("{id:\\d+}")
    @ApiOperation(value = "删除用户")
    public void delete(@ApiParam("用户Id") @PathVariable String id) {
        System.out.println(id);
    }

    @GetMapping("profile")
    public Object userProfile(Authentication user, HttpServletRequest request) throws ExpiredJwtException,
            UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException,
            UnsupportedEncodingException {

		/*String token = StringUtils.substringAfter(request.getHeader("Authorization"), "bearer ");

		Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
					.parseClaimsJws(token).getBody();

		String company = (String) claims.get("company");

		log.info("{}", company);*/
        return user;
    }
}
