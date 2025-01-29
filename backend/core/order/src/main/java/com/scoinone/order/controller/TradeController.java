package com.scoinone.order.controller;

import com.scoinone.order.dto.response.trade.GetTradesByUserIdResponseDto;
import com.scoinone.order.dto.response.trade.GetTradesResponseDto;
import com.scoinone.order.entity.TradeEntity;
import com.scoinone.order.mapper.TradeMapper;
import com.scoinone.order.service.TradeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade")
public class TradeController {
    private final TradeService tradeService;

    @GetMapping("/all/{assetId}")
    public ResponseEntity<GetTradesResponseDto> getTradesByAssetId(@PathVariable("assetId") String assetId) {
        List<TradeEntity> tradeByAssetId = tradeService.getTradeByAssetId(assetId);
        return new ResponseEntity<>(
                TradeMapper.INSTANCE.listToGetTradesResponseDto(tradeByAssetId),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<GetTradesByUserIdResponseDto> getTradesByUserId(
            @RequestHeader(value = "UserId") String userId
    ) {
        List<TradeEntity> tradeByUserId = tradeService.getTradeByUserId(userId);
        return new ResponseEntity<>(
                TradeMapper.INSTANCE.listToGetTradesByUserIdResponseDto(tradeByUserId),
                HttpStatus.OK
        );
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<GetTradesByUserIdResponseDto> getTradesByUserIdAndAssetId(
            @PathVariable("assetId") String assetId,
            @RequestHeader(value = "UserId") String userId
    ) {
        List<TradeEntity> tradeByUserIdAndAssetId = tradeService.getTradeByUserIdAndAssetId(userId, assetId);
        return new ResponseEntity<>(
                TradeMapper.INSTANCE.listToGetTradesByUserIdResponseDto(tradeByUserIdAndAssetId),
                HttpStatus.OK
        );
    }
}
