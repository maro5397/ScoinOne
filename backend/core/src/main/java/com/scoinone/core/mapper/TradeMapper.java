package com.scoinone.core.mapper;

import com.scoinone.core.dto.response.trade.GetTradesResponseDto;
import com.scoinone.core.dto.response.trade.GetTradeResponseDto;
import com.scoinone.core.entity.Trade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TradeMapper {
    TradeMapper INSTANCE = Mappers.getMapper(TradeMapper.class);

    @Mapping(source = "id", target = "tradeId")
    @Mapping(source = "buyOrder.id", target = "buyId")
    @Mapping(source = "sellOrder.id", target = "sellId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    GetTradeResponseDto tradeToGetTradeResponseDto(Trade trade);

    List<GetTradeResponseDto> tradesToGetTradesResponseDto(List<Trade> trades);

    default GetTradesResponseDto listToGetTradesResponseDto(List<Trade> trades) {
        GetTradesResponseDto responseDto = new GetTradesResponseDto();
        responseDto.setTrades(tradesToGetTradesResponseDto(trades));
        return responseDto;
    }
}