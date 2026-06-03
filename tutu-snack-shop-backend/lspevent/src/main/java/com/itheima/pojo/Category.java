package com.itheima.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    //分类ID
    private Integer id;
    //分类名称
    private String name;
    //创建时间
    private LocalDateTime createdAt;
    //更新时间
    private LocalDateTime updatedAt;
}