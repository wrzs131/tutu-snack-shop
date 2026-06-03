package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查询用户列表
     * @return 用户列表
     */
    List<User> list();

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    User getById(Integer id);

    /**
     * 根据手机号查询用户
     * @param phone 手机号
     * @return 用户信息
     */
    User getByPhone(String phone);

    /**
     * 新增用户
     * @param user 用户信息
     */
    void insert(User user);

    /**
     * 更新用户
     * @param user 用户信息
     */
    void update(User user);

    /**
     * 删除用户
     * @param id 用户ID
     */
    void delete(Integer id);

    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
//    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);
}