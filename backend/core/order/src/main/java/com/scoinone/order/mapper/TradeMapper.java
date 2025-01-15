package com.scoinone.order.mapper;

import com.scoinone.order.dto.response.trade.GetTradeResponseDto;
import com.scoinone.order.dto.response.trade.GetTradesResponseDto;
import com.scoinone.order.entity.TradeEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TradeMapper {
    TradeMapper INSTANCE = Mappers.getMapper(TradeMapper.class);

    @Mapping(source = "id", target = "tradeId")
    @Mapping(source = "buyOrder.id", target = "buyId")
    @Mapping(source = "sellOrder.id", target = "sellId")
    @Mapping(source = "virtualAssetId", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    GetTradeResponseDto tradeToGetTradeResponseDto(TradeEntity trade);

    List<GetTradeResponseDto> tradesToGetTradesResponseDto(List<TradeEntity> trades);

    default GetTradesResponseDto listToGetTradesResponseDto(List<TradeEntity> trades) {
        GetTradesResponseDto responseDto = new GetTradesResponseDto();
        responseDto.setTrades(tradesToGetTradesResponseDto(trades));
        return responseDto;
    }
}
