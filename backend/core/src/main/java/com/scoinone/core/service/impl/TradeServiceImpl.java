package com.scoinone.core.service.impl;

import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.OwnedVirtualAsset;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.Trade;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.BuyOrderRepository;
import com.scoinone.core.repository.OwnedVirtualAssetRepository;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.repository.TradeRepository;
import com.scoinone.core.service.TradeService;
import jakarta.persistence.EntityNotFoundException;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final SellOrderRepository sellOrderRepository;
    private final BuyOrderRepository buyOrderRepository;
    private final OwnedVirtualAssetRepository ownedVirtualAssetRepository;

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

        updateOwnedVirtualAsset(trade);

        return tradeRepository.save(trade);
    }

    @Override
    public List<Trade> processBuyOrderTrade(BuyOrder buyOrder) {
        List<Trade> trades = new ArrayList<>();
        BigDecimal buyQuantity = buyOrder.getQuantity();
        BigDecimal buyPrice = buyOrder.getPrice();
        BigDecimal tradeQuantity;

        List<SellOrder> availableSellOrders = sellOrderRepository.findMatchableSellOrders(buyPrice);

        for (SellOrder sellOrder : availableSellOrders) {
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
    public List<Trade> processSellOrderTrade(SellOrder sellOrder) {
        List<Trade> trades = new ArrayList<>();
        BigDecimal sellQuantity = sellOrder.getQuantity();
        BigDecimal sellPrice = sellOrder.getPrice();
        BigDecimal tradeQuantity;

        List<BuyOrder> availableBuyOrders = buyOrderRepository.findMatchableBuyOrders(sellPrice);

        for (BuyOrder buyOrder : availableBuyOrders) {
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

    private void updateOwnedVirtualAsset(Trade trade) {
        OwnedVirtualAsset buyerOwnedVirtualAsset = getOwnedVirtualAsset(
                trade.getBuyOrder().getBuyer(),
                trade.getVirtualAsset()
        );

        buyerOwnedVirtualAsset.setAmount(buyerOwnedVirtualAsset.getAmount().add(trade.getQuantity()));
        ownedVirtualAssetRepository.save(buyerOwnedVirtualAsset);

        OwnedVirtualAsset sellerOwnedVirtualAsset = getOwnedVirtualAsset(
                trade.getSellOrder().getSeller(),
                trade.getVirtualAsset()
        );

        sellerOwnedVirtualAsset.setAmount(sellerOwnedVirtualAsset.getAmount().subtract(trade.getQuantity()));
        ownedVirtualAssetRepository.save(buyerOwnedVirtualAsset);
    }

    private OwnedVirtualAsset getOwnedVirtualAsset(User user, VirtualAsset virtualAsset) {
        return ownedVirtualAssetRepository.findByUser_IdAndVirtualAsset_Id(user.getId(), virtualAsset.getId())
                .orElseGet(() -> OwnedVirtualAsset.builder()
                        .user(user)
                        .virtualAsset(virtualAsset)
                        .amount(BigDecimal.ZERO)
                        .build()
                );
    }
}
