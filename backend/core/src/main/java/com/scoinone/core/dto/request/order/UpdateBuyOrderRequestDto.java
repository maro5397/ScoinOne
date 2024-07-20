package com.scoinone.core.dto.request.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBuyOrderRequestDto {
    private Double quantity;
    private Double price;
    private String status;
}