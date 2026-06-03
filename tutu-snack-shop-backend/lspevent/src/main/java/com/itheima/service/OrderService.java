package com.itheima.service;

import com.itheima.pojo.Order;
import com.itheima.pojo.OrderQueryParam;
import com.itheima.pojo.PageResult;

import java.util.List;

public interface OrderService {
    /**
     * 分页查询订单
     * @param orderQueryParam 查询参数
     * @return 分页结果
     */
    PageResult page(OrderQueryParam orderQueryParam);

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
     * 根据购物车创建订单
     * @return 创建的订单信息
     */
    Order createOrderFromCart();

    /**
     * 更新订单状态
     * @param id 订单ID
     * @param status 新的订单状态
     */
    void updateStatus(Integer id, String status);
}
