package com.scoinone.core.dto.request.order;

import com.scoinone.core.common.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateBuyOrderRequestDto {
    private Long buyerId;
    private String virtualAssetId;
    private BigDecimal quantity;
    private BigDecimal price;
    private OrderStatus status;
}
