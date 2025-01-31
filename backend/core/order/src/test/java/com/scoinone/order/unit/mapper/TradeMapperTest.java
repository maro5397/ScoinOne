package com.scoinone.order.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.order.dto.response.trade.GetTradeResponseDto;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.entity.TradeEntity;
import com.scoinone.order.mapper.TradeMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class TradeMapperTest {
    private static final String testVirtualAssetId1 = "bbbbbbbb-bbbb-bbbb-bbbb-virtualasset";
    private static final String testVirtualAssetId2 = "cccccccc-cccc-cccc-cccc-virtualasset";

    private TradeMapper mapper;
    private List<TradeEntity> trades;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(TradeMapper.class);
        trades = Arrays.asList(
                createTrade(1L, 100L, 200L, testVirtualAssetId1, new BigDecimal("10.5"), new BigDecimal("150.75")),
                createTrade(2L, 400L, 500L, testVirtualAssetId2, new BigDecimal("20.5"), new BigDecimal("250.75"))
        );
    }

    @Test
    @DisplayName("거래 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testTradeToGetTradeResponseDto() {
        TradeEntity trade = trades.getFirst();

        GetTradeResponseDto responseDto = mapper.tradeToGetTradeResponseDto(trade);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getTradeId()).isEqualTo(1L);
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(testVirtualAssetId1);
            softly.assertThat(responseDto.getQuantity()).isEqualTo(new BigDecimal("10.5"));
            softly.assertThat(responseDto.getPrice()).isEqualTo(new BigDecimal("150.75"));
        });
    }

    private TradeEntity createTrade(Long id, Long buyId, Long sellId, String virtualAssetId,
                              BigDecimal quantity, BigDecimal price) {
        BuyOrderEntity buyOrder = BuyOrderEntity.builder()
                .id(buyId)
                .build();

        SellOrderEntity sellOrder = SellOrderEntity.builder()
                .id(sellId)
                .build();

        return TradeEntity.builder()
                .id(id)
                .buyOrder(buyOrder)
                .sellOrder(sellOrder)
                .virtualAssetId(virtualAssetId)
                .quantity(quantity)
                .price(price)
                .build();
    }
}