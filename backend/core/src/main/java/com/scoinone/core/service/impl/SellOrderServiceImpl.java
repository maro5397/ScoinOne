package com.scoinone.core.service.impl;

import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.service.SellOrderService;
import com.scoinone.core.service.TradeService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SellOrderServiceImpl implements SellOrderService {

    private final EntityManager entityManager;

    private final SellOrderRepository sellOrderRepository;

    private final TradeService tradeService;

    @Override
    public SellOrder createSellOrder(SellOrder sellOrder) {
        entityManager.persist(sellOrder);
        tradeService.processSellOrderTrade(sellOrder);
        return sellOrderRepository.save(sellOrder);
    }

    @Override
    public void deleteSellOrder(Long id) {
        sellOrderRepository.deleteById(id);
    }
}
