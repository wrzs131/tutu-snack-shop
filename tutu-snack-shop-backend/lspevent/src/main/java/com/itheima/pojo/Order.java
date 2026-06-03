package com.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    //订单id
    private Integer id;
    //用户id
    private Integer userId;
    //订单总价
    private BigDecimal totalAmount;
    //订单状态
    private String status;
    //创建时间
    //返回给前端的日期格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
