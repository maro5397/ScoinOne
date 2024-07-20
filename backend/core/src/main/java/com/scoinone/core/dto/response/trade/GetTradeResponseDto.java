package com.scoinone.core.dto.response.trade;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTradeResponseDto {
    private String tradeId;
    private String buyId;
    private String sellId;
    private String virtualAssetId;
    private Double quantity;
    private Double price;
    private String tradeTime;
    private String createdAt;
}