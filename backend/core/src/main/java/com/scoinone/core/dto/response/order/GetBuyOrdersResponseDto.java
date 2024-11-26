package com.scoinone.core.dto.response.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetBuyOrdersResponseDto {
    private List<GetBuyOrderResponseDto> buyOrders;
}