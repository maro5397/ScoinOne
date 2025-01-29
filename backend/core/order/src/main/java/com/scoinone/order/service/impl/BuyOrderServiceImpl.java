package com.scoinone.order.service.impl;

import com.scoinone.order.common.status.OrderStatus;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.repository.BuyOrderRepository;
import com.scoinone.order.service.BuyOrderService;
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
public class BuyOrderServiceImpl implements BuyOrderService {

    private final EntityManager entityManager;

    private final BuyOrderRepository buyOrderRepository;

    private final TradeService tradeService;

    @Override
    public BuyOrderEntity createBuyOrder(String assetId, BigDecimal quantity, BigDecimal price, String userId) {
        BuyOrderEntity buyOrder = BuyOrderEntity.builder()
                .buyerId(userId)
                .price(price)
                .quantity(quantity)
                .status(OrderStatus.PENDING)
                .virtualAssetId(assetId)
                .build();
        entityManager.persist(buyOrder);
        tradeService.processBuyOrderTrade(buyOrder);
        return buyOrderRepository.save(buyOrder);
    }

    @Override
    public BuyOrderEntity cancelBuyOrder(Long id, String userId) {
        BuyOrderEntity buyOrder = buyOrderRepository.findByIdAndBuyerIdAndStatus(id, userId, OrderStatus.PENDING)
                .orElseThrow(() -> new EntityNotFoundException(
                        "BuyOrder not found with id: " + id +
                                ", userId: " + userId +
                                ", status: " + OrderStatus.PENDING.getValue())
                );
        buyOrder.setStatus(OrderStatus.CANCELED);
        return buyOrder;
    }

    @Override
    public List<BuyOrderEntity> getBuyOrderByUserId(String userId) {
        return buyOrderRepository.findByBuyerIdAndStatus(userId, OrderStatus.PENDING);
    }
}
