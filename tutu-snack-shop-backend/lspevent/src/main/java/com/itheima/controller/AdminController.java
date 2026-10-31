package com.itheima.controller;

import com.itheima.dto.AdminLoginDTO;
import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.properties.JwtProperties;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtils;
import com.itheima.vo.AdminLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台管理端接口：管理员/商家/骑手账号登录
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@Tag(name = "后台管理接口")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @Operation(summary = "后台账号登录")
    @PostMapping("/login")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        log.info("后台账号登录: {}", adminLoginDTO.getUsername());
        // 校验账号密码，仅管理员/商家/骑手可登录后台
        User user = userService.loginByUsername(adminLoginDTO.getUsername(), adminLoginDTO.getPassword());
        if (user == null) {
            return Result.error("账号或密码错误，或该账号无后台登录权限");
        }
        // 生成jwt令牌，附带角色信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole());
        String token = JwtUtils.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        AdminLoginVO adminLoginVO = AdminLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .role(user.getRole())
                .token(token)
                .build();
        return Result.success(adminLoginVO);
    }
}
