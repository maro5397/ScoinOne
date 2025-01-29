package com.scoinone.order.dto.response.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTradeByUserIdResponseDto {
    private Long tradeId;
    private Long buyId;
    private Long sellId;
    private String virtualAssetId;
    private BigDecimal quantity;
    private BigDecimal price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private OrderDto order;

    @Getter
    @Setter
    public static class OrderDto {
        private Long orderId;
        private String userId;
        private String virtualAssetId;
        private String orderType;
        private BigDecimal price;
        private String status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime tradeTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
    }
}
