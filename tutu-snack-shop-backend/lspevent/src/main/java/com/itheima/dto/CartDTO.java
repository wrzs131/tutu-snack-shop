package com.itheima.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartDTO {
    //购物车ID（编辑时使用）
    private Integer id;
    //商品ID
    private Integer productId;
    //商品数量
    private Integer quantity;
    //商品单价（可选，默认从商品表获取）
    private BigDecimal price;
}
