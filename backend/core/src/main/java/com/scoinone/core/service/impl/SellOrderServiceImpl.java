package com.scoinone.core.service.impl;

import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.repository.VirtualAssetRepository;
import com.scoinone.core.service.SellOrderService;
import com.scoinone.core.service.TradeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellOrderServiceImpl implements SellOrderService {

    private final EntityManager entityManager;

    private final SellOrderRepository sellOrderRepository;

    private final VirtualAssetRepository virtualAssetRepository;

    private final TradeService tradeService;

    @Override
    public SellOrder createSellOrder(Long assetId, BigDecimal quantity, BigDecimal price, User user) {
        VirtualAsset virtualAsset = virtualAssetRepository.findById(assetId)
                .orElseThrow(() -> new EntityNotFoundException("VirtualAsset not found with id: " + assetId));
        SellOrder sellOrder = SellOrder.builder()
                .seller(user)
                .price(price)
                .quantity(quantity)
                .status(OrderStatus.PENDING)
                .virtualAsset(virtualAsset)
                .build();
        entityManager.persist(sellOrder);
        tradeService.processSellOrderTrade(sellOrder);
        return sellOrderRepository.save(sellOrder);
    }

    @Override
    public String deleteSellOrder(Long id, Long userId) {
        Long count = sellOrderRepository.deleteByIdAndSeller_Id(id, userId);
        if (count == 0) {
            throw new EntityNotFoundException("SellOrder not found or you are not authorized to delete this SellOrder");
        }
        return "SellOrder deleted successfully";
    }
}
