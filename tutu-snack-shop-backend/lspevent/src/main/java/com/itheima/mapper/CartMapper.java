package com.itheima.mapper;

import com.itheima.pojo.Cart;
import com.itheima.vo.CartVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    /**
     * 根据用户ID查询购物车列表
     * @param userId 用户ID
     * @return 购物车列表
     */
    List<CartVO> listByUserId(Integer userId);

    /**
     * 根据ID查询购物车项
     * @param id 购物车ID
     * @return 购物车信息
     */
    Cart getById(Integer id);

    /**
     * 根据用户ID和商品ID查询购物车项
     * @param userId 用户ID
     * @param productId 商品ID
     * @return 购物车信息
     */
    Cart getByUserIdAndProductId(Integer userId, Integer productId);

    /**
     * 新增购物车项
     * @param cart 购物车信息
     */
    void insert(Cart cart);

    /**
     * 更新购物车项
     * @param cart 购物车信息
     */
    void update(Cart cart);

    /**
     * 删除购物车项
     * @param id 购物车ID
     */
    void delete(Integer id);

    /**
     * 根据用户ID清空购物车
     * @param userId 用户ID
     */
    void deleteByUserId(Integer userId);
}
