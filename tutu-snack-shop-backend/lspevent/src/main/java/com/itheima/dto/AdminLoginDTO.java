package com.itheima.dto;

import lombok.Data;

/**
 * 后台管理端登录参数
 */
@Data
public class AdminLoginDTO {
    //登录账号
    private String username;
    //登录密码
    private String password;
}
