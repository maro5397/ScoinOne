package com.scoinone.order.service;

import com.scoinone.order.entity.BuyOrderEntity;
import java.math.BigDecimal;
import java.util.List;

public interface BuyOrderService {
    BuyOrderEntity createBuyOrder(String assetId, BigDecimal quantity, BigDecimal price, String userId);

    BuyOrderEntity cancelBuyOrder(Long id, String userId);

    List<BuyOrderEntity> getBuyOrderByUserId(String userId);
}
