package com.scoinone.core.service;

import com.scoinone.core.entity.SellOrder;

import com.scoinone.core.entity.User;
import java.math.BigDecimal;

// 거래 체결값을 구해서 그 값을 기준으로 위/아래 X개의 데이터를 리스트로 전달 - express
public interface SellOrderService {
    SellOrder createSellOrder(Long assetId, BigDecimal quantity, BigDecimal price, User user);

    String deleteSellOrder(Long id, Long userId);
}
