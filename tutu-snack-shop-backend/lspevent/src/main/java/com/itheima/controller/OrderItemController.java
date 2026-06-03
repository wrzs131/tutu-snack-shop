package com.itheima.controller;

import com.itheima.dto.OrderItemWithProductDTO;
import com.itheima.pojo.OrderItem;
import com.itheima.pojo.Result;
import com.itheima.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orderItem")
@Tag(name = "订单商品管理接口")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @Operation(summary = "根据订单ID查询订单商品")
    @GetMapping("/listByOrderId/{orderId}")
    public Result listByOrderId(@PathVariable Integer orderId) {
        List<OrderItemWithProductDTO> orderItemWithProductDTOList = orderItemService.listByOrderId(orderId);
        return Result.success(orderItemWithProductDTOList);
    }

    @Operation(summary = "新增订单商品")
    @PostMapping("/insert")
    public Result insert(@RequestBody OrderItem orderItem) {
        orderItemService.insert(orderItem);
        return Result.success();
    }

    @Operation(summary = "批量新增订单商品")
    @PostMapping("/batchInsert")
    public Result batchInsert(@RequestBody List<OrderItem> orderItems) {
        orderItemService.batchInsert(orderItems);
        return Result.success();
    }

    @Operation(summary = "删除订单商品")
    @DeleteMapping("/deleteByOrderId/{orderId}")
    public Result deleteByOrderId(@PathVariable Integer orderId) {
        orderItemService.deleteByOrderId(orderId);
        return Result.success();
    }
}