package com.fengxuechao.seed.security.dto;

import lombok.Data;

/**
 * @author fengxuechao
 * @version 0.1
 * @date 2019/6/20
 */
@Data
public class UserQueryCondition {

    private String username;

//    @ApiModelProperty(value = "用户年龄起始值")
    private int age;

//    @ApiModelProperty(value = "用户年龄终止值")
    private int ageTo;

    private String xxx;

}