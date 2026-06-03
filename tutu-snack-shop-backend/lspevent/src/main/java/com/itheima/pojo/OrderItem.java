package com.itheima.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItem {
    //关联ID
    private Integer id;
    //订单ID
    private Integer orderId;
    //商品ID
    private Integer productId;
    //购买数量
    private Integer quantity;
    //购买时单价
    private BigDecimal price;
    //创建时间
    private LocalDateTime createdAt;
}