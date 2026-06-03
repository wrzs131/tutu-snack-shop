package com.itheima.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Cart {
    //购物车ID
    private Integer id;
    //用户ID
    private Integer userId;
    //商品ID
    private Integer productId;
    //商品数量
    private Integer quantity;
    //商品单价
    private BigDecimal price;
    //创建时间
    private LocalDateTime createdAt;
    //更新时间
    private LocalDateTime updatedAt;
}
