package com.scoinone.order.service;

import com.scoinone.order.common.status.IntervalType;
import com.scoinone.order.dto.response.marketdata.GetAskingPriceResponseDto;
import com.scoinone.order.dto.response.marketdata.GetCandleStickResponseDto;
import com.scoinone.order.dto.response.marketdata.GetTradeSummaryResponseDto;
import java.time.LocalDateTime;
import java.util.List;

public interface MarketDataService {
    List<GetCandleStickResponseDto> getCandleSticks(
            String virtualAssetId,
            IntervalType intervalType,
            LocalDateTime start,
            LocalDateTime end
    );

    List<GetTradeSummaryResponseDto> getTradeSummaries();

    GetAskingPriceResponseDto getAskingPrice(String virtualAssetId);
}
