package com.itheima.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itheima.dto.UserLoginDTO;
import com.itheima.exception.LoginFailedException;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.properties.WeChatProperties;
import com.itheima.service.UserService;
import com.itheima.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String LOGIN_FAILED = "登录失败";
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatProperties weChatProperties;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public User getByPhone(String phone) {
        return userMapper.getByPhone(phone);
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public User wxlogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

        //判断openid是否为空，如果为空表示登录失败，抛出业务异常
        if(openid==null){
            throw new LoginFailedException(LOGIN_FAILED);
        }
        //判断当前用户是否为新用户
        User user = userMapper.getByOpenid(openid);
        //如果是新用户，自动完成注册
        if(user==null){
            user = User.builder()
                    .openid(openid)
                    .nickname("微信用户_" + openid.substring(0, Math.min(8, openid.length())))
                    .createdAt(LocalDateTime.now())
                    .build();
            //这里的mapper的xml文件之所以要遍历id，是因为controller层要拿到token的userId
            userMapper.insert(user);
        }
        //返回这个用户对象
        return user;
    }

    //将复杂的HTTP请求和JSON解析逻辑封装起来，使主方法更加简洁易懂
    //便于后续修改和调试，例如需要调整微信接口调用参数时只需关注 getOpenid 方法
    private String getOpenid(String code){
        //TODO 测试环境处理：当code为测试值时，返回固定的openid
        if ("123456".equals(code)) {
            log.info("测试环境登录，返回固定openid");
            return "test_openid_123456";
        }
        
        //调用微信接口服务，获得当前微信用户的openid
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        log.info("微信登录参数: appid={}, secret={}, code={}", weChatProperties.getAppid(), weChatProperties.getSecret(), code);
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        log.info("微信登录返回: {}", json);

        //解析微信接口返回的json格式数据（数据里包含字符串类型openid）为JSONObject对象,获取openid
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        log.info("获取到的openid: {}", openid);

        return openid;
    }
}