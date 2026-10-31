package com.itheima.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 后台管理端登录返回结果
 */
@Data
@Builder
public class AdminLoginVO {
    private Integer id;
    private String username;
    private String nickname;
    //角色：admin/merchant/rider
    private String role;
    //登录凭证
    private String token;
}
