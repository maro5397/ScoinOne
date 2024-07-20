package com.scoinone.core.dto.response.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetSellOrderResponseDto {
    private String orderId;
    private String sellerId;
    private String virtualAssetId;
    private Double quantity;
    private Double price;
    private String status;
    private String tradeTime;
    private String createdAt;
}