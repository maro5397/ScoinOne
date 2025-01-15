package com.scoinone.order.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.order.common.status.OrderStatus;
import com.scoinone.order.dto.response.order.CreateBuyOrderResponseDto;
import com.scoinone.order.dto.response.order.CreateSellOrderResponseDto;
import com.scoinone.order.dto.response.order.GetBuyOrderResponseDto;
import com.scoinone.order.dto.response.order.GetBuyOrdersResponseDto;
import com.scoinone.order.dto.response.order.GetSellOrderResponseDto;
import com.scoinone.order.dto.response.order.GetSellOrdersResponseDto;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.mapper.OrderMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class OrderMapperTest {
    private static final String testUserId1 = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";
    private static final String testUserId2 = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser2";
    private static final String testUserId3 = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser3";
    private static final String testVirtualAssetId1 = "bbbbbbbb-bbbb-bbbb-bbb1-virtualasset";
    private static final String testVirtualAssetId2 = "bbbbbbbb-bbbb-bbbb-bbb2-virtualasset";
    private static final String testVirtualAssetId3 = "bbbbbbbb-bbbb-bbbb-bbb3-virtualasset";

    private OrderMapper orderMapper;
    private List<BuyOrderEntity> buyOrders;
    private List<SellOrderEntity> sellOrders;

    @BeforeEach
    public void setUp() {
        orderMapper = Mappers.getMapper(OrderMapper.class);
        buyOrders = Arrays.asList(
                createBuyOrder(1L, testUserId1, testVirtualAssetId1),
                createBuyOrder(3L, testUserId2, testVirtualAssetId2),
                createBuyOrder(5L, testUserId3, testVirtualAssetId3)
        );
        sellOrders = Arrays.asList(
                createSellOrder(2L, testUserId1, testVirtualAssetId1),
                createSellOrder(4L, testUserId2, testVirtualAssetId2),
                createSellOrder(6L, testUserId3, testVirtualAssetId3)
        );
    }

    @Test
    @DisplayName("구매주문 엔티티 객체를 구매주문 생성용 응답 DTO로 매핑")
    public void testBuyOrderToCreateBuyOrderResponseDto() {
        BuyOrderEntity buyOrder = buyOrders.getFirst();
        CreateBuyOrderResponseDto responseDto = orderMapper.buyOrderToCreateBuyOrderResponseDto(buyOrder);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getOrderId()).isEqualTo(buyOrder.getId());
            softly.assertThat(responseDto.getBuyerId()).isEqualTo(buyOrder.getBuyerId());
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(buyOrder.getVirtualAssetId());
            softly.assertThat(responseDto.getQuantity()).isEqualTo(buyOrder.getQuantity());
            softly.assertThat(responseDto.getPrice()).isEqualTo(buyOrder.getPrice());
            softly.assertThat(responseDto.getStatus()).isEqualTo(buyOrder.getStatus().getValue());
            softly.assertThat(responseDto.getCreatedAt()).isEqualTo(buyOrder.getCreatedAt());
        });
    }

    @Test
    @DisplayName("판매주문 엔티티 객체를 판매주문 생성용 응답 DTO로 매핑")
    public void testSellOrderToCreateSellOrderResponseDto() {
        SellOrderEntity sellOrder = sellOrders.getFirst();
        CreateSellOrderResponseDto responseDto = orderMapper.sellOrderToCreateSellOrderResponseDto(sellOrder);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getOrderId()).isEqualTo(sellOrder.getId());
            softly.assertThat(responseDto.getSellerId()).isEqualTo(sellOrder.getSellerId());
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(sellOrder.getVirtualAssetId());
            softly.assertThat(responseDto.getQuantity()).isEqualTo(sellOrder.getQuantity());
            softly.assertThat(responseDto.getPrice()).isEqualTo(sellOrder.getPrice());
            softly.assertThat(responseDto.getStatus()).isEqualTo(sellOrder.getStatus().getValue());
            softly.assertThat(responseDto.getCreatedAt()).isEqualTo(sellOrder.getCreatedAt());
        });
    }

    @Test
    @DisplayName("구매주문 엔티티 객체를 구매주문 조회용 응답 DTO로 매핑")
    public void testBuyOrderToGetBuyOrderResponseDto() {
        BuyOrderEntity buyOrder = buyOrders.getFirst();
        GetBuyOrderResponseDto responseDto = orderMapper.buyOrderToGetBuyOrderResponseDto(buyOrder);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getOrderId()).isEqualTo(buyOrder.getId());
            softly.assertThat(responseDto.getBuyerId()).isEqualTo(buyOrder.getBuyerId());
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(buyOrder.getVirtualAssetId());
            softly.assertThat(responseDto.getTradeTime()).isEqualTo(buyOrder.getTradeTime());
            softly.assertThat(responseDto.getQuantity()).isEqualTo(buyOrder.getQuantity());
            softly.assertThat(responseDto.getPrice()).isEqualTo(buyOrder.getPrice());
            softly.assertThat(responseDto.getStatus()).isEqualTo(buyOrder.getStatus().getValue());
        });
    }

    @Test
    @DisplayName("판매주문 엔티티 객체를 판매주문 조회용 응답 DTO로 매핑")
    public void testSellOrderToGetSellOrderResponseDto() {
        SellOrderEntity sellOrder = sellOrders.getFirst();
        GetSellOrderResponseDto responseDto = orderMapper.sellOrderToGetSellOrderResponseDto(sellOrder);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getOrderId()).isEqualTo(sellOrder.getId());
            softly.assertThat(responseDto.getSellerId()).isEqualTo(sellOrder.getSellerId());
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(sellOrder.getVirtualAssetId());
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

    private BuyOrderEntity createBuyOrder(Long id, String buyerId, String virtualAssetId) {
        return BuyOrderEntity.builder()
                .id(id)
                .buyerId(buyerId)
                .virtualAssetId(virtualAssetId)
                .quantity(BigDecimal.valueOf(10))
                .price(BigDecimal.valueOf(100))
                .status(OrderStatus.PENDING)
                .tradeTime(LocalDateTime.now())
                .build();
    }

    private SellOrderEntity createSellOrder(Long id, String sellerId, String virtualAssetId) {
        return SellOrderEntity.builder()
                .id(id)
                .sellerId(sellerId)
                .virtualAssetId(virtualAssetId)
                .quantity(BigDecimal.valueOf(10))
                .price(BigDecimal.valueOf(100))
                .status(OrderStatus.PENDING)
                .tradeTime(LocalDateTime.now())
                .build();
    }
}