package com.itheima.service.impl;

import com.itheima.mapper.ShopMapper;
import com.itheima.pojo.Shop;
import com.itheima.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public List<Shop> list() {
        return shopMapper.list();
    }

    @Override
    public Shop getById(Integer id) {
        return shopMapper.getById(id);
    }

    @Override
    public void insert(Shop shop) {
        shopMapper.insert(shop);
    }

    @Override
    public void update(Shop shop) {
        shopMapper.update(shop);
    }

    @Override
    public void delete(Integer id) {
        shopMapper.delete(id);
    }
}