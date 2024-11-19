package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.repository.BuyOrderRepository;
import com.scoinone.core.service.TradeService;
import com.scoinone.core.service.impl.BuyOrderServiceImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BuyOrderServiceImplTest {
    @InjectMocks
    private BuyOrderServiceImpl buyOrderService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private BuyOrderRepository buyOrderRepository;

    @Mock
    private TradeService tradeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("구매 주문 생성 테스트")
    public void testCreateBuyOrder() {
        BuyOrder buyOrder = BuyOrder.builder()
                .id(1L)
                .build();

        when(buyOrderRepository.save(buyOrder)).thenReturn(buyOrder);
        BuyOrder result = buyOrderService.createBuyOrder(buyOrder);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getId()).isEqualTo(1L);
            verify(entityManager).persist(buyOrder);
            verify(tradeService).processBuyOrderTrade(buyOrder);
            verify(buyOrderRepository).save(buyOrder);
        });
    }

    @Test
    @DisplayName("구매 주문 삭제 테스트")
    public void testDeleteBuyOrder() {
        Long buyOrderId = 1L;
        buyOrderService.deleteBuyOrder(buyOrderId);
        verify(buyOrderRepository).deleteById(buyOrderId);
    }
}