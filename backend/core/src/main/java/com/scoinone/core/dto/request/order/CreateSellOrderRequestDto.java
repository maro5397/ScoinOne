package com.scoinone.core.dto.request.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateSellOrderRequestDto {
    private String sellerId;
    private String virtualAssetId;
    private BigDecimal quantity;
    private BigDecimal price;
    private String status;
}