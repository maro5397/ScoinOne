package com.scoinone.core.service.impl;

import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.BuyOrderRepository;
import com.scoinone.core.repository.VirtualAssetRepository;
import com.scoinone.core.service.BuyOrderService;
import com.scoinone.core.service.TradeService;
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

    private final VirtualAssetRepository virtualAssetRepository;

    private final TradeService tradeService;

    @Override
    public BuyOrder createBuyOrder(Long assetId, BigDecimal quantity, BigDecimal price, User user) {
        VirtualAsset virtualAsset = virtualAssetRepository.findById(assetId)
                .orElseThrow(() -> new EntityNotFoundException("VirtualAsset not found with id: " + assetId));
        BuyOrder buyOrder = BuyOrder.builder()
                .buyer(user)
                .price(price)
                .quantity(quantity)
                .status(OrderStatus.PENDING)
                .virtualAsset(virtualAsset)
                .build();
        entityManager.persist(buyOrder);
        tradeService.processBuyOrderTrade(buyOrder);
        return buyOrderRepository.save(buyOrder);
    }

    @Override
    public List<BuyOrder> getBuyOrders(User user) {
        return buyOrderRepository.findByBuyer_IdAndStatus(user.getId(), OrderStatus.PENDING);
    }

    @Override
    public String deleteBuyOrder(Long id) {
        buyOrderRepository.deleteById(id);
        return "BuyOrder deleted successfully";
    }
}
