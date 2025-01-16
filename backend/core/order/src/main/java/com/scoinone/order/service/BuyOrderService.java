package com.scoinone.order.service;

import com.scoinone.order.entity.BuyOrderEntity;
import java.math.BigDecimal;
import java.util.List;

// 거래 체결값을 구해서 그 값을 기준으로 위/아래 X개의 데이터를 리스트로 전달
public interface BuyOrderService {
    BuyOrderEntity createBuyOrder(String assetId, BigDecimal quantity, BigDecimal price, String userId);

    String deleteBuyOrder(Long id, String userId);

    List<BuyOrderEntity> getBuyOrderByUserId(String userId);
}
