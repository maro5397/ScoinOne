package com.scoinone.core.dto.response.trade;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class GetTradeListResponseDto {
    private List<TradeDto> trades;

    @Getter
    @Setter
    public static class TradeDto {
        private String tradeId;
        private String buyId;
        private String sellId;
        private String virtualAssetId;
        private BigDecimal quantity;
        private BigDecimal price;
    }
}