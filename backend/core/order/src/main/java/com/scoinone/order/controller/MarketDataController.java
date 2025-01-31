package com.scoinone.order.controller;

import com.scoinone.order.common.status.IntervalType;
import com.scoinone.order.dto.response.marketdata.GetAskingPriceResponseDto;
import com.scoinone.order.dto.response.marketdata.GetCandleStickResponseDto;
import com.scoinone.order.dto.response.marketdata.GetTradeSummaryResponseDto;
import com.scoinone.order.service.MarketDataService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/marketdata")
@RequiredArgsConstructor
public class MarketDataController {
    private final MarketDataService marketDataService;

    @GetMapping("/candlestick/{assetId}")
    public ResponseEntity<List<GetCandleStickResponseDto>> getCandleStick(
            @PathVariable("assetId") String assetId,
            @RequestParam("intervalType") IntervalType intervalType,
            @RequestParam("startDate") LocalDateTime startDate,
            @RequestParam("endDate") LocalDateTime endDate
    ) {
        List<GetCandleStickResponseDto> candleSticks = marketDataService.getCandleSticks(
                assetId,
                intervalType,
                startDate,
                endDate
        );
        return new ResponseEntity<>(candleSticks, HttpStatus.OK);
    }

    @GetMapping("/summary/all")
    public ResponseEntity<List<GetTradeSummaryResponseDto>> getAllTradeSummary() {
        List<GetTradeSummaryResponseDto> tradeSummaries = marketDataService.getTradeSummaries();
        return new ResponseEntity<>(tradeSummaries, HttpStatus.OK);
    }

    @GetMapping("/askingprice/{assetId}")
    public ResponseEntity<GetAskingPriceResponseDto> getAskingPrice(@PathVariable("assetId") String assetId) {
        GetAskingPriceResponseDto askingPrice = marketDataService.getAskingPrice(assetId);
        return new ResponseEntity<>(askingPrice, HttpStatus.OK);
    }
}
