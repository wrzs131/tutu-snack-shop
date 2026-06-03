package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.context.BaseContext;
import com.itheima.mapper.OrderMapper;
import com.itheima.mapper.OrderItemMapper;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderItem;
import com.itheima.pojo.OrderQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.service.CartService;
import com.itheima.service.OrderService;
import com.itheima.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private CartService cartService;

    /**
     * 分页查询
     * @param orderQueryParam
     * @return
     */
    @Override
    public PageResult page(OrderQueryParam orderQueryParam) {
        //PageHelper写法
        //1. 设置分页参数(PageHelper自动帮你去设置)
        PageHelper.startPage(orderQueryParam.getPage(),orderQueryParam.getPageSize());
        //2. 执行查询
        List<Order> empList=orderMapper.list(orderQueryParam);
        Page<Order> p=(Page<Order>) empList;
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public Order getById(Integer id) {
        return orderMapper.getById(id);
    }

    @Override
    public void insert(Order order) {
        // 如果前端没传订单id，则获取最新的id并+1
        if (order.getId() == null) {
            Integer maxId = orderMapper.getMaxId();
            if (maxId == null) {
                // 如果表中没有数据，则从1开始
                order.setId(1);
            } else {
                // 最新id + 1
                order.setId(maxId + 1);
            }
        }
        orderMapper.insert(order);
    }

    @Override
    public void update(Order order) {
        orderMapper.update(order);
    }

    @Override
    public void delete(Integer id) {
        orderMapper.delete(id);
    }

    @Override
    public List<Order> listByUserId(Integer userId) {
        return orderMapper.listByUserId(userId);
    }

    @Override
    @Transactional
    public Order createOrderFromCart() {
        // 1. 获取当前用户ID
        Long userIdLong = BaseContext.getCurrentId();
        if (userIdLong == null) {
            userIdLong = 1L;
        }
        Integer userId = userIdLong.intValue();

        // 2. 查询购物车数据
        List<CartVO> cartList = cartService.listByUserId(userId);
        if (cartList == null || cartList.isEmpty()) {
            throw new RuntimeException("购物车为空，无法创建订单");
        }

        // 3. 计算订单总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartVO cartVO : cartList) {
            if (cartVO.getSubtotal() != null) {
                totalAmount = totalAmount.add(cartVO.getSubtotal());
            } else if (cartVO.getPrice() != null && cartVO.getQuantity() != null) {
                BigDecimal subtotal = cartVO.getPrice().multiply(new BigDecimal(cartVO.getQuantity()));
                totalAmount = totalAmount.add(subtotal);
            }
        }

        // 4. 创建订单主表
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus("待确认"); // 状态默认为待确认
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        this.insert(order); // 调用service的insert方法，获取自动生成的ID

        // 5. 创建订单明细表
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartVO cartVO : cartList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(cartVO.getProductId());
            orderItem.setQuantity(cartVO.getQuantity());
            orderItem.setPrice(cartVO.getPrice());
            orderItem.setCreatedAt(LocalDateTime.now());
            orderItems.add(orderItem);
        }
        orderItemMapper.batchInsert(orderItems);

        // 6. 清空购物车
        cartService.clear();

        return order;
    }

    @Override
    public void updateStatus(Integer id, String status) {
        orderMapper.updateStatus(id, status);
    }
}
