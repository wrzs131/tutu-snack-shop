package com.itheima.service.impl;

import com.itheima.context.BaseContext;
import com.itheima.dto.CartDTO;
import com.itheima.mapper.CartMapper;
import com.itheima.mapper.ProductMapper;
import com.itheima.pojo.Cart;
import com.itheima.pojo.Product;
import com.itheima.service.CartService;
import com.itheima.vo.CartListVO;
import com.itheima.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public CartListVO list() {
        Long userIdLong = BaseContext.getCurrentId();
        if (userIdLong == null) {
            userIdLong = 1L;
        }
        Integer userId = userIdLong.intValue();
        List<CartVO> cartList = cartMapper.listByUserId(userId);

        // 计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartVO cartVO : cartList) {
            if (cartVO.getSubtotal() != null) {
                totalAmount = totalAmount.add(cartVO.getSubtotal());
            }
        }

        CartListVO cartListVO = new CartListVO();
        cartListVO.setCartList(cartList);
        cartListVO.setTotalAmount(totalAmount);

        return cartListVO;
    }

    @Override
    public List<CartVO> listByUserId(Integer userId) {
        return cartMapper.listByUserId(userId);
    }

    @Override
    public void add(CartDTO cartDTO) {
        Long userIdLong = BaseContext.getCurrentId();
        if (userIdLong == null) {
            userIdLong = 1L;
        }
        Integer userId = userIdLong.intValue();
        Integer productId = cartDTO.getProductId();
        Integer quantity = cartDTO.getQuantity();

        // 检查商品是否已存在于购物车中
        Cart existingCart = cartMapper.getByUserIdAndProductId(userId, productId);

        if (existingCart != null) {
            // 如果存在，更新数量
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            cartMapper.update(existingCart);
        } else {
            // 如果不存在，新增购物车项
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(quantity);

            // 获取商品价格
            if (cartDTO.getPrice() != null) {
                cart.setPrice(cartDTO.getPrice());
            } else {
                Product product = productMapper.getById(productId);
                if (product != null) {
                    cart.setPrice(product.getPrice());
                }
            }

            cartMapper.insert(cart);
        }
    }

    @Override
    public void update(CartDTO cartDTO) {
        Long userIdLong = BaseContext.getCurrentId();
        if (userIdLong == null) {
            userIdLong = 1L;
        }
        Integer userId = userIdLong.intValue();

        Cart cart = cartMapper.getById(cartDTO.getId());
        if (cart == null) {
            throw new RuntimeException("购物车项不存在");
        }

        // 验证购物车项是否属于当前用户
        if (!cart.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作此购物车项");
        }

        cart.setQuantity(cartDTO.getQuantity());

        if (cartDTO.getPrice() != null) {
            cart.setPrice(cartDTO.getPrice());
        }

        cartMapper.update(cart);
    }

    @Override
    public void delete(Integer id) {
        Long userIdLong = BaseContext.getCurrentId();
        if (userIdLong == null) {
            userIdLong = 1L;
        }
        Integer userId = userIdLong.intValue();

        // 验证购物车项是否属于当前用户
        Cart cart = cartMapper.getById(id);
        if (cart != null && !cart.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作此购物车项");
        }

        cartMapper.delete(id);
    }

    @Override
    public void clear() {
        Long userIdLong = BaseContext.getCurrentId();
        if (userIdLong == null) {
            userIdLong = 1L;
        }
        Integer userId = userIdLong.intValue();
        cartMapper.deleteByUserId(userId);
    }
}
