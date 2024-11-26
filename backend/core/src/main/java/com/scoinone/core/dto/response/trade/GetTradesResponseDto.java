package com.scoinone.core.dto.response.trade;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetTradesResponseDto {
    private List<GetTradeResponseDto> trades;
}