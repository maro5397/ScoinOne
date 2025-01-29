package com.scoinone.order.mapper;

import com.scoinone.order.dto.response.order.CancelBuyOrderResponseDto;
import com.scoinone.order.dto.response.order.CancelSellOrderResponseDto;
import com.scoinone.order.dto.response.order.CreateBuyOrderResponseDto;
import com.scoinone.order.dto.response.order.CreateSellOrderResponseDto;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.entity.SellOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "buyerId", target = "buyerId")
    @Mapping(source = "virtualAssetId", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status.value", target = "status")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreateBuyOrderResponseDto buyOrderToCreateBuyOrderResponseDto(BuyOrderEntity buyOrder);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "sellerId", target = "sellerId")
    @Mapping(source = "virtualAssetId", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status.value", target = "status")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreateSellOrderResponseDto sellOrderToCreateSellOrderResponseDto(SellOrderEntity sellOrder);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "buyerId", target = "buyerId")
    @Mapping(source = "virtualAssetId", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status.value", target = "status")
    CancelBuyOrderResponseDto buyOrderToCancelBuyOrderResponseDto(BuyOrderEntity buyOrder);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "sellerId", target = "sellerId")
    @Mapping(source = "virtualAssetId", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status.value", target = "status")
    CancelSellOrderResponseDto sellOrderToCancelSellOrderResponseDto(SellOrderEntity sellOrder);
}
