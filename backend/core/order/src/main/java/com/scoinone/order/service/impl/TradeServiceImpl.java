package com.scoinone.order.service.impl;

import com.scoinone.order.common.status.OrderStatus;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.entity.TradeEntity;
import com.scoinone.order.repository.BuyOrderRepository;
import com.scoinone.order.repository.SellOrderRepository;
import com.scoinone.order.repository.TradeRepository;
import com.scoinone.order.service.TradeService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final SellOrderRepository sellOrderRepository;
    private final BuyOrderRepository buyOrderRepository;

    @Override
    public List<TradeEntity> getTrades() {
        return tradeRepository.findAll();
    }

    @Override
    public TradeEntity getTradeById(Long id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trade not found with id: " + id));
    }

    @Override
    public List<TradeEntity> getTradeByUserId(String userId) {
        List<TradeEntity> buyingTrades = tradeRepository.findByBuyOrder_BuyerId(userId);
        List<TradeEntity> sellingTrades = tradeRepository.findBySellOrder_SellerId(userId);

        Set<TradeEntity> tradeSet = new HashSet<>();
        tradeSet.addAll(buyingTrades);
        tradeSet.addAll(sellingTrades);

        List<TradeEntity> allTrades = new ArrayList<>(tradeSet);
        allTrades.sort(Comparator.comparing(TradeEntity::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())));

        return allTrades;
    }

    @Override
    public TradeEntity createTrade(BuyOrderEntity buyOrder, SellOrderEntity sellOrder, BigDecimal tradeQuantity) {
        TradeEntity trade = TradeEntity.builder()
                .buyOrder(buyOrder)
                .sellOrder(sellOrder)
                .virtualAssetId(sellOrder.getVirtualAssetId())
                .quantity(tradeQuantity)
                .price(sellOrder.getPrice())
                .build();

        updateOwnedVirtualAsset(trade);

        return tradeRepository.save(trade);
    }

    @Override
    public List<TradeEntity> processBuyOrderTrade(BuyOrderEntity buyOrder) {
        List<TradeEntity> trades = new ArrayList<>();
        BigDecimal buyQuantity = buyOrder.getQuantity();
        BigDecimal buyPrice = buyOrder.getPrice();
        BigDecimal tradeQuantity;

        List<SellOrderEntity> availableSellOrders = sellOrderRepository.findMatchableSellOrders(buyPrice);

        for (SellOrderEntity sellOrder : availableSellOrders) {
            BigDecimal sellQuantity = sellOrder.getQuantity();
            if (buyQuantity.compareTo(sellQuantity) <= 0) {
                tradeQuantity = buyQuantity;
                sellOrder.setQuantity(sellQuantity.subtract(buyQuantity));
                buyOrder.setQuantity(BigDecimal.ZERO);
                buyOrder.setStatus(OrderStatus.COMPLETED);
            } else {
                tradeQuantity = sellQuantity;
                buyOrder.setQuantity(buyQuantity.subtract(sellQuantity));
                sellOrder.setQuantity(BigDecimal.ZERO);
                sellOrder.setStatus(OrderStatus.COMPLETED);
            }
            trades.add(createTrade(buyOrder, sellOrder, tradeQuantity));
            buyQuantity = buyOrder.getQuantity();
        }
        return trades;
    }

    @Override
    public List<TradeEntity> processSellOrderTrade(SellOrderEntity sellOrder) {
        List<TradeEntity> trades = new ArrayList<>();
        BigDecimal sellQuantity = sellOrder.getQuantity();
        BigDecimal sellPrice = sellOrder.getPrice();
        BigDecimal tradeQuantity;

        List<BuyOrderEntity> availableBuyOrders = buyOrderRepository.findMatchableBuyOrders(sellPrice);

        for (BuyOrderEntity buyOrder : availableBuyOrders) {
            BigDecimal buyQuantity = buyOrder.getQuantity();
            if (buyQuantity.compareTo(sellQuantity) <= 0) {
                tradeQuantity = buyQuantity;
                sellOrder.setQuantity(sellQuantity.subtract(buyQuantity));
                buyOrder.setQuantity(BigDecimal.ZERO);
                buyOrder.setStatus(OrderStatus.COMPLETED);
            } else {
                tradeQuantity = sellQuantity;
                buyOrder.setQuantity(buyQuantity.subtract(sellQuantity));
                sellOrder.setQuantity(BigDecimal.ZERO);
                sellOrder.setStatus(OrderStatus.COMPLETED);
            }
            trades.add(createTrade(buyOrder, sellOrder, tradeQuantity));
            sellQuantity = sellOrder.getQuantity();
        }
        return trades;
    }

    private void updateOwnedVirtualAsset(TradeEntity trade) {
        // feign 으로 데이터 송신할 것
    }
}
