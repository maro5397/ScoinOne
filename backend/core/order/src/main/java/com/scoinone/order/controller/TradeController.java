package com.scoinone.order.controller;

import com.scoinone.order.dto.response.trade.GetTradesResponseDto;
import com.scoinone.order.entity.TradeEntity;
import com.scoinone.order.mapper.TradeMapper;
import com.scoinone.order.service.TradeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade")
public class TradeController {
    private final TradeService tradeService;

    @GetMapping
    public ResponseEntity<GetTradesResponseDto> getTrades(@RequestHeader(value = "UserId") String userId) {
        List<TradeEntity> tradeByUserId = tradeService.getTradeByUserId(userId);
        return new ResponseEntity<>(
                TradeMapper.INSTANCE.listToGetTradesResponseDto(tradeByUserId),
                HttpStatus.OK
        );
    }
}
