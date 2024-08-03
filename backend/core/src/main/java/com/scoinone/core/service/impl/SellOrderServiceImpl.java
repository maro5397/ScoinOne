package com.scoinone.core.service.impl;

import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.service.SellOrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellOrderServiceImpl implements SellOrderService {

    private final SellOrderRepository sellOrderRepository;

    @Override
    public SellOrder createSellOrder(SellOrder sellOrder) {
        return sellOrderRepository.save(sellOrder);
    }

    @Override
    public void deleteSellOrder(Long id) {
        sellOrderRepository.deleteById(id);
    }
}
