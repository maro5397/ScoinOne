package com.scoinone.core.dto.response.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateBuyOrderResponseDto {
    private String orderId;
    private String buyerId;
    private String virtualAssetId;
    private BigDecimal quantity;
    private BigDecimal price;
    private String status;
    private String tradeTime;
    private String createdAt;
}