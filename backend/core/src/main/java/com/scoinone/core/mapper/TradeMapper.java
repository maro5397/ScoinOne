package com.scoinone.core.mapper;

import com.scoinone.core.dto.response.trade.GetTradeListResponseDto;
import com.scoinone.core.dto.response.trade.GetTradeResponseDto;
import com.scoinone.core.entity.Trade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TradeMapper {

    @Mapping(source = "tradeId", target = "tradeId")
    @Mapping(source = "buyOrder.orderId", target = "buyId")
    @Mapping(source = "sellOrder.orderId", target = "sellId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    GetTradeResponseDto tradeToGetTradeResponseDto(Trade trade);

    List<GetTradeResponseDto> tradesToGetTradeResponseDtos(List<Trade> trades);

    default GetTradeListResponseDto listToGetTradeListResponseDto(List<Trade> trades) {
        GetTradeListResponseDto responseDto = new GetTradeListResponseDto();
        responseDto.setTrades(tradesToGetTradeResponseDtos(trades));
        return responseDto;
    }
}