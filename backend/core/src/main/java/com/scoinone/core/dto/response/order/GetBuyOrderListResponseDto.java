package com.scoinone.core.dto.response.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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
        private BigDecimal quantity;
        private BigDecimal price;
        private String status;
        private String tradeTime;
        private String createdAt;
    }
}