package com.scoinone.core.dto.request.order;

import com.scoinone.core.common.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateBuyOrderRequestDto {
    private BigDecimal quantity;
    private BigDecimal price;
    private OrderStatus status;
}