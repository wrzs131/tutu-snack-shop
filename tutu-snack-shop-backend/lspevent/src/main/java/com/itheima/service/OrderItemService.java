package com.itheima.service;

import com.itheima.dto.OrderItemWithProductDTO;
import com.itheima.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {
    /**
     * 根据订单ID查询订单商品
     * @param orderId 订单ID
     * @return 订单商品列表
     */
    List<OrderItemWithProductDTO> listByOrderId(Integer orderId);

    /**
     * 新增订单商品
     * @param orderItem 订单商品信息
     */
    void insert(OrderItem orderItem);

    /**
     * 批量新增订单商品
     * @param orderItems 订单商品列表
     */
    void batchInsert(List<OrderItem> orderItems);

    /**
     * 删除订单商品
     * @param orderId 订单ID
     */
    void deleteByOrderId(Integer orderId);
}