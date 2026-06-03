package com.itheima.controller;

import com.itheima.pojo.Order;
import com.itheima.pojo.OrderQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
@Tag(name = "订单管理接口")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(summary = "订单分页")
    @GetMapping("/list")
    public Result page(OrderQueryParam orderQueryParam) {
        log.info("查询请求参数,{},{},{},{}",orderQueryParam);
        PageResult pageResult = orderService.page(orderQueryParam);
        return Result.success(pageResult);
    }

    @Operation(summary = "根据ID查询订单")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Integer id) {
        Order order = orderService.getById(id);
        return Result.success(order);
    }

    @Operation(summary = "新增订单")
    @PostMapping("/insert")
    public Result insert(@RequestBody Order order) {
        orderService.insert(order);
        return Result.success();
    }

    @Operation(summary = "更新订单")
    @PutMapping("/update")
    public Result update(@RequestBody Order order) {
        orderService.update(order);
        return Result.success();
    }

    @Operation(summary = "删除订单")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        orderService.delete(id);
        return Result.success();
    }

    @Operation(summary = "根据用户ID查询订单")
    @GetMapping("/listByUserId/{userId}")
    public Result listByUserId(@PathVariable Integer userId) {
        List<Order> orderList = orderService.listByUserId(userId);
        return Result.success(orderList);
    }

    @Operation(summary = "根据购物车创建订单")
    @PostMapping("/create")
    public Result createFromCart() {
        Order order = orderService.createOrderFromCart();
        return Result.success(order);
    }

    @Operation(summary = "更新订单状态")
    @PutMapping("/updateStatus")
    public Result updateStatus(@RequestParam Integer id, @RequestParam String status) {
        orderService.updateStatus(id, status);
        return Result.success();
    }
}
