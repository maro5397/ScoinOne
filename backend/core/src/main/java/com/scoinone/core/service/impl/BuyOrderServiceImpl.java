package com.scoinone.core.service.impl;

import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.repository.BuyOrderRepository;
import com.scoinone.core.service.BuyOrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyOrderServiceImpl implements BuyOrderService {

    private final BuyOrderRepository buyOrderRepository;

    @Override
    public BuyOrder createBuyOrder(BuyOrder buyOrder) {
        return buyOrderRepository.save(buyOrder);
    }

    @Override
    public void deleteBuyOrder(Long id) {
        buyOrderRepository.deleteById(id);
    }
}
