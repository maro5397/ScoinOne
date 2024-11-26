package com.scoinone.core.dto.request.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateBuyOrderRequestDto {
    private BigDecimal quantity;
    private BigDecimal price;
}
