package com.itheima.controller;

import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
@Tag(name = "分类管理接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "查询分类列表")
    @GetMapping("/list")
    public Result list() {
        List<Category> categoryList = categoryService.list();
        return Result.success(categoryList);
    }

    @Operation(summary = "根据ID查询分类")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Integer id) {
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    @Operation(summary = "新增分类")
    @PostMapping("/insert")
    public Result insert(@RequestBody Category category) {
        categoryService.insert(category);
        return Result.success();
    }

    @Operation(summary = "更新分类")
    @PutMapping("/update")
    public Result update(@RequestBody Category category) {
        categoryService.update(category);
        return Result.success();
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return Result.success();
    }
}