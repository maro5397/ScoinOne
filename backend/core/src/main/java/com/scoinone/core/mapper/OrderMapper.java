package com.scoinone.core.mapper;

import com.scoinone.core.dto.response.order.*;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.SellOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "buyer.id", target = "buyerId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreateBuyOrderResponseDto buyOrderToCreateBuyOrderResponseDto(BuyOrder buyOrder);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "seller.id", target = "sellerId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreateSellOrderResponseDto sellOrderToCreateSellOrderResponseDto(SellOrder sellOrder);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "buyer.id", target = "buyerId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "tradeTime", target = "tradeTime", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status", target = "status")
    GetBuyOrderResponseDto buyOrderToGetBuyOrderResponseDto(BuyOrder buyOrder);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "seller.id", target = "sellerId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "tradeTime", target = "tradeTime", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status", target = "status")
    GetSellOrderResponseDto sellOrderToGetSellOrderResponseDto(SellOrder sellOrder);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "buyer.id", target = "buyerId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "tradeTime", target = "tradeTime", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status", target = "status")
    UpdateBuyOrderResponseDto buyOrderToUpdateBuyOrderResponseDto(BuyOrder buyOrder);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "seller.id", target = "sellerId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "tradeTime", target = "tradeTime", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status", target = "status")
    UpdateSellOrderResponseDto sellOrderToUpdateSellOrderResponseDto(SellOrder sellOrder);

    List<GetBuyOrderResponseDto> buyOrdersToGetBuyOrdersResponseDto(List<BuyOrder> buyOrders);

    default GetBuyOrderListResponseDto listToGetBuyOrderListResponseDto(List<BuyOrder> buyOrders) {
        GetBuyOrderListResponseDto responseDto = new GetBuyOrderListResponseDto();
        responseDto.setBuyOrders(buyOrdersToGetBuyOrdersResponseDto(buyOrders));
        return responseDto;
    }

    List<GetSellOrderResponseDto> sellOrdersToGetSellOrdersResponseDto(List<SellOrder> sellOrders);

    default GetSellOrderListResponseDto listToGetSellOrderListResponseDto(List<SellOrder> sellOrders) {
        GetSellOrderListResponseDto responseDto = new GetSellOrderListResponseDto();
        responseDto.setSellOrders(sellOrdersToGetSellOrdersResponseDto(sellOrders));
        return responseDto;
    }
}
