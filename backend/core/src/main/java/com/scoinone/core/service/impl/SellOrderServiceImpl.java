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
    public List<SellOrder> getSellOrders() {
        return sellOrderRepository.findAll();
    }

    @Override
    public SellOrder getSellOrderById(Long id) {
        return sellOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SellOrder not found with id: " + id));
    }

    @Override
    public SellOrder createSellOrder(SellOrder sellOrder) {
        return sellOrderRepository.save(sellOrder);
    }

    @Override
    public SellOrder updateSellOrder(Long id, SellOrder updatedSellOrder) {
        SellOrder existedSellOrder = getSellOrderById(id);
        existedSellOrder.setQuantity(updatedSellOrder.getQuantity());
        existedSellOrder.setPrice(updatedSellOrder.getPrice());
        return sellOrderRepository.save(existedSellOrder);
    }

    @Override
    public void deleteSellOrder(Long id) {
        sellOrderRepository.deleteById(id);
    }
}
