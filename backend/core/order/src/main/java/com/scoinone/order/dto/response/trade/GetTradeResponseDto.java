package com.scoinone.order.dto.response.trade;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTradeResponseDto {
    private Long tradeId;
    private Long buyId;
    private Long sellId;
    private String virtualAssetId;
    private BigDecimal quantity;
    private BigDecimal price;
}
