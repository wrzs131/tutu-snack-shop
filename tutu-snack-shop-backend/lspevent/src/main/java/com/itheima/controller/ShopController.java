package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.Shop;
import com.itheima.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shop")
@Tag(name = "店铺管理接口")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @Operation(summary = "查询店铺列表")
    @GetMapping("/list")
    public Result list() {
        List<Shop> shopList = shopService.list();
        return Result.success(shopList);
    }

    @Operation(summary = "根据ID查询店铺")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Integer id) {
        Shop shop = shopService.getById(id);
        return Result.success(shop);
    }

    @Operation(summary = "新增店铺")
    @PostMapping("/insert")
    public Result insert(@RequestBody Shop shop) {
        shopService.insert(shop);
        return Result.success();
    }

    @Operation(summary = "更新店铺")
    @PutMapping("/update")
    public Result update(@RequestBody Shop shop) {
        shopService.update(shop);
        return Result.success();
    }

    @Operation(summary = "删除店铺")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        shopService.delete(id);
        return Result.success();
    }
}