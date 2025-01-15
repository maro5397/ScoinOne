package com.scoinone.order.dto.request.order;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSellOrderRequestDto {
    private BigDecimal quantity;
    private BigDecimal price;
}
