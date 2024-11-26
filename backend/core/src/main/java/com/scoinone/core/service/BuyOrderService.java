package com.scoinone.core.service;

import com.scoinone.core.entity.BuyOrder;

import com.scoinone.core.entity.User;
import java.math.BigDecimal;

// 거래 체결값을 구해서 그 값을 기준으로 위/아래 X개의 데이터를 리스트로 전달 - express
public interface BuyOrderService {
    BuyOrder createBuyOrder(Long assetId, BigDecimal quantity, BigDecimal price, User user);

    String deleteBuyOrder(Long id);
}
