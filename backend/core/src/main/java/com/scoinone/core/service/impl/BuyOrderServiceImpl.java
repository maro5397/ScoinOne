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
    public List<BuyOrder> getBuyOrders() {
        return buyOrderRepository.findAll();
    }

    @Override
    public BuyOrder getBuyOrderById(Long id) {
        return buyOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BuyOrder not found with id: " + id));
    }

    @Override
    public BuyOrder createBuyOrder(BuyOrder buyOrder) {
        return buyOrderRepository.save(buyOrder);
    }

    @Override
    public BuyOrder updateBuyOrder(Long id, BuyOrder updatedBuyOrder) {
        BuyOrder existedBuyOrder = getBuyOrderById(id);
        existedBuyOrder.setQuantity(updatedBuyOrder.getQuantity());
        existedBuyOrder.setPrice(updatedBuyOrder.getPrice());
        return buyOrderRepository.save(existedBuyOrder);
    }

    @Override
    public void deleteBuyOrder(Long id) {
        buyOrderRepository.deleteById(id);
    }
}
