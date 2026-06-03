package com.itheima.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class OrderQueryParam {
    private Integer page=1;//页码
    private Integer pageSize=10;//每页展示记录数
//    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;//入职开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;//入职结束时间
}
