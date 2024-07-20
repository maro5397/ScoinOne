package com.scoinone.core.dto.request.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSellOrderRequestDto {
    private Double quantity;
    private Double price;
    private String status;
}
