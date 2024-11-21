package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.service.TradeService;
import com.scoinone.core.service.impl.SellOrderServiceImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SellOrderServiceImplTest {
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
        SellOrder sellOrder = SellOrder.builder()
                .id(1L)
                .build();

        when(sellOrderRepository.save(sellOrder)).thenReturn(sellOrder);
        sellOrderService.createSellOrder(sellOrder);

        assertSoftly(softly -> {
            verify(entityManager).persist(sellOrder);
            verify(tradeService).processSellOrderTrade(sellOrder);
            verify(sellOrderRepository).save(sellOrder);
        });
    }

    @Test
    @DisplayName("판매 주문 삭제 테스트")
    public void testDeleteSellOrder() {
        Long sellOrderId = 1L;

        sellOrderService.deleteSellOrder(sellOrderId);

        verify(sellOrderRepository).deleteById(sellOrderId);
    }
}