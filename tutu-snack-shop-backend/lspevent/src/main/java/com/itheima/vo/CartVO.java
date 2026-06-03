package com.itheima.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CartVO {
    //购物车ID
    private Integer id;
    //用户ID
    private Integer userId;
    //商品ID
    private Integer productId;
    //商品名称
    private String productName;
    //商品图片
    private String productImage;
    //商品数量
    private Integer quantity;
    //商品单价
    private BigDecimal price;
    //小计（数量 * 单价）
    private BigDecimal subtotal;
    //创建时间
    private LocalDateTime createdAt;
    //更新时间
    private LocalDateTime updatedAt;
}
