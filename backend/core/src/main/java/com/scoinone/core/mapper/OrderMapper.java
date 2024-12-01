package com.scoinone.core.mapper;

import com.scoinone.core.dto.response.order.CreateBuyOrderResponseDto;
import com.scoinone.core.dto.response.order.CreateSellOrderResponseDto;
import com.scoinone.core.dto.response.order.GetBuyOrderResponseDto;
import com.scoinone.core.dto.response.order.GetBuyOrdersResponseDto;
import com.scoinone.core.dto.response.order.GetSellOrderResponseDto;
import com.scoinone.core.dto.response.order.GetSellOrdersResponseDto;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.SellOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "buyer.id", target = "buyerId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status.value", target = "status")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreateBuyOrderResponseDto buyOrderToCreateBuyOrderResponseDto(BuyOrder buyOrder);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "seller.id", target = "sellerId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status.value", target = "status")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreateSellOrderResponseDto sellOrderToCreateSellOrderResponseDto(SellOrder sellOrder);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "buyer.id", target = "buyerId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "tradeTime", target = "tradeTime", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status.value", target = "status")
    GetBuyOrderResponseDto buyOrderToGetBuyOrderResponseDto(BuyOrder buyOrder);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "seller.id", target = "sellerId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "tradeTime", target = "tradeTime", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status.value", target = "status")
    GetSellOrderResponseDto sellOrderToGetSellOrderResponseDto(SellOrder sellOrder);

    List<GetBuyOrderResponseDto> buyOrdersToGetBuyOrdersResponseDto(List<BuyOrder> buyOrders);

    default GetBuyOrdersResponseDto listToGetBuyOrdersResponseDto(List<BuyOrder> buyOrders) {
        GetBuyOrdersResponseDto responseDto = new GetBuyOrdersResponseDto();
        responseDto.setBuyOrders(buyOrdersToGetBuyOrdersResponseDto(buyOrders));
        return responseDto;
    }

    List<GetSellOrderResponseDto> sellOrdersToGetSellOrdersResponseDto(List<SellOrder> sellOrders);

    default GetSellOrdersResponseDto listToGetSellOrdersResponseDto(List<SellOrder> sellOrders) {
        GetSellOrdersResponseDto responseDto = new GetSellOrdersResponseDto();
        responseDto.setSellOrders(sellOrdersToGetSellOrdersResponseDto(sellOrders));
        return responseDto;
    }
}
