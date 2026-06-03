package com.itheima.service.impl;

import com.itheima.mapper.ProductMapper;
import com.itheima.pojo.Product;
import com.itheima.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> list() {
        return productMapper.list();
    }

    @Override
    public List<Product> listByCategoryId(Integer categoryId) {
        return productMapper.listByCategoryId(categoryId);
    }

    @Override
    public Product getById(Integer id) {
        return productMapper.getById(id);
    }

    @Override
    public void insert(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void update(Product product) {
        productMapper.update(product);
    }

    @Override
    public void delete(Integer id) {
        productMapper.delete(id);
    }

    @Override
    public void updateStock(Product product) {
        productMapper.updateStock(product);
    }
}