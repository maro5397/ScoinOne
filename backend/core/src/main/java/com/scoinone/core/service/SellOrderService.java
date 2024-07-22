package com.scoinone.core.service;

import com.scoinone.core.entity.SellOrder;

import java.util.List;

public interface SellOrderService {
    List<SellOrder> getSellOrders();

    SellOrder getSellOrderById(Long id);

    SellOrder createSellOrder(SellOrder sellOrder);

    SellOrder updateSellOrder(Long id, SellOrder updatedSellOrder);

    void deleteSellOrder(Long id);
}
