package com.scoinone.core.service;

import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.Trade;

import java.math.BigDecimal;
import java.util.List;

public interface TradeService {
    List<Trade> getTrades();

    Trade getTradeById(Long id);

    Trade createTrade(BuyOrder buyOrder, SellOrder sellOrder, BigDecimal tradeQuantity);

    List<Trade> processBuyOrderTrade(BuyOrder buyOrder);

    List<Trade> processSellOrderTrade(SellOrder sellOrder);
}
