package com.itheima.mapper;

import com.itheima.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    /**
     * 根据订单ID查询订单商品
     * @param orderId 订单ID
     * @return 订单商品列表
     */
    List<OrderItem> listByOrderId(Integer orderId);

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