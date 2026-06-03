package com.itheima.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    //商品ID
    private Integer id;
    //商品名称
    private String name;
    //销售价格
    private BigDecimal price;
    //原价
    private BigDecimal originalPrice;
    //库存数量
    private Integer stock;
    //商品图片路径
    private String image;
    //所属分类ID
    private Integer categoryId;
    //折扣信息
    private String discount;
    //创建时间
    private LocalDateTime createdAt;
    //更新时间
    private LocalDateTime updatedAt;
}