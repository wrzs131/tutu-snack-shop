package com.itheima.dto;

import com.itheima.pojo.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItemWithProductDTO {
    // 订单项信息
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime createdAt;
    
    // 商品信息
    private Product product;
//    private String name;
//    private BigDecimal originalPrice;
//    private Integer stock;
//    private String image;
//    private Integer categoryId;
//    private String discount;
//    private LocalDateTime productCreatedAt;
//    private LocalDateTime updatedAt;
}