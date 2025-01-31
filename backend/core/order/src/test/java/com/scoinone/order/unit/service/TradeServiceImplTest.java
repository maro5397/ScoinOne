package com.scoinone.order.unit.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.entity.TradeEntity;
import com.scoinone.order.repository.BuyOrderRepository;
import com.scoinone.order.repository.SellOrderRepository;
import com.scoinone.order.repository.TradeRepository;
import com.scoinone.order.service.impl.TradeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TradeServiceImplTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaauser";
    private static final String testVirtualAssetId = "bbbbbbbb-bbbb-bbbb-bbbb-virtualasset";

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
        List<TradeEntity> trades = new ArrayList<>();
        when(tradeRepository.findAll()).thenReturn(trades);

        List<TradeEntity> result = tradeService.getTrades();

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
        TradeEntity trade = TradeEntity.builder()
                .id(tradeId)
                .build();
        when(tradeRepository.findById(tradeId)).thenReturn(Optional.of(trade));

        TradeEntity result = tradeService.getTradeById(tradeId);

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
        BuyOrderEntity buyOrder = BuyOrderEntity.builder()
                .buyerId(testUserId)
                .virtualAssetId(testVirtualAssetId)
                .build();
        SellOrderEntity sellOrder = SellOrderEntity.builder()
                .sellerId(testUserId)
                .virtualAssetId(testVirtualAssetId)
                .build();
        BigDecimal tradeQuantity = new BigDecimal("10.0");
        TradeEntity trade = TradeEntity.builder()
                .buyOrder(buyOrder)
                .sellOrder(sellOrder)
                .quantity(tradeQuantity)
                .price(sellOrder.getPrice())
                .build();

        when(tradeRepository.save(any(TradeEntity.class))).thenReturn(trade);

        TradeEntity result = tradeService.createTrade(buyOrder, sellOrder, tradeQuantity);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getQuantity()).isEqualTo(tradeQuantity);
            verify(tradeRepository).save(any(TradeEntity.class));
        });
    }

    @Test
    @DisplayName("구매 주문 거래 체결 프로세스 테스트")
    public void testProcessBuyOrderTrade() {
        BuyOrderEntity buyOrder = BuyOrderEntity.builder()
                .buyerId(testUserId)
                .virtualAssetId(testVirtualAssetId)
                .quantity(BigDecimal.valueOf(20.0))
                .price(BigDecimal.valueOf(100.0))
                .build();

        SellOrderEntity sellOrder1 = SellOrderEntity.builder()
                .sellerId(testUserId)
                .virtualAssetId(testVirtualAssetId)
                .quantity(BigDecimal.valueOf(10.0))
                .price(BigDecimal.valueOf(100.0))
                .build();

        SellOrderEntity sellOrder2 = SellOrderEntity.builder()
                .sellerId(testUserId)
                .virtualAssetId(testVirtualAssetId)
                .quantity(BigDecimal.valueOf(15.0))
                .price(BigDecimal.valueOf(100.0))
                .build();

        List<SellOrderEntity> sellOrders = List.of(sellOrder1, sellOrder2);
        when(sellOrderRepository.findMatchableSellOrders(buyOrder.getPrice())).thenReturn(sellOrders);

        List<TradeEntity> trades = tradeService.processBuyOrderTrade(buyOrder);

        assertSoftly(softly -> {
            softly.assertThat(trades).hasSize(2);
            softly.assertThat(sellOrder1.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(0.0));
            softly.assertThat(sellOrder2.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(5.0));
            softly.assertThat(buyOrder.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(0.0));
            verify(tradeRepository, times(2)).save(any(TradeEntity.class));
        });
    }

    @Test
    @DisplayName("판매 주문 거래 체결 프로세스 테스트")
    public void testProcessSellOrderTrade() {
        SellOrderEntity sellOrder = SellOrderEntity.builder()
                .sellerId(testUserId)
                .virtualAssetId(testVirtualAssetId)
                .quantity(BigDecimal.valueOf(20.0))
                .price(BigDecimal.valueOf(100.0))
                .build();

        BuyOrderEntity buyOrder1 = BuyOrderEntity.builder()
                .buyerId(testUserId)
                .virtualAssetId(testVirtualAssetId)
                .quantity(BigDecimal.valueOf(10.0))
                .price(BigDecimal.valueOf(100.0))
                .build();

        BuyOrderEntity buyOrder2 = BuyOrderEntity.builder()
                .buyerId(testUserId)
                .virtualAssetId(testVirtualAssetId)
                .quantity(BigDecimal.valueOf(15.0))
                .price(BigDecimal.valueOf(100.0))
                .build();

        List<BuyOrderEntity> buyOrders = List.of(buyOrder1, buyOrder2);
        when(buyOrderRepository.findMatchableBuyOrders(sellOrder.getPrice())).thenReturn(buyOrders);

        List<TradeEntity> trades = tradeService.processSellOrderTrade(sellOrder);

        assertSoftly(softly -> {
            softly.assertThat(trades).hasSize(2);
            softly.assertThat(sellOrder.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(0.0));
            softly.assertThat(buyOrder1.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(0.0));
            softly.assertThat(buyOrder2.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(5.0));
            verify(tradeRepository, times(2)).save(any(TradeEntity.class));
        });
    }

    @Test
    @DisplayName("사용자 체결 거래 조회")
    public void testGetTradeByUserId() {
        List<TradeEntity> trades = Collections.singletonList(TradeEntity.builder().build());
        when(tradeRepository.findTradesByUserId(testUserId)).thenReturn(trades);

        List<TradeEntity> result = tradeService.getTradeByUserId(testUserId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.size()).isEqualTo(trades.size());
            verify(tradeRepository).findTradesByUserId(testUserId);
        });
    }
}