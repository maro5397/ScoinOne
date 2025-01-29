package com.scoinone.order.dto.response.order;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelBuyOrderResponseDto {
    private Long orderId;
    private String buyerId;
    private String virtualAssetId;
    private BigDecimal quantity;
    private BigDecimal price;
    private String status;
}
