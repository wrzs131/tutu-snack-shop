package com.itheima.service;

import com.itheima.dto.CartDTO;
import com.itheima.vo.CartListVO;
import com.itheima.vo.CartVO;

import java.util.List;

public interface CartService {
    /**
     * 查询购物车列表
     * @return 购物车列表（包含总金额）
     */
    CartListVO list();

    /**
     * 根据用户ID查询购物车列表
     * @param userId 用户ID
     * @return 购物车列表
     */
    List<CartVO> listByUserId(Integer userId);

    /**
     * 新增购物车商品
     * @param cartDTO 购物车信息
     */
    void add(CartDTO cartDTO);

    /**
     * 更新购物车商品
     * @param cartDTO 购物车信息
     */
    void update(CartDTO cartDTO);

    /**
     * 删除购物车商品
     * @param id 购物车ID
     */
    void delete(Integer id);

    /**
     * 清空购物车
     */
    void clear();
}
