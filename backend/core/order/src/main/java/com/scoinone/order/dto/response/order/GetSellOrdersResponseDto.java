package com.scoinone.order.dto.response.order;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetSellOrdersResponseDto {
    private List<GetSellOrderResponseDto> sellOrders;
}
