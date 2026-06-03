package com.itheima.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartListVO {
    //购物车商品列表
    private List<CartVO> cartList;
    //购物车总金额
    private BigDecimal totalAmount;
}
