package com.scoinone.order.service;

import com.scoinone.order.entity.SellOrderEntity;
import java.math.BigDecimal;
import java.util.List;

public interface SellOrderService {
    SellOrderEntity createSellOrder(String assetId, BigDecimal quantity, BigDecimal price, String userId);

    SellOrderEntity cancelSellOrder(Long id, String userId);

    List<SellOrderEntity> getSellOrderByUserId(String userId);
}
