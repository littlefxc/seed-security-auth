package com.fengxuechao.seed.security.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * 如果社交登录成功但无业务系统用户，如果不想让引导用户注册而是系统自动注册一个用户则可以使用这个类
 *
 * @author fengxuechao
 * @date 2020-02-05
 */
//@Component
public class ExampleConnectionSignUp implements ConnectionSignUp {

    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息默认创建用户并返回用户唯一标识
        return connection.getDisplayName();
    }

}
