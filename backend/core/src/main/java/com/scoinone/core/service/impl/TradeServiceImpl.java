package com.scoinone.core.service.impl;

import com.scoinone.core.entity.Trade;
import com.scoinone.core.repository.TradeRepository;
import com.scoinone.core.service.TradeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Override
    public List<Trade> getTrades() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade getTradeById(Long id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trade not found with id: " + id));
    }

    // 다른 서버가 담당하게 할지 검색해봐야함
    @Override
    public Trade createTrade(Trade trade) {
        return tradeRepository.save(trade);
    }
}
