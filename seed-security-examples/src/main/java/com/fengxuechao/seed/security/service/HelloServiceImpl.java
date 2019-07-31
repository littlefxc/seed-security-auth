package com.fengxuechao.seed.security.service;

import org.springframework.stereotype.Service;

/**
 * @author fengxuechao
 * @date 2019-07-31
 */
@Service
public class HelloServiceImpl implements HelloService {


    @Override
    public String greeting(String name) {
        System.out.println("greeting");
        return "hello " + name;
    }
}
