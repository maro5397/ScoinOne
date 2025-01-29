package com.scoinone.order.dto.response.trade;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTradesByUserIdResponseDto {
    private List<GetTradeByUserIdResponseDto> trades;
}
