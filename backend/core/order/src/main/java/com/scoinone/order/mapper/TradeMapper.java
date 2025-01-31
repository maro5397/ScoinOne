package com.scoinone.order.mapper;

import com.scoinone.order.common.status.OrderType;
import com.scoinone.order.dto.response.trade.GetTradeByUserIdResponseDto;
import com.scoinone.order.dto.response.trade.GetTradeByUserIdResponseDto.OrderDto;
import com.scoinone.order.dto.response.trade.GetTradeResponseDto;
import com.scoinone.order.dto.response.trade.GetTradesByUserIdResponseDto;
import com.scoinone.order.dto.response.trade.GetTradesResponseDto;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.entity.TradeEntity;
import java.util.List;
import org.mapstruct.Context;
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

    @Mapping(source = "id", target = "tradeId")
    @Mapping(source = "buyOrder.id", target = "buyId")
    @Mapping(source = "sellOrder.id", target = "sellId")
    @Mapping(source = "virtualAssetId", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(target = "order", expression = "java(mapOrder(userId, trade))")
    GetTradeByUserIdResponseDto tradeToGetTradeByUserIdResponseDto(@Context String userId, TradeEntity trade);

    default OrderDto mapOrder(String userId, TradeEntity trade) {
        if (trade.getBuyOrder() != null && trade.getBuyOrder().getBuyerId().equals(userId)) {
            return mapBuyOrderToOrderDto(trade.getBuyOrder());
        } else if (trade.getSellOrder() != null && trade.getSellOrder().getSellerId().equals(userId)) {
            return mapSellOrderToOrderDto(trade.getSellOrder());
        }
        return null;
    }

    default OrderDto mapBuyOrderToOrderDto(BuyOrderEntity buyOrder) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(buyOrder.getId());
        orderDto.setUserId(buyOrder.getBuyerId());
        orderDto.setVirtualAssetId(buyOrder.getVirtualAssetId());
        orderDto.setOrderType(OrderType.BUY_TYPE.getValue());
        orderDto.setPrice(buyOrder.getPrice());
        orderDto.setStatus(buyOrder.getStatus().getValue());
        orderDto.setTradeTime(buyOrder.getTradeTime());
        orderDto.setCreatedAt(buyOrder.getCreatedAt());
        return orderDto;
    }

    default OrderDto mapSellOrderToOrderDto(SellOrderEntity sellOrder) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(sellOrder.getId());
        orderDto.setUserId(sellOrder.getSellerId());
        orderDto.setVirtualAssetId(sellOrder.getVirtualAssetId());
        orderDto.setOrderType(OrderType.SELL_TYPE.getValue());
        orderDto.setPrice(sellOrder.getPrice());
        orderDto.setStatus(sellOrder.getStatus().getValue());
        orderDto.setTradeTime(sellOrder.getTradeTime());
        orderDto.setCreatedAt(sellOrder.getCreatedAt());
        return orderDto;
    }

    List<GetTradeByUserIdResponseDto> tradeToGetTradesByUserIdResponseDto(
            @Context String userId,
            List<TradeEntity> trades
    );

    default GetTradesByUserIdResponseDto listToGetTradesByUserIdResponseDto(String userId, List<TradeEntity> trades) {
        GetTradesByUserIdResponseDto responseDto = new GetTradesByUserIdResponseDto();
        responseDto.setTrades(tradeToGetTradesByUserIdResponseDto(userId, trades));
        return responseDto;
    }
}
