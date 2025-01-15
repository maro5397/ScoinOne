package com.scoinone.order.service;

import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.entity.TradeEntity;
import java.math.BigDecimal;
import java.util.List;

public interface TradeService {
    List<TradeEntity> getTrades();

    TradeEntity getTradeById(Long id);

    List<TradeEntity> getTradeByUserId(String userId);

    TradeEntity createTrade(BuyOrderEntity buyOrder, SellOrderEntity sellOrder, BigDecimal tradeQuantity);

    List<TradeEntity> processBuyOrderTrade(BuyOrderEntity buyOrder);

    List<TradeEntity> processSellOrderTrade(SellOrderEntity sellOrder);
}
