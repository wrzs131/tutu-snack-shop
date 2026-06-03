package com.itheima.controller;

import com.itheima.dto.UserLoginDTO;
import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.properties.JwtProperties;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtils;
import com.itheima.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理接口")
public class UserController {
    public static final String userId = "userId";
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信用户登录,{}",userLoginDTO.getCode());
        User user = userService.wxlogin(userLoginDTO);
        //为微信用户生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(userId,user.getId());
        String token = JwtUtils.createJWT(jwtProperties.getUserSecretKey(),jwtProperties.getUserTtl(),claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        //返回的是UserLoginVO对象的3个属性：id、openid、token（userId,secretKey,Ttl）
        return Result.success(userLoginVO);
    }

    @Operation(summary = "查询用户列表")
    @GetMapping("/list")
    public Result list() {
        List<User> userList = userService.list();
        return Result.success(userList);
    }

    @Operation(summary = "根据ID查询用户")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    @Operation(summary = "根据手机号查询用户")
    @GetMapping("/getByPhone/{phone}")
    public Result getByPhone(@PathVariable String phone) {
        User user = userService.getByPhone(phone);
        return Result.success(user);
    }

    @Operation(summary = "新增用户")
    @PostMapping("/insert")
    public Result insert(@RequestBody User user) {
        userService.insert(user);
        return Result.success();
    }

    @Operation(summary = "更新用户")
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.delete(id);
        return Result.success();
    }
}