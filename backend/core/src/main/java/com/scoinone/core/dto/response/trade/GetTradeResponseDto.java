package com.scoinone.core.dto.response.trade;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GetTradeResponseDto {
    private String tradeId;
    private String buyId;
    private String sellId;
    private String virtualAssetId;
    private BigDecimal quantity;
    private BigDecimal price;
}