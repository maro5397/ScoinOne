package com.scoinone.core.dto.response.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class GetSellOrderListResponseDto {
    private List<SellOrderDto> sellOrders;

    @Getter
    @Setter
    public static class SellOrderDto {
        private String orderId;
        private String sellerId;
        private String virtualAssetId;
        private BigDecimal quantity;
        private BigDecimal price;
        private String status;
        private String tradeTime;
        private String createdAt;
    }
}
