package com.fengxuechao.seed.security.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.fengxuechao.seed.security.dto.User;
import com.fengxuechao.seed.security.dto.UserQueryCondition;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengxuechao
 * @version 0.1
 * @date 2019/6/20
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping
    @JsonView(User.UserSimpleView.class)
//    @ApiOperation(value = "用户查询服务")
    public List<User> query(UserQueryCondition condition) {
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

//        System.out.println(pageable.getPageSize());
//        System.out.println(pageable.getPageNumber());
//        System.out.println(pageable.getSort());

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
        User user = new User();
        user.setId(id);
        user.setUsername("tom");
        user.setPassword("tom");
        return user;
    }
}
