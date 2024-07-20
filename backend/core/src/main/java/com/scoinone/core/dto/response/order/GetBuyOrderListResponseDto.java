package com.scoinone.core.dto.response.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetBuyOrderListResponseDto {
    private List<BuyOrderDto> buyOrders;

    @Getter
    @Setter
    public static class BuyOrderDto {
        private String orderId;
        private String buyerId;
        private String virtualAssetId;
        private Double quantity;
        private Double price;
        private String status;
        private String tradeTime;
        private String createdAt;
    }
}