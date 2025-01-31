package com.scoinone.order.unit.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.order.common.status.OrderStatus;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.repository.BuyOrderRepository;
import com.scoinone.order.service.TradeService;
import com.scoinone.order.service.impl.BuyOrderServiceImpl;
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

class BuyOrderServiceImplTest {
    private static final String testBuyerId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaauser";
    private static final String testVirtualAssetId = "bbbbbbbb-bbbb-bbbb-bbbb-virtualasset";

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
        BigDecimal quantity = BigDecimal.valueOf(2);
        BigDecimal price = BigDecimal.valueOf(300);

        buyOrderService.createBuyOrder(testVirtualAssetId, quantity, price, testBuyerId);

        ArgumentCaptor<BuyOrderEntity> buyOrderCaptor = ArgumentCaptor.forClass(BuyOrderEntity.class);
        verify(buyOrderRepository).save(buyOrderCaptor.capture());

        BuyOrderEntity buyOrder = buyOrderCaptor.getValue();

        assertSoftly(softly -> {
            softly.assertThat(buyOrder).isNotNull();
            verify(entityManager).persist(buyOrder);
            verify(tradeService).processBuyOrderTrade(buyOrder);
            verify(buyOrderRepository).save(buyOrder);
        });
    }

    @Test
    @DisplayName("구매 주문 삭제 테스트")
    public void testCancelBuyOrder() {
        Long buyOrderId = 1L;

        when(buyOrderRepository.findByIdAndBuyerIdAndStatus(buyOrderId, testBuyerId, OrderStatus.PENDING))
                .thenReturn(Optional.ofNullable(BuyOrderEntity.builder().build()));

        buyOrderService.cancelBuyOrder(buyOrderId, testBuyerId);
        verify(buyOrderRepository).findByIdAndBuyerIdAndStatus(buyOrderId, testBuyerId, OrderStatus.PENDING);
    }

    @Test
    @DisplayName("사용자 구매 주문 조회")
    public void testGetBuyOrderByUserId() {
        List<BuyOrderEntity> buyOrders = Collections.singletonList(BuyOrderEntity.builder().build());
        when(buyOrderRepository.findByBuyerIdAndStatus(testBuyerId, OrderStatus.PENDING)).thenReturn(buyOrders);

        List<BuyOrderEntity> result = buyOrderService.getBuyOrderByUserId(testBuyerId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(buyOrderRepository).findByBuyerIdAndStatus(testBuyerId, OrderStatus.PENDING);
        });
    }
}