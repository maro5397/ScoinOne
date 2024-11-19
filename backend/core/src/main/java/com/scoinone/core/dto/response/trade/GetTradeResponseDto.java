package com.scoinone.core.dto.response.trade;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GetTradeResponseDto {
    private Long tradeId;
    private Long buyId;
    private Long sellId;
    private Long virtualAssetId;
    private BigDecimal quantity;
    private BigDecimal price;
}