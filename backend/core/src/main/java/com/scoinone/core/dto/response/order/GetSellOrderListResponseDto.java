package com.scoinone.core.dto.response.order;

import lombok.Getter;
import lombok.Setter;
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
        private Double quantity;
        private Double price;
        private String status;
        private String tradeTime;
        private String createdAt;
    }
}
