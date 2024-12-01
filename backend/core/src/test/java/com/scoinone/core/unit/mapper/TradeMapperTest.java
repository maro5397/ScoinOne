package com.scoinone.core.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.dto.response.trade.GetTradesResponseDto;
import com.scoinone.core.dto.response.trade.GetTradeResponseDto;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.Trade;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.mapper.TradeMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class TradeMapperTest {
    private TradeMapper mapper;
    private List<Trade> trades;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(TradeMapper.class);
        trades = Arrays.asList(
                createTrade(1L, 100L, 200L, 300L, new BigDecimal("10.5"), new BigDecimal("150.75")),
                createTrade(2L, 400L, 500L, 600L, new BigDecimal("20.5"), new BigDecimal("250.75"))
        );
    }

    @Test
    @DisplayName("거래 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testTradeToGetTradeResponseDto() {
        Trade trade = trades.getFirst();

        GetTradeResponseDto responseDto = mapper.tradeToGetTradeResponseDto(trade);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getTradeId()).isEqualTo(1L);
            softly.assertThat(responseDto.getBuyId()).isEqualTo(100L);
            softly.assertThat(responseDto.getSellId()).isEqualTo(200L);
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(300L);
            softly.assertThat(responseDto.getQuantity()).isEqualTo(new BigDecimal("10.5"));
            softly.assertThat(responseDto.getPrice()).isEqualTo(new BigDecimal("150.75"));
        });
    }

    @Test
    @DisplayName("다수의 거래 엔티티 객체들을 조회용 응답 DTO로 매핑")
    public void testListToGetTradeListResponseDto() {
        GetTradesResponseDto responseDto = mapper.listToGetTradesResponseDto(trades);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getTrades()).hasSize(2);
            softly.assertThat(responseDto.getTrades().get(0).getTradeId()).isEqualTo(1L);
            softly.assertThat(responseDto.getTrades().get(1).getTradeId()).isEqualTo(2L);
        });
    }

    private Trade createTrade(Long id, Long buyId, Long sellId, Long virtualAssetId,
                              BigDecimal quantity, BigDecimal price) {
        BuyOrder buyOrder = BuyOrder.builder()
                .id(buyId)
                .build();

        SellOrder sellOrder = SellOrder.builder()
                .id(sellId)
                .build();

        VirtualAsset virtualAsset = VirtualAsset.builder()
                .id(virtualAssetId)
                .build();

        return Trade.builder()
                .id(id)
                .buyOrder(buyOrder)
                .sellOrder(sellOrder)
                .virtualAsset(virtualAsset)
                .quantity(quantity)
                .price(price)
                .build();
    }
}