package com.scoinone.core.dto.response.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateBuyOrderResponseDto {
    private Long orderId;
    private Long buyerId;
    private Long virtualAssetId;
    private BigDecimal quantity;
    private BigDecimal price;
    private String status;
    private String tradeTime;
    private String createdAt;
}