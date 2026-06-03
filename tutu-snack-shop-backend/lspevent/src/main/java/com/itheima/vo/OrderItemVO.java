package com.itheima.vo;

import com.itheima.pojo.OrderItem;
import com.itheima.pojo.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemVO {
    // 订单项信息
    private OrderItem orderItem;
    // 关联的商品信息
    private Product product;
}
