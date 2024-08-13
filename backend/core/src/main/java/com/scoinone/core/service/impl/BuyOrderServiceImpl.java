package com.scoinone.core.service.impl;

import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.repository.BuyOrderRepository;
import com.scoinone.core.service.BuyOrderService;
import com.scoinone.core.service.TradeService;
import jakarta.persistence.EntityManager;
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
    public BuyOrder createBuyOrder(BuyOrder buyOrder) {
        entityManager.persist(buyOrder);
        tradeService.processBuyOrderTrade(buyOrder);
        return buyOrderRepository.save(buyOrder);
    }

    @Override
    public void deleteBuyOrder(Long id) {
        buyOrderRepository.deleteById(id);
    }
}
