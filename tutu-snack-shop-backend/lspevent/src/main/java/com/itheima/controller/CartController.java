package com.itheima.controller;

import com.itheima.dto.CartDTO;
import com.itheima.pojo.Result;
import com.itheima.service.CartService;
import com.itheima.vo.CartListVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cart")
@Tag(name = "购物车管理接口")
public class CartController {

    @Autowired
    private CartService cartService;

    @Operation(summary = "查询购物车列表")
    @GetMapping("/list")
    public Result<CartListVO> list() {
        log.info("查询购物车列表");
        CartListVO cartListVO = cartService.list();
        return Result.success(cartListVO);
    }

    @Operation(summary = "新增购物车商品")
    @PostMapping("/add")
    public Result add(@RequestBody CartDTO cartDTO) {
        log.info("新增购物车商品: {}", cartDTO);
        cartService.add(cartDTO);
        return Result.success();
    }

    @Operation(summary = "更新购物车商品")
    @PutMapping("/update")
    public Result update(@RequestBody CartDTO cartDTO) {
        log.info("更新购物车商品: {}", cartDTO);
        cartService.update(cartDTO);
        return Result.success();
    }

    @Operation(summary = "删除购物车商品")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除购物车商品: {}", id);
        cartService.delete(id);
        return Result.success();
    }

    @Operation(summary = "清空购物车")
    @DeleteMapping("/clear")
    public Result clear() {
        log.info("清空购物车");
        cartService.clear();
        return Result.success();
    }
}
