package com.scoinone.core.service;

import com.scoinone.core.entity.Trade;

import java.util.List;

public interface TradeService {
    List<Trade> getTrades();

    Trade getTradeById(Long id);

    Trade createTrade(Trade trade);
}
