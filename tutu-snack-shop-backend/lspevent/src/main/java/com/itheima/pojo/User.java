package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //用户ID
    private Integer id;
    //微信用户唯一标识
    private String openid;
    //用户昵称
    private String nickname;
    //手机号
    private String phone;
    //头像
    private String avatar;
    //角色: admin/merchant/rider/customer
    private String role;
    //员工登录账号
    private String username;
    //员工登录密码
    private String password;
    //账号状态: active/disabled
    private String status;
    //创建时间
    private LocalDateTime createdAt;
    //更新时间
    private LocalDateTime updatedAt;
}
