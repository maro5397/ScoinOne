package com.scoinone.order.mapper;

import com.scoinone.order.dto.response.order.CancelBuyOrderResponseDto;
import com.scoinone.order.dto.response.order.CancelSellOrderResponseDto;
import com.scoinone.order.dto.response.order.CreateBuyOrderResponseDto;
import com.scoinone.order.dto.response.order.CreateSellOrderResponseDto;
import com.scoinone.order.dto.response.order.GetOrderByUserIdResponseDto;
import com.scoinone.order.dto.response.order.GetOrderResponseDto;
import com.scoinone.order.dto.response.order.GetOrdersByUserIdResponseDto;
import com.scoinone.order.dto.response.order.GetOrdersResponseDto;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.entity.base.OrderEntity;
import java.util.List;
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

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "virtualAssetId", target = "virtualAssetId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "createdAt", target = "createdAt")
    GetOrderResponseDto orderToGetOrderResponseDto(OrderEntity order);

    List<GetOrderResponseDto> orderToGetOrdersResponseDto(List<OrderEntity> orders);

    default GetOrdersResponseDto listToGetOrdersResponseDto(List<OrderEntity> orders) {
        GetOrdersResponseDto responseDto = new GetOrdersResponseDto();
        responseDto.setOrders(orderToGetOrdersResponseDto(orders));
        return responseDto;
    }

    default GetOrderByUserIdResponseDto orderToGetOrderByUserIdResponseDto(OrderEntity order, String orderId) {
        GetOrderByUserIdResponseDto dto = new GetOrderByUserIdResponseDto();

        dto.setOrderId(orderId);
        dto.setVirtualAssetId(order.getVirtualAssetId());
        dto.setQuantity(order.getQuantity());
        dto.setPrice(order.getPrice());
        dto.setCreatedAt(order.getCreatedAt());

        if (order instanceof BuyOrderEntity) {
            dto.setUserId(((BuyOrderEntity) order).getBuyerId());
        } else if (order instanceof SellOrderEntity) {
            dto.setUserId(((SellOrderEntity) order).getSellerId());
        }

        dto.setStatus(order.getStatus().getValue());

        return dto;
    }

    List<GetOrderByUserIdResponseDto> orderToGetOrderByUserIdResponseDto(List<OrderEntity> orders);

    default GetOrdersByUserIdResponseDto listToGetOrdersByUserIdResponseDto(List<OrderEntity> orders) {
        GetOrdersByUserIdResponseDto responseDto = new GetOrdersByUserIdResponseDto();
        responseDto.setOrders(orderToGetOrderByUserIdResponseDto(orders));
        return responseDto;
    }
}
