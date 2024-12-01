package com.scoinone.core.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.dto.response.order.CreateBuyOrderResponseDto;
import com.scoinone.core.dto.response.order.CreateSellOrderResponseDto;
import com.scoinone.core.dto.response.order.GetBuyOrdersResponseDto;
import com.scoinone.core.dto.response.order.GetBuyOrderResponseDto;
import com.scoinone.core.dto.response.order.GetSellOrdersResponseDto;
import com.scoinone.core.dto.response.order.GetSellOrderResponseDto;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.mapper.OrderMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class OrderMapperTest {
    private OrderMapper orderMapper;
    private List<BuyOrder> buyOrders;
    private List<SellOrder> sellOrders;

    @BeforeEach
    public void setUp() {
        orderMapper = Mappers.getMapper(OrderMapper.class);
        buyOrders = Arrays.asList(
                createBuyOrder(1L, 1L, 1L),
                createBuyOrder(3L, 3L, 3L),
                createBuyOrder(5L, 5L, 5L)
        );
        sellOrders = Arrays.asList(
                createSellOrder(2L, 2L, 2L),
                createSellOrder(4L, 4L, 4L),
                createSellOrder(6L, 6L, 6L)
        );
    }

    @Test
    @DisplayName("구매주문 엔티티 객체를 구매주문 생성용 응답 DTO로 매핑")
    public void testBuyOrderToCreateBuyOrderResponseDto() {
        BuyOrder buyOrder = buyOrders.getFirst();
        CreateBuyOrderResponseDto responseDto = orderMapper.buyOrderToCreateBuyOrderResponseDto(buyOrder);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getOrderId()).isEqualTo(buyOrder.getId());
            softly.assertThat(responseDto.getBuyerId()).isEqualTo(buyOrder.getBuyer().getId());
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(buyOrder.getVirtualAsset().getId());
            softly.assertThat(responseDto.getQuantity()).isEqualTo(buyOrder.getQuantity());
            softly.assertThat(responseDto.getPrice()).isEqualTo(buyOrder.getPrice());
            softly.assertThat(responseDto.getStatus()).isEqualTo(buyOrder.getStatus().getValue());
            softly.assertThat(responseDto.getCreatedAt()).isEqualTo(buyOrder.getCreatedAt());
        });
    }

    @Test
    @DisplayName("판매주문 엔티티 객체를 판매주문 생성용 응답 DTO로 매핑")
    public void testSellOrderToCreateSellOrderResponseDto() {
        SellOrder sellOrder = sellOrders.getFirst();
        CreateSellOrderResponseDto responseDto = orderMapper.sellOrderToCreateSellOrderResponseDto(sellOrder);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getOrderId()).isEqualTo(sellOrder.getId());
            softly.assertThat(responseDto.getSellerId()).isEqualTo(sellOrder.getSeller().getId());
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(sellOrder.getVirtualAsset().getId());
            softly.assertThat(responseDto.getQuantity()).isEqualTo(sellOrder.getQuantity());
            softly.assertThat(responseDto.getPrice()).isEqualTo(sellOrder.getPrice());
            softly.assertThat(responseDto.getStatus()).isEqualTo(sellOrder.getStatus().getValue());
            softly.assertThat(responseDto.getCreatedAt()).isEqualTo(sellOrder.getCreatedAt());
        });
    }

    @Test
    @DisplayName("구매주문 엔티티 객체를 구매주문 조회용 응답 DTO로 매핑")
    public void testBuyOrderToGetBuyOrderResponseDto() {
        BuyOrder buyOrder = buyOrders.getFirst();
        GetBuyOrderResponseDto responseDto = orderMapper.buyOrderToGetBuyOrderResponseDto(buyOrder);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getOrderId()).isEqualTo(buyOrder.getId());
            softly.assertThat(responseDto.getBuyerId()).isEqualTo(buyOrder.getBuyer().getId());
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(buyOrder.getVirtualAsset().getId());
            softly.assertThat(responseDto.getTradeTime()).isEqualTo(buyOrder.getTradeTime());
            softly.assertThat(responseDto.getQuantity()).isEqualTo(buyOrder.getQuantity());
            softly.assertThat(responseDto.getPrice()).isEqualTo(buyOrder.getPrice());
            softly.assertThat(responseDto.getStatus()).isEqualTo(buyOrder.getStatus().getValue());
        });
    }

    @Test
    @DisplayName("판매주문 엔티티 객체를 판매주문 조회용 응답 DTO로 매핑")
    public void testSellOrderToGetSellOrderResponseDto() {
        SellOrder sellOrder = sellOrders.getFirst();
        GetSellOrderResponseDto responseDto = orderMapper.sellOrderToGetSellOrderResponseDto(sellOrder);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getOrderId()).isEqualTo(sellOrder.getId());
            softly.assertThat(responseDto.getSellerId()).isEqualTo(sellOrder.getSeller().getId());
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(sellOrder.getVirtualAsset().getId());
            softly.assertThat(responseDto.getTradeTime()).isEqualTo(sellOrder.getTradeTime());
            softly.assertThat(responseDto.getQuantity()).isEqualTo(sellOrder.getQuantity());
            softly.assertThat(responseDto.getPrice()).isEqualTo(sellOrder.getPrice());
            softly.assertThat(responseDto.getStatus()).isEqualTo(sellOrder.getStatus().getValue());
        });
    }

    @Test
    @DisplayName("구매주문 엔티티 객체를 구매주문 리스트 조회용 응답 DTO로 매핑")
    public void testListToGetBuyOrderListResponseDto() {
        GetBuyOrdersResponseDto responseDto = orderMapper.listToGetBuyOrdersResponseDto(buyOrders);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getBuyOrders()).hasSize(buyOrders.size());
            softly.assertThat(responseDto.getBuyOrders().get(0).getOrderId()).isEqualTo(buyOrders.get(0).getId());
            softly.assertThat(responseDto.getBuyOrders().get(1).getOrderId()).isEqualTo(buyOrders.get(1).getId());
            softly.assertThat(responseDto.getBuyOrders().get(2).getOrderId()).isEqualTo(buyOrders.get(2).getId());
        });
    }

    @Test
    @DisplayName("판매주문 엔티티 객체를 판매주문 리스트 조회용 응답 DTO로 매핑")
    public void testListToGetSellOrderListResponseDto() {
        GetSellOrdersResponseDto responseDto = orderMapper.listToGetSellOrdersResponseDto(sellOrders);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getSellOrders()).hasSize(sellOrders.size());
            softly.assertThat(responseDto.getSellOrders().get(0).getOrderId()).isEqualTo(sellOrders.get(0).getId());
            softly.assertThat(responseDto.getSellOrders().get(1).getOrderId()).isEqualTo(sellOrders.get(1).getId());
            softly.assertThat(responseDto.getSellOrders().get(2).getOrderId()).isEqualTo(sellOrders.get(2).getId());
        });
    }

    private BuyOrder createBuyOrder(Long id, Long buyerId, Long virtualAssetId) {
        User buyer = User.builder()
                .id(buyerId)
                .build();

        VirtualAsset virtualAsset = VirtualAsset.builder()
                .id(virtualAssetId)
                .build();

        return BuyOrder.builder()
                .id(id)
                .buyer(buyer)
                .virtualAsset(virtualAsset)
                .quantity(BigDecimal.valueOf(10))
                .price(BigDecimal.valueOf(100))
                .status(OrderStatus.PENDING)
                .tradeTime(LocalDateTime.now())
                .build();
    }

    private SellOrder createSellOrder(Long id, Long sellerId, Long virtualAssetId) {
        User seller = User.builder()
                .id(sellerId)
                .build();

        VirtualAsset virtualAsset = VirtualAsset.builder()
                .id(virtualAssetId)
                .build();

        return SellOrder.builder()
                .id(id)
                .seller(seller)
                .virtualAsset(virtualAsset)
                .quantity(BigDecimal.valueOf(10))
                .price(BigDecimal.valueOf(100))
                .status(OrderStatus.PENDING)
                .tradeTime(LocalDateTime.now())
                .build();
    }
}