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
    //创建时间
    private LocalDateTime createdAt;
    //更新时间
    private LocalDateTime updatedAt;
}