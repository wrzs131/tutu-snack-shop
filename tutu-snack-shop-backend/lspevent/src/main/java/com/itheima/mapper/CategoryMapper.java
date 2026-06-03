package com.itheima.mapper;

import com.itheima.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 查询分类列表
     * @return 分类列表
     */
    List<Category> list();

    /**
     * 根据ID查询分类
     * @param id 分类ID
     * @return 分类信息
     */
    Category getById(Integer id);

    /**
     * 新增分类
     * @param category 分类信息
     */
    void insert(Category category);

    /**
     * 更新分类
     * @param category 分类信息
     */
    void update(Category category);

    /**
     * 删除分类
     * @param id 分类ID
     */
    void delete(Integer id);
}