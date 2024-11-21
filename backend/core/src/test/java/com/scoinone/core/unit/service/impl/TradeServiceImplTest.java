package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.Trade;
import com.scoinone.core.repository.BuyOrderRepository;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.repository.TradeRepository;
import com.scoinone.core.service.impl.TradeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TradeServiceImplTest {
    @InjectMocks
    private TradeServiceImpl tradeService;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private SellOrderRepository sellOrderRepository;

    @Mock
    private BuyOrderRepository buyOrderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("거래 조회 테스트")
    public void testGetTrades() {
        List<Trade> trades = new ArrayList<>();
        when(tradeRepository.findAll()).thenReturn(trades);

        List<Trade> result = tradeService.getTrades();

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result).isEqualTo(trades);
            verify(tradeRepository).findAll();
        });
    }

    @Test
    @DisplayName("인덱스로 거래 조회 테스트")
    public void testGetTradeById_Success() {
        Long tradeId = 1L;
        Trade trade = Trade.builder()
                .id(tradeId)
                .build();
        when(tradeRepository.findById(tradeId)).thenReturn(Optional.of(trade));

        Trade result = tradeService.getTradeById(tradeId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(tradeRepository).findById(tradeId);
        });
    }

    @Test
    @DisplayName("인덱스로 거래 조회 테스트 실패")
    public void testGetTradeById_NotFound() {
        Long tradeId = 1L;
        when(tradeRepository.findById(tradeId)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> tradeService.getTradeById(tradeId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("Trade not found with id: " + tradeId);
        });
    }

    @Test
    @DisplayName("거래 생성 테스트")
    public void testCreateTrade() {
        BuyOrder buyOrder = BuyOrder.builder().build();
        SellOrder sellOrder = SellOrder.builder().build();
        BigDecimal tradeQuantity = new BigDecimal("10.0");
        Trade trade = Trade.builder()
                .buyOrder(buyOrder)
                .sellOrder(sellOrder)
                .quantity(tradeQuantity)
                .price(sellOrder.getPrice())
                .build();

        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade result = tradeService.createTrade(buyOrder, sellOrder, tradeQuantity);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getQuantity()).isEqualTo(tradeQuantity);
            verify(tradeRepository).save(any(Trade.class));
        });
    }

    @Test
    @DisplayName("구매 주문 거래 체결 프로세스 테스트")
    public void testProcessBuyOrderTrade() {
        BuyOrder buyOrder = BuyOrder.builder()
                .quantity(BigDecimal.valueOf(20.0))
                .price(BigDecimal.valueOf(100.0))
                .build();

        SellOrder sellOrder1 = SellOrder.builder()
                .quantity(BigDecimal.valueOf(10.0))
                .price(BigDecimal.valueOf(100.0))
                .build();

        SellOrder sellOrder2 = SellOrder.builder()
                .quantity(BigDecimal.valueOf(15.0))
                .price(BigDecimal.valueOf(100.0))
                .build();

        List<SellOrder> sellOrders = List.of(sellOrder1, sellOrder2);
        when(sellOrderRepository.findMatchableSellOrders(buyOrder.getPrice())).thenReturn(Optional.of(sellOrders));

        List<Trade> trades = tradeService.processBuyOrderTrade(buyOrder);

        assertSoftly(softly -> {
            softly.assertThat(trades).hasSize(2);
            softly.assertThat(sellOrder1.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(0.0));
            softly.assertThat(sellOrder2.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(5.0));
            softly.assertThat(buyOrder.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(0.0));
            verify(tradeRepository, times(2)).save(any(Trade.class));
        });
    }

    @Test
    @DisplayName("판매 주문 거래 체결 프로세스 테스트")
    public void testProcessSellOrderTrade() {
        SellOrder sellOrder = SellOrder.builder()
                .quantity(BigDecimal.valueOf(20.0))
                .price(BigDecimal.valueOf(100.0))
                .build();

        BuyOrder buyOrder1 = BuyOrder.builder()
                .quantity(BigDecimal.valueOf(10.0))
                .price(BigDecimal.valueOf(100.0))
                .build();

        BuyOrder buyOrder2 = BuyOrder.builder()
                .quantity(BigDecimal.valueOf(15.0))
                .price(BigDecimal.valueOf(100.0))
                .build();

        List<BuyOrder> buyOrders = List.of(buyOrder1, buyOrder2);
        when(buyOrderRepository.findMatchableBuyOrders(sellOrder.getPrice())).thenReturn(Optional.of(buyOrders));

        List<Trade> trades = tradeService.processSellOrderTrade(sellOrder);

        assertSoftly(softly -> {
            softly.assertThat(trades).hasSize(2);
            softly.assertThat(sellOrder.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(0.0));
            softly.assertThat(buyOrder1.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(0.0));
            softly.assertThat(buyOrder2.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(5.0));
            verify(tradeRepository, times(2)).save(any(Trade.class));
        });
    }
}