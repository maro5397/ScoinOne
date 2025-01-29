package com.scoinone.order.service;

import com.scoinone.order.entity.base.OrderEntity;
import java.util.List;

public interface OrderService {
    List<OrderEntity> getOrders();

    OrderEntity getOrderById(Long id);

    List<OrderEntity> getOrderByUserId(String userId);

    List<OrderEntity> getOrderByAssetId(String assetId);

    List<OrderEntity> getOrderByUserIdAndAssetId(String userId, String assetId);

    String cancelAllOrders(String userId);
}
