package com.scoinone.order.service;

import com.scoinone.order.entity.SellOrderEntity;
import java.math.BigDecimal;
import java.util.List;

// 거래 체결값을 구해서 그 값을 기준으로 위/아래 X개의 데이터를 리스트로 전달
public interface SellOrderService {
    SellOrderEntity createSellOrder(String assetId, BigDecimal quantity, BigDecimal price, String userId);

    String deleteSellOrder(Long id, String userId);

    List<SellOrderEntity> getSellOrderByUserId(String userId);
}
