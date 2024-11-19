package com.scoinone.core.dto.response.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scoinone.core.common.OrderStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GetBuyOrderResponseDto {
    private Long orderId;
    private Long buyerId;
    private Long virtualAssetId;
    private BigDecimal quantity;
    private BigDecimal price;
    private OrderStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tradeTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}