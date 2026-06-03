package com.itheima.controller;

import com.itheima.pojo.Product;
import com.itheima.pojo.Result;
import com.itheima.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
@Tag(name = "商品管理接口")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "查询商品列表")
    @GetMapping("/list")
    public Result list() {
        List<Product> productList = productService.list();
        return Result.success(productList);
    }

    @Operation(summary = "根据分类ID查询商品")
    @GetMapping("/listByCategoryId/{categoryId}")
    public Result listByCategoryId(@PathVariable Integer categoryId) {
        List<Product> productList = productService.listByCategoryId(categoryId);
        return Result.success(productList);
    }

    @Operation(summary = "根据ID查询商品")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Integer id) {
        Product product = productService.getById(id);
        return Result.success(product);
    }

    @Operation(summary = "新增商品")
    @PostMapping("/insert")
    public Result insert(@RequestBody Product product) {
        productService.insert(product);
        return Result.success();
    }

    @Operation(summary = "更新商品")
    @PutMapping("/update")
    public Result update(@RequestBody Product product) {
        productService.update(product);
        return Result.success();
    }

    @Operation(summary = "删除商品")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        productService.delete(id);
        return Result.success();
    }

    @Operation(summary = "更新商品库存")
    @PutMapping("/updateStock")
    public Result updateStock(@RequestBody Product product) {
        productService.updateStock(product);
        return Result.success();
    }
}