package com.itheima.mapper;

import com.itheima.pojo.Shop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopMapper {
    /**
     * 查询店铺列表
     * @return 店铺列表
     */
    List<Shop> list();

    /**
     * 根据ID查询店铺
     * @param id 店铺ID
     * @return 店铺信息
     */
    Shop getById(Integer id);

    /**
     * 新增店铺
     * @param shop 店铺信息
     */
    void insert(Shop shop);

    /**
     * 更新店铺
     * @param shop 店铺信息
     */
    void update(Shop shop);

    /**
     * 删除店铺
     * @param id 店铺ID
     */
    void delete(Integer id);
}