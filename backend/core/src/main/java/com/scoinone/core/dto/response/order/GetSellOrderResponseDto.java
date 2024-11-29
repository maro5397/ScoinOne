package com.scoinone.core.dto.response.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GetSellOrderResponseDto {
    private Long orderId;
    private Long sellerId;
    private Long virtualAssetId;
    private BigDecimal quantity;
    private BigDecimal price;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tradeTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}