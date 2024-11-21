package com.scoinone.core.service.impl;

import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.Trade;
import com.scoinone.core.repository.BuyOrderRepository;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.repository.TradeRepository;
import com.scoinone.core.service.TradeService;
import jakarta.persistence.EntityNotFoundException;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final SellOrderRepository sellOrderRepository;
    private final BuyOrderRepository buyOrderRepository;

    private final Clock clock;

    @Override
    public List<Trade> getTrades() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade getTradeById(Long id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trade not found with id: " + id));
    }

    @Override
    public Trade createTrade(BuyOrder buyOrder, SellOrder sellOrder, BigDecimal tradeQuantity) {
        Trade trade = Trade.builder()
                .buyOrder(buyOrder)
                .sellOrder(sellOrder)
                .virtualAsset(sellOrder.getVirtualAsset())
                .quantity(tradeQuantity)
                .price(sellOrder.getPrice())
                .build();
        return tradeRepository.save(trade);
    }

    @Override
    public List<Trade> processBuyOrderTrade(BuyOrder buyOrder) {
        List<Trade> trades = new ArrayList<>();
        BigDecimal buyQuantity = buyOrder.getQuantity();
        BigDecimal buyPrice = buyOrder.getPrice();
        BigDecimal tradeQuantity;

        List<SellOrder> availableSellOrders = sellOrderRepository.findMatchableSellOrders(buyPrice)
                .orElseThrow(() -> new EntityNotFoundException("SellOrders not found with buyPrice: " + buyPrice));

        for (SellOrder sellOrder : availableSellOrders) {
            BigDecimal sellQuantity = sellOrder.getQuantity();
            if (buyQuantity.compareTo(sellQuantity) <= 0) {
                tradeQuantity = buyQuantity;
                sellOrder.setQuantity(sellQuantity.subtract(buyQuantity));
                buyOrder.setQuantity(new BigDecimal(0));
                buyOrder.setStatus(OrderStatus.COMPLETED);
            } else {
                tradeQuantity = sellQuantity;
                buyOrder.setQuantity(buyQuantity.subtract(sellQuantity));
                sellOrder.setQuantity(new BigDecimal(0));
                sellOrder.setStatus(OrderStatus.COMPLETED);
            }
            trades.add(createTrade(buyOrder, sellOrder, tradeQuantity));
        }
        return trades;
    }

    @Override
    public List<Trade> processSellOrderTrade(SellOrder sellOrder) {
        List<Trade> trades = new ArrayList<>();
        BigDecimal sellQuantity = sellOrder.getQuantity();
        BigDecimal sellPrice = sellOrder.getPrice();
        BigDecimal tradeQuantity;

        List<BuyOrder> availableBuyOrders = buyOrderRepository.findMatchableBuyOrders(sellPrice)
                .orElseThrow(() -> new EntityNotFoundException("BuyOrders not found with sellPrice: " + sellPrice));

        for (BuyOrder buyOrder : availableBuyOrders) {
            BigDecimal buyQuantity = buyOrder.getQuantity();
            if (buyQuantity.compareTo(sellQuantity) <= 0) {
                tradeQuantity = buyQuantity;
                sellOrder.setQuantity(sellQuantity.subtract(buyQuantity));
                buyOrder.setQuantity(new BigDecimal(0));
                buyOrder.setStatus(OrderStatus.COMPLETED);
            } else {
                tradeQuantity = sellQuantity;
                buyOrder.setQuantity(buyQuantity.subtract(sellQuantity));
                sellOrder.setQuantity(new BigDecimal(0));
                sellOrder.setStatus(OrderStatus.COMPLETED);
            }
            trades.add(createTrade(buyOrder, sellOrder, tradeQuantity));
        }
        return trades;
    }
}
