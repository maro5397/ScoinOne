package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.BuyOrderRepository;
import com.scoinone.core.repository.VirtualAssetRepository;
import com.scoinone.core.service.TradeService;
import com.scoinone.core.service.impl.BuyOrderServiceImpl;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
    private VirtualAssetRepository virtualAssetRepository;

    @Mock
    private TradeService tradeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("구매 주문 생성 테스트")
    public void testCreateBuyOrder() {
        Long assetId = 1L;
        BigDecimal quantity = BigDecimal.valueOf(2);
        BigDecimal price = BigDecimal.valueOf(300);
        VirtualAsset virtualAsset = VirtualAsset.builder().build();
        User user = User.builder().build();
        when(virtualAssetRepository.findById(assetId)).thenReturn(Optional.of(virtualAsset));

        buyOrderService.createBuyOrder(assetId, quantity, price, user);

        ArgumentCaptor<BuyOrder> buyOrderCaptor = ArgumentCaptor.forClass(BuyOrder.class);
        verify(buyOrderRepository).save(buyOrderCaptor.capture());

        BuyOrder buyOrder = buyOrderCaptor.getValue();

        assertSoftly(softly -> {
            softly.assertThat(buyOrder).isNotNull();
            verify(entityManager).persist(buyOrder);
            verify(tradeService).processBuyOrderTrade(buyOrder);
            verify(buyOrderRepository).save(buyOrder);
        });
    }

    @Test
    @DisplayName("구매 주문 삭제 테스트")
    public void testDeleteBuyOrder() {
        Long buyOrderId = 1L;
        Long buyerId = 1L;

        when(buyOrderRepository.deleteByIdAndBuyer_Id(buyOrderId, buyerId)).thenReturn(1L);

        buyOrderService.deleteBuyOrder(buyOrderId, buyerId);
        verify(buyOrderRepository).deleteByIdAndBuyer_Id(buyOrderId, buyerId);
    }
}