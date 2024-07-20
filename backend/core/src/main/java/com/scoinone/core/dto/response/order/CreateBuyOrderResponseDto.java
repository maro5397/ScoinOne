package com.scoinone.core.dto.response.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBuyOrderResponseDto {
    private String orderId;
    private String buyerId;
    private String virtualAssetId;
    private Double quantity;
    private Double price;
    private String status;
    private String tradeTime;
    private String createdAt;
}