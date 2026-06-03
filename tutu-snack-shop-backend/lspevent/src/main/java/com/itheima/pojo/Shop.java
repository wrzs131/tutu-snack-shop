package com.itheima.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Shop {
    //店铺ID
    private Integer id;
    //店铺名称
    private String name;
    //店铺Logo
    private String logo;
    //营业状态
    private String status;
    //营业时间
    private String businessHours;
    //联系电话
    private String phone;
    //店铺地址
    private String address;
    //创建时间
    private LocalDateTime createdAt;
    //更新时间
    private LocalDateTime updatedAt;
}