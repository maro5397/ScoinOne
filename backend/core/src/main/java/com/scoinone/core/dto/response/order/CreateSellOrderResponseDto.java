package com.scoinone.core.dto.response.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateSellOrderResponseDto {
    private Long orderId;
    private Long sellerId;
    private Long virtualAssetId;
    private BigDecimal quantity;
    private BigDecimal price;
    private String status;
    private String createdAt;
}
