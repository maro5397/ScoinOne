package com.scoinone.core.service;

import com.scoinone.core.entity.BuyOrder;

import java.util.List;

public interface BuyOrderService {
    List<BuyOrder> getBuyOrders();

    BuyOrder getBuyOrderById(Long id);

    BuyOrder createBuyOrder(BuyOrder buyOrder);

    BuyOrder updateBuyOrder(Long id, BuyOrder updatedBuyOrder);

    void deleteBuyOrder(Long id);
}
