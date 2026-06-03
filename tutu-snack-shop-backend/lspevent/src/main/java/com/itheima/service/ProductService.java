package com.itheima.service;

import com.itheima.pojo.Product;

import java.util.List;

public interface ProductService {
    /**
     * 查询商品列表
     * @return 商品列表
     */
    List<Product> list();

    /**
     * 根据分类ID查询商品
     * @param categoryId 分类ID
     * @return 商品列表
     */
    List<Product> listByCategoryId(Integer categoryId);

    /**
     * 根据ID查询商品
     * @param id 商品ID
     * @return 商品信息
     */
    Product getById(Integer id);

    /**
     * 新增商品
     * @param product 商品信息
     */
    void insert(Product product);

    /**
     * 更新商品
     * @param product 商品信息
     */
    void update(Product product);

    /**
     * 删除商品
     * @param id 商品ID
     */
    void delete(Integer id);

    /**
     * 更新商品库存
     * @param product 商品信息
     */
    void updateStock(Product product);
}