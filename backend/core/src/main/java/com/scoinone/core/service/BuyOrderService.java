package com.scoinone.core.service;

import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.Trade;

import java.math.BigDecimal;
import java.util.List;

// 거래 체결값을 구해서 그 값을 기준으로 위/아래 X개의 데이터를 리스트로 전달 - express
public interface BuyOrderService {

    BuyOrder createBuyOrder(BuyOrder buyOrder);

    void deleteBuyOrder(Long id);
}
