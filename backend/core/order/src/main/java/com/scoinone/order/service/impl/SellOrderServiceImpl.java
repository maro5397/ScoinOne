package com.scoinone.order.service.impl;

import com.scoinone.order.common.status.OrderStatus;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.repository.SellOrderRepository;
import com.scoinone.order.service.SellOrderService;
import com.scoinone.order.service.TradeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellOrderServiceImpl implements SellOrderService {

    private final EntityManager entityManager;

    private final SellOrderRepository sellOrderRepository;

    private final TradeService tradeService;

    @Override
    public SellOrderEntity createSellOrder(String assetId, BigDecimal quantity, BigDecimal price, String userId) {
        SellOrderEntity sellOrder = SellOrderEntity.builder()
                .sellerId(userId)
                .price(price)
                .quantity(quantity)
                .status(OrderStatus.PENDING)
                .virtualAssetId(assetId)
                .build();
        entityManager.persist(sellOrder);
        tradeService.processSellOrderTrade(sellOrder);
        return sellOrderRepository.save(sellOrder);
    }

    @Override
    public SellOrderEntity cancelSellOrder(Long id, String userId) {
        SellOrderEntity sellOrder = sellOrderRepository.findByIdAndSellerIdAndStatus(id, userId, OrderStatus.PENDING)
                .orElseThrow(() -> new EntityNotFoundException(
                        "SellOrder not found with id: " + id +
                                ", userId: " + userId +
                                ", status: " + OrderStatus.PENDING.getValue())
                );
        sellOrder.setStatus(OrderStatus.CANCELED);
        return sellOrder;
    }

    @Override
    public List<SellOrderEntity> getSellOrderByUserId(String userId) {
        return sellOrderRepository.findBySellerIdAndStatus(userId, OrderStatus.PENDING);
    }
}
