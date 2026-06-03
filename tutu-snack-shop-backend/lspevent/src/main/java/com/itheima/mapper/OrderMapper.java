package com.itheima.mapper;

import com.itheima.pojo.Order;
import com.itheima.pojo.OrderQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    /**
     * 查询订单列表
     * @param orderQueryParam 查询参数
     * @return 订单列表
     */
    List<Order> list(OrderQueryParam orderQueryParam);

    /**
     * 根据ID查询订单
     * @param id 订单ID
     * @return 订单信息
     */
    Order getById(Integer id);

    /**
     * 新增订单
     * @param order 订单信息
     */
    void insert(Order order);

    /**
     * 更新订单
     * @param order 订单信息
     */
    void update(Order order);

    /**
     * 删除订单
     * @param id 订单ID
     */
    void delete(Integer id);

    /**
     * 根据用户ID查询订单
     * @param userId 用户ID
     * @return 订单列表
     */
    List<Order> listByUserId(Integer userId);

    /**
     * 获取最新的订单ID
     * @return 最新的订单ID
     */
    Integer getMaxId();

    /**
     * 更新订单状态
     * @param id 订单ID
     * @param status 新的订单状态
     */
    void updateStatus(@Param("id") Integer id, @Param("status") String status);
}
