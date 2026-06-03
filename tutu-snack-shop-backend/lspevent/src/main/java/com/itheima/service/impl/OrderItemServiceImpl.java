package com.itheima.service.impl;

import com.itheima.dto.OrderItemWithProductDTO;
import com.itheima.mapper.OrderItemMapper;
import com.itheima.mapper.ProductMapper;
import com.itheima.pojo.OrderItem;
import com.itheima.pojo.Product;
import com.itheima.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<OrderItemWithProductDTO> listByOrderId(Integer orderId) {
        // 查询订单项列表
        List<OrderItem> orderItems = orderItemMapper.listByOrderId(orderId);

        List<OrderItemWithProductDTO> orderItemWithProductDTOList = new ArrayList<>();

        // 遍历每个订单项，获取关联的商品信息
        for (OrderItem orderItem : orderItems) {
            OrderItemWithProductDTO dto = new OrderItemWithProductDTO();
            
            // 设置订单项信息
            dto.setId(orderItem.getId());
            dto.setOrderId(orderItem.getOrderId());
            dto.setProductId(orderItem.getProductId());
            dto.setQuantity(orderItem.getQuantity());
            dto.setPrice(orderItem.getPrice());
            dto.setCreatedAt(orderItem.getCreatedAt());

            // 通过 productId 获取商品信息
            Product product = productMapper.getById(orderItem.getProductId());
            if (product != null) {
                // 设置商品信息
                dto.setProduct(product);
//                dto.setName(product.getName());
//                dto.setOriginalPrice(product.getOriginalPrice());
//                dto.setStock(product.getStock());
//                dto.setImage(product.getImage());
//                dto.setCategoryId(product.getCategoryId());
//                dto.setDiscount(product.getDiscount());
//                dto.setProductCreatedAt(product.getCreatedAt());
//                dto.setUpdatedAt(product.getUpdatedAt());
            }

            orderItemWithProductDTOList.add(dto);
        }

        return orderItemWithProductDTOList;
    }

    @Override
    public void insert(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }

    @Override
    public void batchInsert(List<OrderItem> orderItems) {
        orderItemMapper.batchInsert(orderItems);
    }

    @Override
    public void deleteByOrderId(Integer orderId) {
        orderItemMapper.deleteByOrderId(orderId);
    }
}