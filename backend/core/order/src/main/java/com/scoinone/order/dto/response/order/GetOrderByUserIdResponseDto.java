package com.scoinone.order.dto.response.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOrderByUserIdResponseDto {
    private String orderId;
    private String userId;
    private String virtualAssetId;
    private String orderType;
    private BigDecimal quantity;
    private BigDecimal price;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
