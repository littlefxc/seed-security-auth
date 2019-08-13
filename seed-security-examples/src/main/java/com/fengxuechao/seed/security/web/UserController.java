package com.fengxuechao.seed.security.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.fengxuechao.seed.security.dto.User;
import com.fengxuechao.seed.security.dto.UserQueryCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @JsonView(User.UserSimpleView.class)
//    @ApiOperation(value = "用户查询服务")
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
    public User getInfo(@PathVariable String id) {
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
    public User createUser(@Validated @RequestBody User user) {
//        if (errors.hasErrors()) {
//            errors.getAllErrors().forEach(System.out::println);
//        }
        user.setId("1");
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        return user;
    }

    @PutMapping("{id:\\d+}")
    public User updateUser(@PathVariable String id, @Validated @RequestBody User user, BindingResult errors) {
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
    public void delete(@PathVariable String id) {
        System.out.println(id);
    }
}
