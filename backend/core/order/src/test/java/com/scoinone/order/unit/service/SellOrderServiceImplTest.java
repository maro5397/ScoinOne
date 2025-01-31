package com.scoinone.order.unit.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.order.common.status.OrderStatus;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.repository.SellOrderRepository;
import com.scoinone.order.service.TradeService;
import com.scoinone.order.service.impl.SellOrderServiceImpl;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SellOrderServiceImplTest {
    private static final String testSellerId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaauser";
    private static final String testVirtualAssetId = "bbbbbbbb-bbbb-bbbb-bbbb-virtualasset";

    @InjectMocks
    private SellOrderServiceImpl sellOrderService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private SellOrderRepository sellOrderRepository;

    @Mock
    private TradeService tradeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("판매 주문 생성 테스트")
    public void testCreateSellOrder() {
        BigDecimal quantity = BigDecimal.valueOf(2);
        BigDecimal price = BigDecimal.valueOf(300);

        sellOrderService.createSellOrder(testVirtualAssetId, quantity, price, testSellerId);

        ArgumentCaptor<SellOrderEntity> sellOrderCaptor = ArgumentCaptor.forClass(SellOrderEntity.class);
        verify(sellOrderRepository).save(sellOrderCaptor.capture());

        SellOrderEntity sellOrder = sellOrderCaptor.getValue();

        assertSoftly(softly -> {
            softly.assertThat(sellOrder).isNotNull();
            verify(entityManager).persist(sellOrder);
            verify(tradeService).processSellOrderTrade(sellOrder);
            verify(sellOrderRepository).save(sellOrder);
        });
    }

    @Test
    @DisplayName("판매 주문 취소 테스트")
    public void testCancelSellOrder() {
        Long sellOrderId = 1L;

        when(sellOrderRepository.findByIdAndSellerIdAndStatus(sellOrderId, testSellerId, OrderStatus.PENDING))
                .thenReturn(Optional.ofNullable(SellOrderEntity.builder().build()));

        sellOrderService.cancelSellOrder(sellOrderId, testSellerId);
        verify(sellOrderRepository).findByIdAndSellerIdAndStatus(sellOrderId, testSellerId, OrderStatus.PENDING);
    }

    @Test
    @DisplayName("사용자 판매 주문 조회")
    public void testGetSellOrderByUserId() {
        Long userId = 1L;
        List<SellOrderEntity> sellOrders = Collections.singletonList(SellOrderEntity.builder().build());
        when(sellOrderRepository.findBySellerIdAndStatus(testSellerId, OrderStatus.PENDING)).thenReturn(sellOrders);

        List<SellOrderEntity> result = sellOrderService.getSellOrderByUserId(testSellerId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(sellOrderRepository).findBySellerIdAndStatus(testSellerId, OrderStatus.PENDING);
        });
    }
}